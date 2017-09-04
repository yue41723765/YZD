package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.IntegralEsEntity;
import com.android.yzd.been.IntegralListBean;
import com.android.yzd.been.QuestionEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class IntegralActivity extends BaseActivity {

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.integral_number)
    TextView integralNumber;
    @BindView(R.id.integral_getIntegral)
    TextView integralGetIntegral;
    @BindView(R.id.integral_history)
    Button integralHistory;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.integral_recycler)
    RecyclerView integralRecycler;


    CommonAdapter adapter_2;
    List<View> views = new ArrayList<>();
    int p = 1;
    UserInfoEntity userinfo;
    List<IntegralListBean> integral_list = new ArrayList<>();
    int lastVisibleItem = -1;
    boolean isData = true;

    @Override
    public int getContentViewId() {
        return R.layout.activiti_integral_ecshop;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        userinfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        initList();
        setAdapter();
        getIntegralData();
    }

    private void initList() {
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        integralRecycler.setLayoutManager(linearLayoutManager2);
        Drawable drawable = getResources().getDrawable(R.color.background);
        integralRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, 2));

        integralRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter_2.getItemCount()) {
                    p++;
                    getIntegralData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager2.findLastVisibleItemPosition();
            }
        });
    }

    private void setAdapter() {
        adapter_2 = new CommonAdapter<IntegralListBean>(this, R.layout.item_find_2, integral_list) {

            @Override
            protected void convert(ViewHolder holder, IntegralListBean s, final int position) {
                Picasso.with(IntegralActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.find_image));
                holder.setText(R.id.find_title, s.getGoods_name());
                holder.setText(R.id.find_content, s.getGoods_brief());
                holder.setText(R.id.find_integral, s.getNeed_integral());

                holder.setOnClickListener(R.id.conversion, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(IntegralActivity.this, ConfirmConversionActivity.class);
                        intent.putExtra(K.DATA, integral_list.get(position));
                        startActivity(intent);
                    }
                });
            }
        };
        integralRecycler.setAdapter(adapter_2);
    }

    private void getIntegralData() {
        if (isData) {
            SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    IntegralEsEntity esEntity = gson.fromJson(gson.toJson(o), IntegralEsEntity.class);
                    integral_list.addAll(esEntity.getIntegral_list());
                    adapter_2.notifyDataSetChanged();
                    if (esEntity.getIntegral_list().size() == 0)
                        isData = false;

                    integralNumber.setText(esEntity.getIntegral() + "");
                }
            };
            setProgressSubscriber(onNextListener);
            httpParamet.addParameter("m_id", userinfo.getM_id());
            httpParamet.addParameter("p", p + "");
            HttpMethods.getInstance(this).integralShop(progressSubscriber, httpParamet.bulider());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.integral_getIntegral:
//                intent = new Intent(this, IntegralQuestionActivity.class);
//                startActivity(intent);
                getQuestionList();
                break;
            case R.id.integral_history:
                intent = new Intent(this, ConversionActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getQuestionList() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                QuestionEntity questionEntity = gson.fromJson(gson.toJson(o), QuestionEntity.class);
                if (questionEntity != null) {
                    intent = new Intent(IntegralActivity.this, WebView.class);
                    intent.putExtra(K.DATA, questionEntity);
                    startActivity(intent);
                }
            }
        };
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(this).scoreProblem(progressSubscriber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
