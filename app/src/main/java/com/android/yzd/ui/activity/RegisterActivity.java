package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.UserRegAgr;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.F;
import com.android.yzd.tools.K;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.thread.TimeCount;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class RegisterActivity extends BaseActivity {
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

    int status_title;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.bottom)
    LinearLayout bottom;

    SubscriberOnNextListener getCodeListener;
    SubscriberOnNextListener verifytCodeListener;
    SubscriberOnNextListener userRegAgrListener;
    SubscriberOnNextListener sendInviteCode;

    TimeCount count;
    String codeTel;

    String inviteCode;
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
        status_title = getIntent().getExtras().getInt(K.TITLE);
        if (status_title == 2) {
            //title.setText("忘记密码");
            //bindingTel.setText("安全验证");
            //next.setText("验证");
            invite.setVisibility(View.INVISIBLE);
            bottom.setVisibility(View.GONE);
        }else if (status_title==3){
           next.setBackgroundResource(R.drawable.register_mobile_button);
        }
        getCodeListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                count = new TimeCount(60 * 1000, 1000);
                count.setTimeCount(getCode, "s后重新发送");
                count.start();
                //T.show(RegisterActivity.this, "信息已送达,10分钟内有效", Toast.LENGTH_SHORT);
                reminder.setText("信息已送达,10分钟内有效");
                reminder.setVisibility(View.VISIBLE);
            }
        };
        verifytCodeListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                    intent = new Intent(RegisterActivity.this, Register2Activity.class);
                    intent.putExtra(K.TITLE, status_title);
                    intent.putExtra(K.DATA, codeTel);
                    intent.putExtra(K.INVITE_CODE,inviteCode);
                    startActivity(intent);
                    finish();
            }
        };
        userRegAgrListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr ura = gson.fromJson(gson.toJson(o), UserRegAgr.class);
                intent = new Intent(RegisterActivity.this, WebView.class);
                intent.putExtra(K.DATA, ura);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next:
                String tel = registerTel.getText().toString();
                String code = registerCode.getText().toString();
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
                inviteCode=invite.getText().toString();
                setProgressSubscriber(verifytCodeListener);
                params.put("verify", code);
                HttpMethods.getInstance(this).checkVerify(progressSubscriber, params);
                break;
            case R.id.get_code:
                codeTel = registerTel.getText().toString();
                if (codeTel.equals("")) {
                    //T.show(this, "请出入手机号码", Toast.LENGTH_SHORT);
                    reminder.setText("请出入手机号码");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                if (!U.isTel(codeTel)) {
                    //T.show(this, "手机号码出错", Toast.LENGTH_SHORT);
                    reminder.setText("手机号码出错");
                    reminder.setVisibility(View.VISIBLE);
                    return;
                }
                setProgressSubscriber(getCodeListener);
                params = new HashMap<>();
                params.put("account", codeTel);
                if (status_title == 1) {
                    params.put("type", F.ACTIVATE);
                } else if (status_title==2){
                    params.put("type", F.RETRIEVE);
                }else {
                    params.put("type", F.RE_BIND);
                }
                HttpMethods.getInstance(this).sendVerify(progressSubscriber, params);
                break;
            case R.id.protocol_deal:
                setProgressSubscriber(userRegAgrListener);
                HttpMethods.getInstance(this).userRegAgr(progressSubscriber);
                break;
        }
    }

}
