package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.MessageEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class OrderMessageActivity extends BaseActivity {
    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.message_recycler)
    RecyclerView messageRecycler;


    CommonAdapter adapter;

    SubscriberOnNextListener onNextListener;
    ProgressSubscriber progressSubscriber;

    Map<String, String> params = new HashMap<>();


    List<MessageEntity> messageEntities = new ArrayList<>();
    int p = 1;
    int lastVisibleItem = -1;
    boolean isData = true;

    @Override
    public int getContentViewId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        getMessageList();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messageRecycler.setItemAnimator(new DefaultItemAnimator());

        messageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    p++;
                    getMessageList();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


        setAdapter();

    }

    private void setAdapter() {
        adapter = new CommonAdapter<MessageEntity>(this, R.layout.item_order_message, messageEntities) {

            @Override
            protected void convert(ViewHolder holder, MessageEntity info, final int position) {

                holder.setText(R.id.item_date, U.timeStampToStr(info.getCreate_time()));
                holder.setText(R.id.order_time, U.timeStampToStr(info.getOrder_create_time()));

                holder.setText(R.id.message_title, info.getTitle());
                holder.setText(R.id.order_number, info.getOrder_sn());
                holder.setText(R.id.order_address, info.getContent());

                TextView textView = holder.getView(R.id.details);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(OrderMessageActivity.this, SystemDetailsActivity.class);
                        intent.putExtra(K.DATA, messageEntities.get(position));
                        startActivity(intent);
                    }
                });
                if (info.getStatus().equals("1")) {
                    holder.setTextColor(R.id.order_time, R.color.black_90);
                    holder.setTextColor(R.id.order_number, R.color.black_90);
                    holder.setTextColor(R.id.message_title, R.color.black_90);
                    holder.setTextColor(R.id.order_address, R.color.black_90);
                    holder.setTextColor(R.id.title_3, R.color.black_90);
                    holder.setTextColor(R.id.title_2, R.color.black_90);
                    holder.setTextColor(R.id.title_1, R.color.black_90);
                }

            }
        };
        messageRecycler.setAdapter(adapter);
    }


    private void getMessageList() {
        if (isData) {
            params.put("m_id", getUserInfo().getM_id());
            params.put("p", p + "");

            onNextListener = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    if (p == 1)
                        OrderMessageActivity.this.messageEntities.clear();
                    List<MessageEntity> messageEntities = gson.fromJson(gson.toJson(o), new TypeToken<List<MessageEntity>>() {
                    }.getType());

                    if (messageEntities.size() == 0)
                        isData = false;
                    OrderMessageActivity.this.messageEntities.addAll(messageEntities);
                    adapter.notifyDataSetChanged();
                }
            };
            progressSubscriber = new ProgressSubscriber(onNextListener, this);
            HttpMethods.getInstance(this).orderNoticeList(progressSubscriber, params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            StatusBarUtil.StatusBarLightMode(this);
        }
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
