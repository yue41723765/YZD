package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/9 0009.
 * 账单明细
 */
public class BillDetailActivity extends BaseActivity {

    @BindView(R.id.billDetail_recycler)
    RecyclerView billDetailRecycler;
    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_bill_detail;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        billDetailRecycler.setLayoutManager(linearLayoutManager);
        billDetailRecycler.addItemDecoration(new RecyclerViewDivider(this, OrientationHelper.HORIZONTAL, 2, getResources().getColor(R.color.background)));

        adapter = new CommonAdapter<String>(this, R.layout.item_bill_detail, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

                TextView money = holder.getView(R.id.billDetail_money);
                if (position % 2 == 1) {
                    money.setTextColor(getResources().getColor(R.color.red_30));
                }

            }
        };
        billDetailRecycler.setAdapter(adapter);
    }


}
