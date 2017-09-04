package com.android.yzd.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.AddressEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.titleBar_right_text)
    TextView title_right;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edit_address)
    EditText editAddress;

    UserInfoEntity userInfo;
    int status;
    AddressEntity address;

    @Override
    public int getContentViewId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        status = getIntent().getExtras().getInt(K.STATUS);//1------新增 2----编辑
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        switch (status) {
            case 1:
                title_right.setVisibility(View.GONE);
                break;
            case 2:
                address = getIntent().getExtras().getParcelable(K.DATA);
                editName.setText(address.getConsignee());
                editTel.setText(address.getMobile());
                editAddress.setText(address.getAddress());
                break;
        }
    }


    @OnClick({R.id.titleBar_right_text})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleBar_right_text:
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("是否删除收货地址?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delAddress();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

                break;
            case R.id.edit_address_save:
                String name = editName.getText().toString();
                String tel = editTel.getText().toString();
                String address = editAddress.getText().toString();
                if (name.equals("")) {
                    T.show(this, "请输入收货人", Toast.LENGTH_SHORT);
                    return;
                }
                if (tel.equals("")) {
                    T.show(this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (address.equals("")) {
                    T.show(this, "请输入详细地址", Toast.LENGTH_SHORT);
                    return;
                }
                if (!U.isTel(tel)) {
                    T.show(this, "手机号码格式错误", Toast.LENGTH_SHORT);
                    return;
                }
                params.clear();
                if (status == 1) {
                    params.put("m_id", userInfo.getM_id());
                } else {
                    params.put("address_id", this.address.getAddress_id());
                }
                params.put("consignee", name);
                params.put("mobile", tel);
                params.put("address", address);
                if (status == 1) {
                    addAddress();
                } else {
                    editAddress();
                }
                break;
        }
    }

    //添加地址
    private void addAddress() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(EditAddressActivity.this, "新增收货地址成功", Toast.LENGTH_SHORT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 500);
            }
        };
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(this).addAddress(progressSubscriber, params);
    }

    //编辑地址
    private void editAddress() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(EditAddressActivity.this, "修改收货地址成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(this).modifyAddress(progressSubscriber, params);
    }

    //删除地址
    private void delAddress() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                finish();
                T.show(EditAddressActivity.this, "删除收货地址成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("address_id", address.getAddress_id());
        HttpMethods.getInstance(this).deleteAddress(progressSubscriber, params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
