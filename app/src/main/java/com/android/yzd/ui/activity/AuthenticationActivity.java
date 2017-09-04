package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.F;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.thread.TimeCount;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class AuthenticationActivity extends BaseActivity {
    @BindView(R.id.authentication)
    TextView authentication;
    @BindView(R.id.binding_tel)
    TextView bindingTel;
    @BindView(R.id.authentication_tel)
    EditText authenticationTel;
    @BindView(R.id.authentication_code)
    EditText authenticationCode;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.sure)
    Button sure;

    SubscriberOnNextListener mChangeTelSubscriberOnNextListener;
    ProgressSubscriber mProgressSubscriber;

    SubscriberOnNextListener mVerifyTelSubscriberOnNextListener;
    ProgressSubscriber mVerifyTelProgressSubscriber;

    SubscriberOnNextListener mSendCodeSubscriberOnNextListener;
    ProgressSubscriber mSendCodeProgressSubscriber;
    Map<String, String> param = new HashMap<>();

    TimeCount count;
    String mNewTel;
    String mCode;

    int status = 1;//1---验证  2---绑定

    @Override
    public int getContentViewId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        authenticationTel.setFocusable(false);
        authenticationTel.setFocusableInTouchMode(false);

        String tel = getUserInfo().getAccount();
        tel = tel.substring(0, 3) + "****" + tel.substring(7, 11);
        authenticationTel.setText(tel);
        count = new TimeCount(60 * 1000, 1000);
        count.setTimeCount(getCode, "s后重新发送");
        mChangeTelSubscriberOnNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                intent = new Intent();
                intent.putExtra("tel", mNewTel);
                setResult(10, intent);
                finish();
            }
        };
        mSendCodeSubscriberOnNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                count.start();
                T.show(AuthenticationActivity.this, "信息已送达,10分钟内有效", Toast.LENGTH_SHORT);
            }
        };
        mVerifyTelSubscriberOnNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                authenticationTel.setFocusable(true);
                authenticationTel.setFocusableInTouchMode(true);
                authenticationTel.requestFocus();
                count.onStop();
                count.onFinish();
                getCode.setText("发送验证码");
                count = new TimeCount(60 * 1000, 1000);
                count.setTimeCount(getCode, "s后重新发送");
                status = 2;
                sure.setText("绑定");
                authenticationTel.setText("");
                authenticationCode.setText("");
                authentication.setTextColor(getResources().getColor(R.color.black_30));
                bindingTel.setTextColor(getResources().getColor(R.color.red_30));
            }
        };

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sure:
                if (status == 1) {
                    mNewTel = getUserInfo().getAccount();
                    mCode = authenticationCode.getText().toString();
                    mVerifyTelProgressSubscriber = new ProgressSubscriber(mVerifyTelSubscriberOnNextListener, this, false);
                    param.clear();
                    param.put("type", F.MOD_BIND);
                    param.put("account", mNewTel);
                    param.put("verify", mCode);
                    HttpMethods.getInstance(this).checkVerify(mVerifyTelProgressSubscriber, param);

                } else if (status == 2) {
                    mNewTel = authenticationTel.getText().toString();
                    mCode = authenticationCode.getText().toString();
                    if (TextUtils.isEmpty(mNewTel)) {
                        T.show(this, "请输入需要跟换的手机号码", Toast.LENGTH_LONG);
                        return;
                    }
                    if (!U.isTel(mNewTel)) {
                        T.show(this, "手机号码错误，请重新输入", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (TextUtils.isEmpty(mCode)) {
                        T.show(this, "请输入验证码", Toast.LENGTH_LONG);
                        return;
                    }
                    mProgressSubscriber = new ProgressSubscriber(mChangeTelSubscriberOnNextListener, this, false);
                    param.clear();
                    param.put("m_id", getUserInfo().getM_id());
                    param.put("account", mNewTel);
                    param.put("verify", mCode);
                    HttpMethods.getInstance(this).modifyAccount(mProgressSubscriber, param);
                }
                break;
            case R.id.get_code:
                if (status == 1) {
                    mNewTel = getUserInfo().getAccount();
                } else {
                    mNewTel = authenticationTel.getText().toString();
                }
                if (mNewTel.equals("")) {
                    T.show(this, "请出入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!U.isTel(mNewTel)) {
                    T.show(this, "手机号码错误，请重新输入", Toast.LENGTH_SHORT);
                    return;
                }
                mSendCodeProgressSubscriber = new ProgressSubscriber(mSendCodeSubscriberOnNextListener, this, false);
                param.clear();
                param.put("account", mNewTel);
                if (status == 1) {
                    param.put("type", F.MOD_BIND);
                } else {
                    param.put("type", F.RE_BIND);
                }
                HttpMethods.getInstance(this).sendVerify(mSendCodeProgressSubscriber, param);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            StatusBarUtil.StatusBarLightMode(this);
        }
        ButterKnife.bind(this);
    }
}
