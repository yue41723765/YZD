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
import com.android.yzd.been.SystemMessEntity;
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
 * Created by Administrator on 2016/10/11 0011.
 */
public class SystemMessageActivity extends BaseActivity {
    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.message_recycler)
    RecyclerView messageRecycler;


    CommonAdapter adapter;

    SubscriberOnNextListener onNextListener;
    ProgressSubscriber progressSubscriber;

    Map<String, String> params = new HashMap<>();


    List<SystemMessEntity> messageEntities = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        params.put("m_id", getUserInfo().getM_id());
        params.put("p", "1");
        onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                List<SystemMessEntity> messageEntities = gson.fromJson(gson.toJson(o), new TypeToken<List<SystemMessEntity>>() {
                }.getType());
                SystemMessageActivity.this.messageEntities.addAll(messageEntities);
                adapter.notifyDataSetChanged();
            }
        };
        progressSubscriber = new ProgressSubscriber(onNextListener, this);
        HttpMethods.getInstance(this).systemMessageList(progressSubscriber, params);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messageRecycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new CommonAdapter<SystemMessEntity>(this, R.layout.item_system_message, messageEntities) {

            @Override
            protected void convert(ViewHolder holder, SystemMessEntity info, final int position) {
                holder.setText(R.id.item_date, U.timeStampToStr(info.getCreate_time()));
                holder.setText(R.id.item_date, U.timeStampToStr(info.getCreate_time()));
                holder.setText(R.id.message_title, info.getTitle());
                holder.setText(R.id.message_content, info.getContent());

                if (info.getStatus().equals("1")) {
                    holder.setTextColor(R.id.message_title, R.color.black_90);
                    holder.setTextColor(R.id.message_content, R.color.black_90);
                    holder.setTextColor(R.id.item_date, R.color.black_90);
                }


                TextView textView = holder.getView(R.id.details);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(SystemMessageActivity.this, WebView.class);
                        intent.putExtra(K.DATA, messageEntities.get(position));
                        startActivity(intent);
                    }
                });
            }
        };
        messageRecycler.setAdapter(adapter);
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
