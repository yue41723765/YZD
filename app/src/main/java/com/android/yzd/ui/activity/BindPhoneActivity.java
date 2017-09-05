package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.been.UserRegAgr;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.F;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.thread.TimeCount;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 33181 on 2017/9/5.
 */

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.register_tel)
    EditText registerTel;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.register_check)
    CheckBox registerCheck;
    @BindView(R.id.protocol_deal)
    TextView protocolDeal;
    @BindView(R.id.register_toast)
    TextView reminder;
    @BindView(R.id.register_invite)
    EditText invite;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.bottom)
    LinearLayout bottom;

    SubscriberOnNextListener getCodeListener;
    SubscriberOnNextListener bindThreeListener;
    SubscriberOnNextListener userRegAgrListener;

    TimeCount count;
    String codeTel;
    //user_id
    String user_id;
    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.transparencyBar(this);
        }
        ButterKnife.bind(this);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        user_id=getIntent().getExtras().getString(K.USERID);
        next.setBackgroundResource(R.drawable.register_mobile_button);
        //获取验证码
        getCodeListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                count = new TimeCount(60 * 1000, 1000);
                count.setTimeCount(getCode, "s后重新发送");
                count.start();
                reminder.setText("信息已送达,10分钟内有效");
                reminder.setVisibility(View.VISIBLE);
            }
        };
        //注册协议
        userRegAgrListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr ura = gson.fromJson(gson.toJson(o), UserRegAgr.class);
                intent = new Intent(BindPhoneActivity.this, WebView.class);
                intent.putExtra(K.DATA, ura);
                startActivity(intent);
            }
        };

        bindThreeListener=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                loadUserInfo(user_id);
            }
        };
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.next:
                String tel = registerTel.getText().toString();
                String code = registerCode.getText().toString();
                String invite_code=invite.getText().toString();
                if (!U.isTel(tel)) {
                    //T.show(this, "手机号码出错", Toast.LENGTH_SHORT);
                    reminder.setText("手机号码出错");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                if (!tel.equals(codeTel)) {
                    //T.show(this, "两次手机号码不一致!", Toast.LENGTH_SHORT);
                    reminder.setText("两次手机号码不一致");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                if (!registerCheck.isChecked()) {
                    // T.show(this, "请阅读并同意《一站达注册协议》!", Toast.LENGTH_SHORT);
                    reminder.setText("请阅读并同意《一站达注册协议》");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                if (code.equals("")) {
                    //T.show(this, "请出入验证码!", Toast.LENGTH_SHORT);
                    reminder.setText("请输入验证码");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                setProgressSubscriber(bindThreeListener);
                params.put("m_id", user_id);
                params.put("account",tel);
                params.put("verify",code);
                params.put("invite_code",invite_code);
                HttpMethods.getInstance(this).bindPhone(progressSubscriber, params);
                break;
            case R.id.get_code:
                codeTel = registerTel.getText().toString();
                if (codeTel.equals("")) {
                    reminder.setText("请出入手机号码");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                if (!U.isTel(codeTel)) {
                    reminder.setText("手机号码出错");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                setProgressSubscriber(getCodeListener);
                params = new HashMap<>();
                params.put("account", codeTel);
                params.put("type", F.RE_BIND);
                HttpMethods.getInstance(this).sendVerify(progressSubscriber, params);
                break;
            case R.id.protocol_deal:
                setProgressSubscriber(userRegAgrListener);
                HttpMethods.getInstance(this).userRegAgr(progressSubscriber);
                break;
        }
    }


    /*
    *
    *重新加载用户信息通过m_id
    *
    */
    public void loadUserInfo(String m_id){
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserInfoEntity userInfo = gson.fromJson(gson.toJson(o), UserInfoEntity.class);
                SPUtils.put(BindPhoneActivity.this, K.USERINFO, userInfo);
                loginEC(userInfo.getEasemob_account(), userInfo.getEasemob_password());
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("m_id", m_id);
        HttpMethods.getInstance(this).memberCenter(progressSubscriber, params);
    }
    /*
    *
    *环信登录包含了跳转界面
    *
    */
    private void loginEC(String ecUser, String ecPass) {
        EMClient.getInstance().login(ecUser, ecPass, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                SPUtils.put(BindPhoneActivity.this, K.ISLOG, false);
                SPUtils.put(BindPhoneActivity.this, K.USERNAME, codeTel);

                intent = new Intent(BindPhoneActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                handler.sendEmptyMessageAtTime(-1, 0);
            }

            @Override
            public void onProgress(int progress, String status) {

            }
            @Override
            public void onError(int code, String message) {
                //L.i("----onError----");
                handler.sendEmptyMessageAtTime(code, 0);
            }
        });
    }

    /*
   *
   * 返还状态
   *
   * */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    reminder.setText("登录成功");
                    reminder.setVisibility(View.VISIBLE);
                    break;
                case 200:
                    reminder.setText("用户已登录");
                    reminder.setVisibility(View.VISIBLE);
                    break;
                case 202:
                    reminder.setText("账号/密码不正确");
                    reminder.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}
