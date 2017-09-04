package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.android.yzd.R;
import com.android.yzd.been.CouponEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class DiscountCouponActivity extends BaseActivity {

    @BindView(R.id.not_discount_coupon)
    LinearLayout notDiscountCoupon;
    @BindView(R.id.discount_coupon_recycler)
    RecyclerView discountCouponRecycler;

    CommonAdapter adapter;

    int p = 1;
    UserInfoEntity userInfo;

    List<CouponEntity> couponList = new ArrayList<>();
    int lastVisibleItem = -1;
    int status = 0;
    boolean isData = true;

    @Override
    public int getContentViewId() {
        return R.layout.activity_discount_coupon;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        try {
            status = getIntent().getIntExtra(K.STATUS, 0);
        } catch (Exception e) {
        }
        getData();

        initList();
        discountCouponRecycler.setVisibility(View.VISIBLE);

    }

    private void initList() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        discountCouponRecycler.setLayoutManager(linearLayoutManager);
        discountCouponRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommonAdapter<CouponEntity>(this, R.layout.item_coupon, couponList) {
            @Override
            protected void convert(ViewHolder holder, CouponEntity entity, int position) {
                holder.setText(R.id.coupon_name, entity.getTitle());
                holder.setText(R.id.coupon_validity, "有效期至：" + U.timeStampToStr_(entity.getEnd_time()));
                holder.setText(R.id.coupon_price, entity.getValue());
                holder.setText(R.id.coupon_full, "满" + entity.getCondition() + "使用");
                holder.setText(R.id.coupon_user, "仅限" + entity.getAccount() + "使用");

                switch (entity.getIs_use()) {
                    case "0":
                        long end_time = Long.valueOf(entity.getEnd_time());
                        if (end_time < System.currentTimeMillis() / 1000) {
                            holder.setImageResource(R.id.coupon_top, R.mipmap.coupon_top_2);
                            holder.setVisible(R.id.coupon_status, true);
                            holder.setImageResource(R.id.coupon_status, R.mipmap.have_expired);
                            holder.setTextColor(R.id.coupon_name, getResources().getColor(R.color.black_90));
                            holder.setTextColor(R.id.coupon_validity, getResources().getColor(R.color.black_90));
                            holder.setTextColor(R.id.coupon_price_title, getResources().getColor(R.color.black_90));
                            holder.setTextColor(R.id.coupon_price, getResources().getColor(R.color.black_90));
                            holder.setTextColor(R.id.coupon_full, getResources().getColor(R.color.black_90));
                            holder.setTextColor(R.id.coupon_user, getResources().getColor(R.color.black_90));
                        }
                        break;
                    case "1":
                        holder.setImageResource(R.id.coupon_top, R.mipmap.coupon_top_2);
                        holder.setVisible(R.id.coupon_status, true);
                        holder.setTextColor(R.id.coupon_name, getResources().getColor(R.color.black_90));
                        holder.setTextColor(R.id.coupon_validity, getResources().getColor(R.color.black_90));
                        holder.setTextColor(R.id.coupon_price_title, getResources().getColor(R.color.black_90));
                        holder.setTextColor(R.id.coupon_price, getResources().getColor(R.color.black_90));
                        holder.setTextColor(R.id.coupon_full, getResources().getColor(R.color.black_90));
                        holder.setTextColor(R.id.coupon_user, getResources().getColor(R.color.black_90));
                        break;
                }


            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (status == 111) {
                    if (couponList.get(position).getIs_use().equals("0")) {
                        long end_time = Long.valueOf(couponList.get(position).getEnd_time());
                        if (end_time > System.currentTimeMillis() / 1000) {
                            intent = new Intent();
                            intent.putExtra(K.DATA, couponList.get(position));
                            setResult(200, intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        discountCouponRecycler.setAdapter(adapter);


        discountCouponRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter.getItemCount()) {
                    p++;
                    getData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    private void getData() {
        if (!isData)
            return;
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                L.i(gson.toJson(o));
                List<CouponEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<CouponEntity>>() {
                }.getType());
                couponList.addAll(list);
                adapter.notifyDataSetChanged();
                if (couponList.size() == 0) {
                    notDiscountCoupon.setVisibility(View.VISIBLE);
                }
                if (list.size() == 0) {
                    isData = false;
                }
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("m_id", userInfo.getM_id());
        params.put("p", p + "");
        HttpMethods.getInstance(this).couponList(progressSubscriber, params);
    }

}
