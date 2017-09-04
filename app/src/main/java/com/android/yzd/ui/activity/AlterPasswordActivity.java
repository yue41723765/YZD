package com.android.yzd.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class AlterPasswordActivity extends BaseActivity {

    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.again_new_password)
    EditText againNewPassword;
    @BindView(R.id.sure)
    Button sure;

    SubscriberOnNextListener mSubscriberOnNextListener;
    ProgressSubscriber mProgressSubscriber;
    Map<String, String> param = new HashMap<>();
    UserInfoEntity mUserInfoEntity;

    @Override
    public int getContentViewId() {
        return R.layout.activity_alter_password;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        mUserInfoEntity = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        mSubscriberOnNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(AlterPasswordActivity.this, "密码修改成功", Toast.LENGTH_LONG);
                finish();
            }
        };

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sure:
                String old = oldPassword.getText().toString();
                String new_ = newPassword.getText().toString();
                String again = againNewPassword.getText().toString();
                if (TextUtils.isEmpty(old)) {
                    T.show(this, "请您输入原密码", Toast.LENGTH_LONG);
                    return;
                }
                if (TextUtils.isEmpty(new_)) {
                    T.show(this, "请您输入新密码", Toast.LENGTH_LONG);
                    return;
                }
                if (TextUtils.isEmpty(again)) {
                    T.show(this, "请您再次输入新密码", Toast.LENGTH_LONG);
                    return;
                }
                if (!new_.equals(again)) {
                    T.show(this, "两次输入的密码不相同，请重新输入", Toast.LENGTH_LONG);
                    return;
                }
                mProgressSubscriber = new ProgressSubscriber(mSubscriberOnNextListener, this, false);
                param.clear();
                param.put("password", old);
                param.put("m_id", mUserInfoEntity.getM_id());
                param.put("new_password", new_);
                HttpMethods.getInstance(this).modifyPassword(mProgressSubscriber, param);
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
