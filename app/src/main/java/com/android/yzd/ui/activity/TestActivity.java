package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;

/**
 * Created by 33181 on 2017/9/4.
 */

public class TestActivity extends BaseActivity  {
    @BindView(R.id.pay_result_tv)TextView test;
    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        intent=getIntent();
        String aaa=intent.getStringExtra("PASSWORD");
        test.setText(aaa);
    }
}
