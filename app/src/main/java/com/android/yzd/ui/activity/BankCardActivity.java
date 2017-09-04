package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.bankCard_recycler)
    RecyclerView bankCardRecycler;

    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_bank_card;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        bankCardRecycler.setLayoutManager(linearLayoutManager);
        Drawable drawable = getResources().getDrawable(R.color.background);
        bankCardRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, DensityUtils.dp2px(this, 10)));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        adapter = new CommonAdapter<String>(this, R.layout.item_bank_card, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        bankCardRecycler.setAdapter(adapter);
    }


    @OnClick({R.id.titleBar_right_text})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleBar_right_text:
                intent = new Intent(this, AddBankActivity.class);
                startActivity(intent);
                break;
        }
    }
}
