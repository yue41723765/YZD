package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class BankCardTypeActivity extends BaseActivity {

    @BindView(R.id.bankCardType_recycler)
    RecyclerView bankCardTypeRecycler;
    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_bank_type;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        bankCardTypeRecycler.setLayoutManager(linearLayoutManager);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        adapter = new CommonAdapter<String>(this, R.layout.item_bankcardtype, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        bankCardTypeRecycler.setAdapter(adapter);
    }

}
