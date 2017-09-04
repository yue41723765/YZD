package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.yzd.R;
import com.android.yzd.been.IntergralHistoryEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
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
 * Created by Administrator on 2016/10/10 0010.
 */
public class ConversionActivity extends BaseActivity {

    @BindView(R.id.conversion_recycler)
    RecyclerView conversionRecycler;

    CommonAdapter adapter;
    UserInfoEntity userInfo;
    int p = 1;
    List<IntergralHistoryEntity> historyEntity = new ArrayList<>();
    int lastVisibleItem = -1;
    boolean isData = true;

    @Override
    public int getContentViewId() {
        return R.layout.activity_conversion;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);

        initList();
        setAdapter();
        getHistory();
    }

    private void initList() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        conversionRecycler.setLayoutManager(linearLayoutManager);
        conversionRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.HORIZONTAL, getResources().getDrawable(R.color.background), 1));

        conversionRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
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

    private void setAdapter() {
        adapter = new CommonAdapter<IntergralHistoryEntity>(this, R.layout.item_history, historyEntity) {
            @Override
            protected void convert(ViewHolder holder, IntergralHistoryEntity s, int position) {
                if (s.getStatus() == 0) {
                    holder.setText(R.id.history_status, "待处理");
                } else {
                    holder.setText(R.id.history_status, "兑换成功");
                }
                holder.setText(R.id.history_name, s.getGoods_name());
                holder.setText(R.id.integral_number, "-" + s.getUse_integral());
                holder.setText(R.id.history_date, U.timeStampToStr(s.getCreate_time()));
            }
        };

        conversionRecycler.setAdapter(adapter);
    }

    private void getHistory() {
        if (isData) {
            SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
                @Override
                public void onNext(Object o) {
                    List<IntergralHistoryEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<IntergralHistoryEntity>>() {
                    }.getType());
                    historyEntity.addAll(list);
                    adapter.notifyDataSetChanged();

                    if (list.size() == 0)
                        isData = false;
                }
            };
            setProgressSubscriber(onNextListener);
            httpParamet.addParameter("m_id", userInfo.getM_id());
            httpParamet.addParameter("p", p + "");
            HttpMethods.getInstance(this).exchangeLog(progressSubscriber, httpParamet.bulider());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
