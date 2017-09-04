package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 33181 on 2017/8/30.
 */

public class MyGradeActivity extends BaseActivity {

    @BindView(R.id.grade_circle_title)
    TextView circleTitle;
    @BindView(R.id.grade_num)
    TextView circleNum;
    @BindView(R.id.grade_item_title)
    TextView itemTitle;
    @BindView(R.id.grade_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.titleBar_title)
    TextView titleBar;
    @BindView(R.id.titleBar_right_text)
    TextView rightBar;
    CommonAdapter<Integer> itemAdapter;
    String choose;
    List<Integer> data;
    String rule;
    @Override
    public int getContentViewId() {
        return R.layout.activity_my_grade;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        intent=getIntent();
        choose=intent.getStringExtra("CHOOSE");
        if ("Convert".equals(choose)){
            titleBar.setText("我的兑换");
            circleTitle.setText("兑换次数");
            itemTitle.setText("兑换明细");
            setConvertAdapter();
        }else if ("Integral".equals(choose)){
            titleBar.setText("我的积分");
            circleTitle.setText("兑换次数");
            itemTitle.setText("积分明细");
            setIntegralAdapter();
        }else if ("Grade".equals(choose)){
            titleBar.setText("我的等级");
            circleTitle.setText("当前等级");
            itemTitle.setText("等级经验");
            setGradeAdapter();
        }

        rightBar.setOnClickListener(rightClick);
    }

    View.OnClickListener rightClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent=new Intent();
            intent.putExtra("RULE",rule);
            startActivity(intent);
        }
    };
    /*
    * 我的兑换
    */
    private void setConvertAdapter() {
        itemAdapter=new CommonAdapter<Integer>(this,R.layout.item_grade_page,data) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setText(R.id.item_grade_text,"下单购买产品获得经验+"+"");
                holder.setText(R.id.item_grade_time,"");
                holder.setText(R.id.item_grade_num,"+"+"");
            }
        };
        recyclerView.setAdapter(itemAdapter);
    }

    /*
    * 我的积分
    */
    private void setIntegralAdapter() {
        itemAdapter=new CommonAdapter<Integer>(this,R.layout.item_grade_page,data) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setText(R.id.item_grade_text,"下单购买产品获得积分+"+"");
                holder.setText(R.id.item_grade_time,"");
                holder.setText(R.id.item_grade_num,"+"+"");
            }
        };
        recyclerView.setAdapter(itemAdapter);
    }
    /*
    * 经验等级
    */
    private void setGradeAdapter() {

        itemAdapter=new CommonAdapter<Integer>(this,R.layout.item_grade_page,data) {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setText(R.id.item_grade_text,"兑换十元代金券"+"");
                holder.setText(R.id.item_grade_time,"");
                holder.setText(R.id.item_grade_num,"+"+"");
            }
        };
        recyclerView.setAdapter(itemAdapter);
    }

}
