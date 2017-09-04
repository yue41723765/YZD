package com.android.yzd.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.AdvertListBean;
import com.android.yzd.been.GoodGoodsListBean;
import com.android.yzd.been.HomeDataEntity;
import com.android.yzd.been.RecommendGoodsListBean;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.ScreenUtils;
import com.android.yzd.tools.update.DownLoadService;
import com.android.yzd.tools.update.Version;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.activity.HomeSearchActivity;
import com.android.yzd.ui.activity.HotActivity;
import com.android.yzd.ui.activity.IntegralActivity;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.MainActivity;
import com.android.yzd.ui.activity.MessageManagerActivity;
import com.android.yzd.ui.activity.OrderActivity;
import com.android.yzd.ui.activity.SetActivity;
import com.android.yzd.ui.adapter.NetworkImageHolderView;
import com.android.yzd.ui.adapter.ViewPagerAdapter;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.AutoScrollViewPager;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class HomeFragment extends BaseFragment {

    HomeDataEntity homeData;
    @BindView(R.id.home_message)
    ImageView homeMessage;
    @BindView(R.id.home_search)
    RelativeLayout homeSearch;
    @BindView(R.id.home_viewPage)
    ConvenientBanner<String> homeViewPage;
/*    @BindView(R.id.home_viewPage)
    AutoScrollViewPager homeViewPage;*/
 /*   @BindView(R.id.home_circle)
    CircleIndicator homeCircle;*/
    @BindView(R.id.home_hot)
    TextView homeHot;
    @BindView(R.id.home_recommend)
    TextView homeRecommend;
    @BindView(R.id.home_favorable)
    TextView homeFavorable;
    @BindView(R.id.home_integral)
    TextView homeIntegral;
    @BindView(R.id.optimization_goods)
    ImageView optimizationGoods;
    @BindView(R.id.home_recommend_recycler)
    RecyclerView homeRecommendRecycler;


    CommonAdapter itemAdapter2;

    List<String> views = new ArrayList<>();
    int width;
    @BindView(R.id.home_img_1)
    ImageView homeImg1;
    @BindView(R.id.home_img_2)
    ImageView homeImg2;
    @BindView(R.id.home_img_3)
    ImageView homeImg3;
    @BindView(R.id.home_img_4)
    ImageView homeImg4;
    RecyclerViewItemDecoration itemDecoration;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        width = ScreenUtils.getScreenWidth(getContext());
        itemDecoration = new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), DensityUtils.dp2px(context, 3), 0, 0);

    }


    @Override
    public void onResume() {
        super.onResume();
        getHomeData();
        homeViewPage.startTurning(5000);
    }

    private void getHomeData() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
               // L.i(gson.toJson(o));
                homeData = gson.fromJson(gson.toJson(o), HomeDataEntity.class);
                showUi();
            }
        };
        builder.addParameter("m_id", getUserInfo() == null ? "" : getUserInfo().getM_id());
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(context).index(progressSubscriber, builder.bulider());
    }

    private void showUi() {
        //轮播图片
        setViewPager();
        //本周推荐
        setAdapter1();

        if (homeData.getNot_read().equals("1")) {
            homeMessage.setImageResource(R.mipmap.home_message_);
        } else {
            homeMessage.setImageResource(R.mipmap.home_message);
        }

        if (homeData.getGood_goods_list().size() > 0) {
            List<GoodGoodsListBean> entityList = homeData.getGood_goods_list();
            try {
                Picasso.with(context).load(entityList.get(0).getGoods_logo()).into(homeImg1);
            } catch (Exception e) {
            }
            try {
                Picasso.with(context).load(entityList.get(1).getGoods_logo()).into(homeImg2);
            } catch (Exception e) {
            }
            try {
                Picasso.with(context).load(entityList.get(2).getGoods_logo()).into(homeImg3);
            } catch (Exception e) {
            }
            try {
                Picasso.with(context).load(entityList.get(3).getGoods_logo()).into(homeImg4);
            } catch (Exception e) {
            }

        }

    }

    private void setAdapter1() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        homeRecommendRecycler.removeItemDecoration(itemDecoration);
        homeRecommendRecycler.addItemDecoration(itemDecoration);
        homeRecommendRecycler.setLayoutManager(gridLayoutManager);
        itemAdapter2 = new CommonAdapter<RecommendGoodsListBean>(getContext(), R.layout.item_hot_2, homeData.getRecommend_goods_list()) {

            @Override
            protected void convert(ViewHolder holder, RecommendGoodsListBean goods, int position) {
                Picasso.with(getContext()).load(goods.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_2_image));
                holder.setText(R.id.hot_2_title, goods.getGoods_name());
                holder.setText(R.id.hot_2_price, "￥" + goods.getGoods_price());
                holder.setText(R.id.hot_2_number, goods.getSales() + "人付款");
            }
        };
        itemAdapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, homeData.getRecommend_goods_list().get(position).getGoods_id());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        homeRecommendRecycler.setAdapter(itemAdapter2);
    }

    private void setViewPager() {
        for (int i = 0; i < homeData.getAdvert_list().size(); i++) {
            AdvertListBean adverList = homeData.getAdvert_list().get(i);
            views.add(adverList.getAd_pic());
        }
        homeViewPage.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },views);
        homeViewPage.startTurning(4000);
        homeViewPage.setPageIndicator(new int[]{R.mipmap.ic_page_indicator,R.mipmap.ic_page_indicator_focused}).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
      /*  views.clear();
        for (int i = 0; i < homeData.getAdvert_list().size(); i++) {
            AdvertListBean adverList = homeData.getAdvert_list().get(i);
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getContext()).load(adverList.getAd_pic()).into(imageView);
            views.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views);
        homeViewPage.setAdapter(viewPagerAdapter);
        homeCircle.setViewPager(homeViewPage);*/
    }
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        homeViewPage.stopTurning();
    }

    private void details(int i) {
        if (getUserInfo() == null) {
            intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(K.GOODS_ID, homeData.getGood_goods_list().get(i).getGoods_id());
            startActivity(intent);
        }
    }

    @OnClick({R.id.home_search, R.id.home_hot, R.id.home_recommend, R.id.home_favorable,
            R.id.home_integral, R.id.home_viewPage, R.id.home_message,
            R.id.home_img_1, R.id.home_img_2, R.id.home_img_3, R.id.home_img_4})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.home_img_1:
                try {
                    details(0);
                } catch (Exception e) {
                }

                break;
            case R.id.home_img_2:
                details(1);
                break;
            case R.id.home_img_3:
                try {
                    details(2);
                } catch (Exception e) {
                }

                break;
            case R.id.home_img_4:
                try {
                    details(3);
                } catch (Exception e) {
                }
                break;

            case R.id.home_integral:
                intent = new Intent(getContext(), IntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.home_search:
                intent = new Intent(getContext(), HomeSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.home_hot:
                intent = new Intent(getContext(), HotActivity.class);
                intent.putExtra(K.TITLE, 1);
                startActivity(intent);
                break;
            case R.id.home_recommend:
                intent = new Intent();
                intent.setAction(MainActivity.REFRESH);
                intent.putExtra(K.STATUS, 2);
                context.sendBroadcast(intent);
                break;
            case R.id.home_favorable:
                if (getUserInfo() == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), OrderActivity.class);
                    intent.putExtra(K.STATUS, 0);
                    startActivity(intent);
                }
                break;
            case R.id.home_viewPage:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.home_message:
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

}
