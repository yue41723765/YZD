package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class WalletActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.titleBar_right_text})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleBar_right_text:
                intent = new Intent(this, BillDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.wallet_recharge:
                intent = new Intent(this, RechargeActivity.class);
                intent.putExtra(K.STATUS, 1);
                startActivity(intent);
                break;
            case R.id.withdraw_deposit:
                intent = new Intent(this, RechargeActivity.class);
                intent.putExtra(K.STATUS, 2);
                startActivity(intent);
                break;
        }
    }
}
