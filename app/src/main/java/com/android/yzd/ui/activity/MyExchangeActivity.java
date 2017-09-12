package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.MyExchangeBean;
import com.android.yzd.been.MyExchangeList;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.been.UserRegAgr;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 33181 on 2017/9/9.
 */

public class MyExchangeActivity extends BaseActivity {
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

    CommonAdapter itemAdapter;
    List<MyExchangeBean> data=new ArrayList<>();
    UserInfoEntity userInfo;
    MyExchangeList bean;
    private int p=1;
    int lastVisibleItem = -1;
    boolean isData = true;
    @Override
    public int getContentViewId() {
        return R.layout.activity_my_grade;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        userInfo= (UserInfoEntity) SPUtils.get(this, K.USERINFO,UserInfoEntity.class);
        titleBar.setText("我的兑换");
        circleTitle.setText("兑换次数");
        itemTitle.setText("兑换明细");
        rightBar.setOnClickListener(rightClick);
        L.d("TAG","id--"+userInfo.getM_id());
        initList();
        setConvertAdapter();
        getHistory();
    }
    SubscriberOnNextListener onclick;
    View.OnClickListener rightClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getIntegralRule();
        }
    };
    public void getIntegralRule() {
        onclick=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr allRule=gson.fromJson(gson.toJson(o),UserRegAgr.class);
                intent=new Intent(MyExchangeActivity.this,WebView.class);
                intent.putExtra(K.DATA,allRule);
                startActivity(intent);
            }
        };
        setProgressSubscriber(onclick);
        HttpMethods.getInstance(this).getIntegralRule(progressSubscriber);
    }
    private void initList() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration(OrientationHelper.HORIZONTAL, getResources().getDrawable(R.color.background), 1));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == itemAdapter.getItemCount()) {
                    p++;
                    getHistory();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    /*
     * 我的兑换
     */
    private void setConvertAdapter() {
        itemAdapter= new CommonAdapter<MyExchangeBean>(this, R.layout.item_grade_page, data) {
            @Override
            protected void convert(ViewHolder holder, MyExchangeBean exchangeLogBean, int position) {
                holder.setText(R.id.item_grade_text,"兑换"+exchangeLogBean.getCou_value()+"元优惠券");
                holder.setText(R.id.item_grade_time, U.timeStampToStr(exchangeLogBean.getCreate_time()));
                holder.setText(R.id.item_grade_num,"*"+exchangeLogBean.getNum());
            }
        };
        recyclerView.setAdapter(itemAdapter);

    }
    /*
    * 刷新兑换列表
    */
    protected void getHistory(){
        if (isData) {
            SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    bean=gson.fromJson(gson.toJson(o),MyExchangeList.class);
                    data.addAll(bean.getList());
                    itemAdapter.notifyDataSetChanged();
                    setCircleNum();
                    if (bean.getList().size() == 0)
                        isData = false;
                }
            };
            setProgressSubscriber(onNextListener);
            httpParamet.addParameter("m_id", userInfo.getM_id());
            httpParamet.addParameter("p",String.valueOf(p));
            HttpMethods.getInstance(this).getMyExchange(progressSubscriber, httpParamet.bulider());
        }
    }

    protected void setCircleNum(){
        if (bean.getExchange_num()==null){

        }
        int num=Integer.parseInt(bean.getExchange_num());
        if (num<1000){
            circleNum.setText(num+"");
        }else if (num>=1000&&num<10000){
            int value=num%10000;
            circleNum.setText(value+"w");
        }else if (num>=10000&&num<100000){
            int value=num%10000;
            circleNum.setText(value+"w");
        }else if (num>=100000&&num<1000000){
            int value=num%1000000;
            circleNum.setText(value+"mil");
        }else {
            circleNum.setText("NNN");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
