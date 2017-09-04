package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.FindEntity;
import com.android.yzd.been.GoodsListBean;
import com.android.yzd.been.IntegralListBean;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.activity.ConfirmConversionActivity;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.activity.IntegralActivity;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.MessageManagerActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.MyItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class FindFragment extends BaseFragment {

    @BindView(R.id.sc_title)
    TextView scTitle;
    @BindView(R.id.find_message)
    ImageView findMessage;
    @BindView(R.id.top_tools)
    RelativeLayout topTools;
   /* @BindView(R.id.boutique_recycler)
    RecyclerView boutiqueRecycler;
    @BindView(R.id.find_more)
    TextView findMore;*/
    @BindView(R.id.integration_recycler)
    RecyclerView integrationRecycler;


   // CommonAdapter adapter_1;
    CommonAdapter adapter_2;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        //linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //boutiqueRecycler.setLayoutManager(linearLayoutManager);
        Drawable drawable = getResources().getDrawable(android.R.color.white);
        //boutiqueRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.HORIZONTAL, drawable, DensityUtils.dp2px(context, 10)));

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager2.setOrientation(OrientationHelper.VERTICAL);
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        integrationRecycler.setLayoutManager(linearLayoutManager2);
        drawable = getResources().getDrawable(R.color.background);
        integrationRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, 2));
    }


    private void getFindData(String m_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                FindEntity findEntity = gson.fromJson(gson.toJson(o), FindEntity.class);
                //setBoutiqueRecommend(findEntity.getGoods_list());
                setIntegralAdapter(findEntity.getIntegral_list());

                if (findEntity.getNot_read().equals("1")) {
                    findMessage.setImageResource(R.mipmap.home_message_);
                } else {
                    findMessage.setImageResource(R.mipmap.home_message);
                }
            }
        };
        setProgressSubscriber(onNextListener);
        builder.clear();
        builder.addParameter("m_id", m_id);
        HttpMethods.getInstance(context).findIndex(progressSubscriber, builder.bulider());
    }

    //积分
    private void setIntegralAdapter(final List<IntegralListBean> integral_list) {
        adapter_2 = new CommonAdapter<IntegralListBean>(context, R.layout.item_find_2, integral_list) {

            @Override
            protected void convert(ViewHolder holder, IntegralListBean s, final int position) {
                Picasso.with(context).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.find_image));
                holder.setText(R.id.find_title,"有限期至："+ s.getGoods_name());
                holder.setText(R.id.find_content, s.getGoods_brief());
                holder.setText(R.id.find_integral, s.getNeed_integral()+"积分兑换");

                holder.setOnClickListener(R.id.conversion, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getUserInfo() == null) {
                            intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            intent = new Intent(context, ConfirmConversionActivity.class);
                            intent.putExtra(K.DATA, integral_list.get(position));
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        integrationRecycler.setAdapter(adapter_2);
    }

    //精品推荐
   /* private void setBoutiqueRecommend(final List<GoodsListBean> goods_list) {
        adapter_1 = new CommonAdapter<GoodsListBean>(context, R.layout.item_find_1, goods_list) {

            @Override
            protected void convert(ViewHolder holder, GoodsListBean s, int position) {
                View view = holder.getConvertView();
                if (position == 0) {
                    view.setPadding(DensityUtils.dp2px(context, 10), 0, 0, 0);
                }

                Picasso.with(context).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.find_image));

                holder.setText(R.id.find_name, s.getGoods_name());
            }
        };
        adapter_1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, goods_list.get(position).getGoods_id());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        boutiqueRecycler.setAdapter(adapter_1);
    }*/


   //因为改版所以屏蔽了更多R.id.find_more
    @OnClick(R.id.find_message)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
           /* case R.id.find_more:
                if (getUserInfo() == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, IntegralActivity.class);
                    startActivity(intent);
                }

                break;*/
            case R.id.find_message:
                if (getUserInfo() == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), MessageManagerActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getFindData(getUserInfo() == null ? "" : getUserInfo().getM_id());
    }
}
