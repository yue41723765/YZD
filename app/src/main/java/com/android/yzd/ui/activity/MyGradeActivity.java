package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.MyDegreeBean;
import com.android.yzd.been.MyDegreeList;
import com.android.yzd.been.MyExchangeBean;
import com.android.yzd.been.MyExchangeList;
import com.android.yzd.been.MyIntegralBean;
import com.android.yzd.been.MyIntegralList;
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
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    CommonAdapter itemAdapter;
    String choose;

    List<MyExchangeBean> exchangeData=new ArrayList<>();
    List<MyIntegralBean> integralData=new ArrayList<>();
    List<MyDegreeBean> degreeData=new ArrayList<>();

    MyExchangeList exchangeList;
    MyIntegralList integralList;
    MyDegreeList degreeList;

    UserInfoEntity userInfo;
    private int p=1;
    int lastVisibleItem = -1;
    boolean isData = true;

    SubscriberOnNextListener integralShopOnclick;
    @Override
    public int getContentViewId() {
        return R.layout.activity_my_grade;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        intent=getIntent();
        choose=intent.getStringExtra("CHOOSE");
        rightBar.setOnClickListener(rightClick);
        userInfo= (UserInfoEntity) SPUtils.get(this, K.USERINFO,UserInfoEntity.class);
        initList();
        initChange();
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
                    if ("Convert".equals(choose)){
                        refreshExchangeList();
                    }else if ("Integral".equals(choose)){
                        refreshIntegralList();
                    }else if ("Grade".equals(choose)){
                        refreshDegreeList();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private void initChange() {
        if ("Convert".equals(choose)){
            titleBar.setText("我的兑换");
            circleTitle.setText("兑换次数");
            itemTitle.setText("兑换明细");
            setConvertAdapter();
            refreshExchangeList();
        }else if ("Integral".equals(choose)){
            titleBar.setText("我的积分");
            circleTitle.setText("当前积分");
            itemTitle.setText("积分明细");
            setIntegralAdapter();
            refreshIntegralList();
        }else if ("Grade".equals(choose)){
            titleBar.setText("我的等级");
            circleTitle.setText("当前等级");
            itemTitle.setText("等级经验");
            setGradeAdapter();
            refreshDegreeList();
        }
    }

    /*
    * 刷新兑换列表
    */
    protected void refreshExchangeList(){
        if (isData) {
            integralShopOnclick = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    exchangeList = gson.fromJson(gson.toJson(o), MyExchangeList.class);
                    exchangeData = exchangeList.getList();
                    itemAdapter.notifyDataSetChanged();
                    setCircleNum(exchangeList.getExchange_num());
                    if (exchangeList.getList().size() == 0)
                        isData = false;
                }
            };
            setProgressSubscriber(integralShopOnclick);
            httpParamet.clear();
            httpParamet.addParameter("m_id", userInfo.getM_id());
            httpParamet.addParameter("p",String.valueOf(p));
            HttpMethods.getInstance(this).getMyExchange(progressSubscriber, httpParamet.bulider());
        }
    }

    /*
    * 刷新积分列表
    */
    protected void refreshIntegralList(){
        if (isData) {
            integralShopOnclick = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    integralList = gson.fromJson(gson.toJson(o), MyIntegralList.class);
                    integralData = integralList.getList();
                    itemAdapter.notifyDataSetChanged();
                    setCircleNum(integralList.getIntegral());
                    if (integralList.getList().size() == 0)
                        isData = false;
                }
            };
            setProgressSubscriber(integralShopOnclick);
            httpParamet.clear();
            httpParamet.addParameter("m_id", userInfo.getM_id());
            httpParamet.addParameter("p",String.valueOf(p));
            HttpMethods.getInstance(this).getMyIntegral(progressSubscriber, httpParamet.bulider());
        }
    }

    /*
     * 刷新等级列表
     */
    protected void refreshDegreeList(){
        if (isData) {
            integralShopOnclick = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    degreeList = gson.fromJson(gson.toJson(o), MyDegreeList.class);
                    degreeData = degreeList.getList();
                    itemAdapter.notifyDataSetChanged();
                    circleNum.setText(degreeList.getDegree());
                    if (degreeList.getList().size() == 0)
                        isData = false;
                }
            };
            setProgressSubscriber(integralShopOnclick);
            httpParamet.clear();
            httpParamet.addParameter("m_id", userInfo.getM_id());
            httpParamet.addParameter("p",String.valueOf(p));
            HttpMethods.getInstance(this).getMyDegree(progressSubscriber, httpParamet.bulider());
        }
    }


    /*
    * 我的兑换
    */
    private void setConvertAdapter() {
        itemAdapter= new CommonAdapter<MyExchangeBean>(this, R.layout.item_grade_page, exchangeData) {
            @Override
            protected void convert(ViewHolder holder, MyExchangeBean exchangeLogBean, int position) {
                holder.setText(R.id.item_grade_text,"兑换"+exchangeLogBean.getCou_value()+"元优惠券");
                holder.setText(R.id.item_grade_time,U.timeStampToStr(exchangeLogBean.getCreate_time()));
                holder.setText(R.id.item_grade_num,"*"+exchangeLogBean.getNum());
            }
        };
        recyclerView.setAdapter(itemAdapter);

    }

    /*
    * 我的积分
    */
    private void setIntegralAdapter() {
        itemAdapter=new CommonAdapter<MyIntegralBean>(this,R.layout.item_grade_page,integralData) {
            @Override
            protected void convert(ViewHolder holder, MyIntegralBean exchangeLogBean, int position) {
                holder.setText(R.id.item_grade_text,exchangeLogBean.getDescription());
                if ("0".equals(exchangeLogBean.getSymbol())){
                    holder.setText(R.id.item_grade_num,"-"+exchangeLogBean);
                }else {
                    holder.setText(R.id.item_grade_num,"+"+exchangeLogBean);
                }
                holder.setText(R.id.item_grade_time,U.timeStampToStr(exchangeLogBean.getCreate_time()));
            }
        };
        recyclerView.setAdapter(itemAdapter);
    }
    /*
    * 经验等级
    */
    private void setGradeAdapter() {

        itemAdapter=new CommonAdapter<MyDegreeBean>(this,R.layout.item_grade_page,degreeData) {
            @Override
            protected void convert(ViewHolder holder, MyDegreeBean s, int position) {
                if ("0".equals(s.getSymbol())){
                    holder.setText(R.id.item_grade_num,"-"+s.getExp_num());
                }else {
                    holder.setText(R.id.item_grade_num,"+"+s.getExp_num());
                }
                holder.setText(R.id.item_grade_text,s.getDescription());
                holder.setText(R.id.item_grade_time,U.timeStampToStr(s.getCreate_time()));
            }
        };
        recyclerView.setAdapter(itemAdapter);
    }
    /*
    *
    * 显示积分或兑换次数过多
    */
    protected void setCircleNum(String number){
            int num = Integer.parseInt(number);
            if (num < 1000) {
                circleNum.setText(num + "");
            } else if (num >= 1000 && num < 10000) {
                int value = num / 10000;
                circleNum.setText(value + "k");
            } else if (num >= 10000 && num < 100000) {
                int value = num / 10000;
                circleNum.setText(value + "w");
            } else if (num >= 100000 && num < 1000000) {
                int value = num / 100000;
                circleNum.setText(value + "w");
            }else if (num >= 1000000 && num < 10000000){
                int value = num / 1000000;
                circleNum.setText(value + "mil");
            }else {
                circleNum.setText("NNN");
            }
    }

    /*
    * 规则的跳转
    */
    SubscriberOnNextListener onclick;
    View.OnClickListener rightClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ("Convert".equals(choose)){
                getIntegralRule();
            }else if ("Integral".equals(choose)){
                getIntegralRule();
            }else if ("Grade".equals(choose)){
                getDegreeRule();
            }

        }
    };
    public void getDegreeRule() {
        onclick=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr allRule=gson.fromJson(gson.toJson(o),UserRegAgr.class);
                initData(allRule);
            }
        };
        setProgressSubscriber(onclick);
        HttpMethods.getInstance(this).getDegreeRule(progressSubscriber);

    }
    public void getIntegralRule() {
        onclick=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr allRule=gson.fromJson(gson.toJson(o),UserRegAgr.class);
                initData( allRule);
            }
        };
        setProgressSubscriber(onclick);
        HttpMethods.getInstance(this).getIntegralRule(progressSubscriber);
    }
    private void initData(UserRegAgr data) {
        intent=new Intent(MyGradeActivity.this,WebView.class);
        intent.putExtra(K.DATA,data);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
