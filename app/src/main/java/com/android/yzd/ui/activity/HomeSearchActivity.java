package com.android.yzd.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.HotEntity;
import com.android.yzd.been.GoodsInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class HomeSearchActivity extends BaseActivity {

    @BindView(R.id.hot_recycler)
    RecyclerView hotRecycler;
    @BindView(R.id.history_recycler)
    RecyclerView historyRecycler;
    @BindView(R.id.history_clear)
    TextView historyClear;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.searchEdit)
    EditText searchEdit;

    CommonAdapter hotAdapter;
    CommonAdapter historyAdapter;
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    Set<String> history;


    List<GoodsInfoEntity> dataList = new ArrayList<>();
    @BindView(R.id.mode_1)
    LinearLayout mode1;
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;


    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        shared = getSharedPreferences(K.USERINFO, 0);
        editor = shared.edit();
        getHotData();


        setHistoryDate();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        searchRecycler.setLayoutManager(manager);
    }

    private void setHistory() {
        history = shared.getStringSet(K.HISTORY, new HashSet<String>());
        final List<String> history = new ArrayList<>();
        for (String str : this.history) {
            history.add(str);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        historyRecycler.setLayoutManager(layoutManager);

        historyAdapter = new CommonAdapter<String>(this, R.layout.item_home_search_history, history) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.history_text, s);
            }
        };
        historyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                searchGoods(history.get(position));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        historyRecycler.setAdapter(historyAdapter);

    }

    //获取历史记录
    private void setHistoryDate() {
        history = shared.getStringSet(K.HISTORY, new HashSet<String>());
        L.i(history.toString());
        if (history.size() > 0) {
            historyClear.setVisibility(View.VISIBLE);
            setHistory();
        } else {
            historyClear.setVisibility(View.GONE);
        }

    }

    private void addHistory(String history) {
        if (this.history.size() >= 5) {
            this.history.remove(0);
        }
        this.history.add(history);
        editor.putStringSet(K.HISTORY, this.history);
        editor.commit();
        setHistoryDate();
    }


    private void getHotData() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                List<HotEntity> hotEntities = gson.fromJson(gson.toJson(o), new TypeToken<List<HotEntity>>() {
                }.getType());
                setAdapter(hotEntities);
            }
        };
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(this).hotSearch(progressSubscriber);
    }

    private void setAdapter(final List<HotEntity> hotEntities) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        hotRecycler.setLayoutManager(gridLayoutManager);
        hotRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 2, 0, 0));

        hotAdapter = new CommonAdapter<HotEntity>(this, R.layout.item_home_search_hot, hotEntities) {

            @Override
            protected void convert(ViewHolder holder, HotEntity s, int position) {
                holder.setText(R.id.hot_text, s.getSearch_phrases());
            }
        };
        hotAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                addHistory(hotEntities.get(position).getSearch_phrases());
                searchGoods(hotEntities.get(position).getSearch_phrases());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        hotRecycler.setAdapter(hotAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search:
                String search = searchEdit.getText().toString();
                if (!search.equals("")) {
                    addHistory(search);
                    searchGoods(search);
                } else {
                    T.show(this, "请输入搜索关键字", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.history_clear:
                editor.putStringSet(K.HISTORY, new HashSet<String>());
                editor.commit();
                setHistory();
                break;
        }
    }

    private void searchGoods(String key) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                List<GoodsInfoEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<GoodsInfoEntity>>() {
                }.getType());
                if (list.size() > 0)
                    setSearchAdapter(list);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("keywords", key);
        HttpMethods.getInstance(this).searchResult(progressSubscriber, httpParamet.bulider());
    }

    private void setSearchAdapter(final List<GoodsInfoEntity> list) {
        mode1.setVisibility(View.GONE);
        searchRecycler.setVisibility(View.VISIBLE);
        CommonAdapter adapter = new CommonAdapter<GoodsInfoEntity>(this, R.layout.item_hot_1, list) {

            @Override
            protected void convert(ViewHolder holder, GoodsInfoEntity s, int position) {
                Picasso.with(HomeSearchActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_image));
                holder.setText(R.id.hot_title, s.getGoods_name());
                holder.setText(R.id.hot_price, "￥" + s.getGoods_price());
                holder.setText(R.id.hot_number, s.getSales() + "人付款");
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(HomeSearchActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(HomeSearchActivity.this, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, list.get(position).getGoods_id());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        searchRecycler.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
