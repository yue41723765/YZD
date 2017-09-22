package com.android.yzd.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.GoodsInfoEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class CollectActivity extends BaseActivity {


    @BindView(R.id.collect_recycler)
    RecyclerView collectRecycler;

    CommonAdapter adapter;

    UserInfoEntity userInfo;
    List<GoodsInfoEntity> goodsList = new ArrayList<>();
    @BindView(R.id.not_data)
    ImageView notData;

    @Override
    public int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);


        init();
        setAdapter();
        getCollectData(userInfo.getM_id());
    }

    private void setAdapter() {
        adapter = new CommonAdapter<GoodsInfoEntity>(this, R.layout.item_collect, goodsList) {
            @Override
            protected void convert(ViewHolder holder, final GoodsInfoEntity s, final int position) {
                Picasso.with(CollectActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.collect_image));
                holder.setText(R.id.collect_title, s.getGoods_name());
                holder.setText(R.id.collect_number, s.getSales() + "人购买");
                holder.setText(R.id.collect_price, "￥" + s.getGoods_price());
                holder.setOnClickListener(R.id.cancel_collect, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(CollectActivity.this)
                                .setTitle("提示")
                                .setMessage("是否取消收藏?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delCollect(s.getGoods_id(), position);
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.create().show();
                    }
                });
                holder.setOnClickListener(R.id.add_shoppingCart, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCart(s.getGoods_id());
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(CollectActivity.this, DetailsActivity.class);
                intent.putExtra(K.GOODS_ID, goodsList.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        collectRecycler.setAdapter(adapter);
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        collectRecycler.setLayoutManager(layoutManager);
        collectRecycler.setItemAnimator(new DefaultItemAnimator());
        collectRecycler.addItemDecoration(new RecyclerViewDivider(this, OrientationHelper.VERTICAL));
    }

    private void getCollectData(String m_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                goodsList.clear();
                adapter.notifyDataSetChanged();
                List<GoodsInfoEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<GoodsInfoEntity>>() {
                }.getType());
                goodsList.addAll(list);
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    notData.setVisibility(View.VISIBLE);
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("m_id", m_id);
        HttpMethods.getInstance(this).collectList(progressSubscriber, httpParamet.bulider());
    }


    private void delCollect(String goods_id, final int position) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                goodsList.remove(position);
                adapter.notifyDataSetChanged();
                T.show(CollectActivity.this, "取消收藏成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("goods_id", goods_id);
        httpParamet.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).deleteCollect(progressSubscriber, httpParamet.bulider());

    }

    private void addCart(String goods_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(CollectActivity.this, "添加购物车成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("m_id", userInfo.getM_id());
        httpParamet.addParameter("goods_id", goods_id);
        HttpMethods.getInstance(this).addCart(progressSubscriber, httpParamet.bulider());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
