package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.DetailsEntity;
import com.android.yzd.been.GoodsPictureBean;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.tools.U;
import com.android.yzd.ui.adapter.ViewPagerAdapter;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.AutoScrollViewPager;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/10/5 0005.
 */
public class DetailsActivity extends BaseActivity {
    PopupWindow popupWindow;

    View addressView;
    RecyclerView address_recycler;
    CommonAdapter recyclerAdapter;
    List<View> views = new ArrayList<>();

    View view;
    RecyclerView recycler;
    CommonAdapter Adapter;

    CommonAdapter itemAdapter;
    UserInfoEntity userInfo;

    String goods_id;
    @BindView(R.id.service_head)
    ImageView serviceHead;
    @BindView(R.id.details_service)
    TextView detailsService;
    @BindView(R.id.details_collect)
    TextView detailsCollect;
    @BindView(R.id.details_shopping_cart_number)
    TextView detailsShoppingCartNumber;
    @BindView(R.id.details_shopping_cart)
    FrameLayout detailsShoppingCart;
    @BindView(R.id.add_shoppingCart)
    TextView addShoppingCart;
    @BindView(R.id.details_buy)
    TextView detailsBuy;
    @BindView(R.id.tools)
    LinearLayout tools;
    @BindView(R.id.details_viewpager)
    AutoScrollViewPager detailsViewpager;
    @BindView(R.id.viewpage_circle)
    CircleIndicator viewpageCircle;
    @BindView(R.id.isCollect)
    TextView isCollect;
    @BindView(R.id.details_title)
    TextView detailsTitle;
    @BindView(R.id.details_price)
    TextView detailsPrice;
    @BindView(R.id.details_number)
    TextView detailsNumber;
    @BindView(R.id.details_integral)
    TextView detailsIntegral;
    @BindView(R.id.details_more)
    ImageView detailsMore;
    @BindView(R.id.details_recycler)
    RecyclerView detailsRecycler;
    @BindView(R.id.details_song)
    TextView detailsSong;
    @BindView(R.id.details_more_2)
    ImageView detailsMore2;
    @BindView(R.id.details_position)
    TextView detailsPosition;
    @BindView(R.id.details_status)
    TextView detailsStatus;


    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        init();

        getDetailsData();


//        Adapter = new CommonAdapter<String>(this, R.layout.item_details_more_1, list) {
//            @Override
//            protected void convert(ViewHolder holder, String s, int position) {
//                switch (position) {
//                    case 0:
//                        holder.setText(R.id.item_title, "无忧安装");
//                        holder.setText(R.id.item_content, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                        Picasso.with(DetailsActivity.this).load(R.mipmap.null_).into((ImageView) holder.getView(R.id.item_image));
//                        break;
//                    case 1:
//                        holder.setText(R.id.item_title, "三年保质");
//                        Picasso.with(DetailsActivity.this).load(R.mipmap.real).into((ImageView) holder.getView(R.id.item_image));
//                        holder.setText(R.id.item_content, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                        break;
//                    case 2:
//                        holder.setText(R.id.item_title, "假一赔十");
//                        Picasso.with(DetailsActivity.this).load(R.mipmap.keep).into((ImageView) holder.getView(R.id.item_image));
//                        holder.setText(R.id.item_content, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                        break;
//                }
//            }
//        };
//        recycler.setAdapter(Adapter);
//        recyclerAdapter = new CommonAdapter<String>(this, R.layout.popup_item_address, list) {
//
//            @Override
//            protected void convert(ViewHolder holder, String s, int position) {
//                TextView textview = holder.getView(R.id.address);
//                if (position == 0) {
//                    textview.setTextColor(getResources().getColor(R.color.red_30));
//                    Drawable left = getResources().getDrawable(R.mipmap.details_position);
//                    Drawable right = getResources().getDrawable(R.mipmap.red_check);
//                    left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
//                    right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
//                    textview.setCompoundDrawables(left, null, right, null);
//                } else {
//                    textview.setText(s);
//                }
//
//            }
//        };
//        address_recycler.setAdapter(recyclerAdapter);

        List<String> list = new ArrayList<>();
        list.add("上门安装");
        list.add("三年质保");
        list.add("假一赔五");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        detailsRecycler.setLayoutManager(linearLayoutManager);
        detailsRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_VERTICAL, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));
        itemAdapter = new CommonAdapter<String>(this, R.layout.item_details, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.details_text, s);
            }
        };
        detailsRecycler.setAdapter(itemAdapter);
    }

    DetailsEntity detailsEntity;

    private void getDetailsData() {

        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                detailsEntity = gson.fromJson(gson.toJson(o), DetailsEntity.class);
                try {
                    showUi(detailsEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("m_id", userInfo.getM_id());
        httpParamet.addParameter("goods_id", goods_id);
        HttpMethods.getInstance(this).goodsInfo(progressSubscriber, httpParamet.bulider());
    }

    private void showUi(DetailsEntity detailsEntity) throws Exception {
        //广告界面
        views.clear();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < detailsEntity.getGoods_picture().size(); i++) {
            GoodsPictureBean picture = detailsEntity.getGoods_picture().get(i);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (picture.getPic() != null)
                Picasso.with(this).load(picture.getPic()).into(imageView);
            views.add(imageView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        detailsViewpager.setAdapter(adapter);
        viewpageCircle.setViewPager(detailsViewpager);

        detailsTitle.setText(detailsEntity.getGoods_name());
        detailsPrice.setText("￥" + detailsEntity.getGoods_price());
        if (detailsEntity.getIs_collect().equals("1")) {
            Drawable drawable = getResources().getDrawable(R.mipmap.collect_true);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            detailsCollect.setCompoundDrawables(null, drawable, null, null);
            isCollect.setCompoundDrawables(null, drawable, null, null);
        } else {
            Drawable drawable = getResources().getDrawable(R.mipmap.collect_false);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            detailsCollect.setCompoundDrawables(null, drawable, null, null);
            isCollect.setCompoundDrawables(null, drawable, null, null);
        }
        detailsTitle.setText(detailsEntity.getGoods_name());
        //购买人数
        detailsNumber.setText(detailsEntity.getSales() + "人购买");
        //积分
        detailsIntegral.setText("买可送可获得积分");
        //购物车数量
        if (detailsEntity.getCart_number().equals("0") | detailsEntity.equals("")) {
            detailsShoppingCartNumber.setVisibility(View.GONE);
        } else {
            int nuumber = Integer.valueOf(detailsEntity.getCart_number());
            if (nuumber > 0 && nuumber < 100) {
                detailsShoppingCartNumber.setText(nuumber + "");
            } else {
                detailsShoppingCartNumber.setText(99 + "+");
            }
        }
        if (detailsEntity.getService_logo() != null)
            Picasso.with(this).load(detailsEntity.getService_logo()).into(serviceHead);
    }


    private void init() {
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        goods_id = getIntent().getExtras().getString(K.GOODS_ID);

        addressView = getLayoutInflater().inflate(R.layout.popup_details_address, null);
        address_recycler = (RecyclerView) addressView.findViewById(R.id.popup_recycler);

        view = getLayoutInflater().inflate(R.layout.popup_details_1, null);
        recycler = (RecyclerView) view.findViewById(R.id.popup_recycler_1);

        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(this, 340));
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.PopupAnimationStyle);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                U.setWindows(DetailsActivity.this, 1f);
            }
        });

        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(OrientationHelper.VERTICAL);
        address_recycler.setLayoutManager(layoutmanager);
        LinearLayoutManager layoutmanager1 = new LinearLayoutManager(this);
        layoutmanager1.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(layoutmanager1);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.details_more_2:
                U.setWindows(DetailsActivity.this, 0.5f);
                popupWindow.setContentView(addressView);
                popupWindow.showAtLocation(addressView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.details_buy:
                intent = new Intent(this, AddOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.details_more:
                U.setWindows(DetailsActivity.this, 0.5f);
                popupWindow.setContentView(view);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.isCollect:
            case R.id.details_collect:
                if (detailsEntity != null) {
                    if (detailsEntity.getIs_collect().equals("0")) {
                        addCollect(goods_id);
                    } else {
                        delCollect(goods_id);
                    }
                }
                break;
            case R.id.add_shoppingCart:
                addCart(goods_id);
                break;
            case R.id.details_shopping_cart:
                intent = new Intent();
                intent.setAction(MainActivity.REFRESH);
                intent.putExtra(K.STATUS, 3);
                sendBroadcast(intent);
                AppManager.getAppManager().finishActivity(ClassitySearchActivity.class);
                AppManager.getAppManager().finishActivity(HotActivity.class);
                finish();
                break;
            case R.id.details_service:
                if (detailsEntity == null)
                    return;
                intent = new Intent(this, MessageActivity.class);
                intent.putExtra("nameNick", "一站达客服");
                intent.putExtra("ecId", detailsEntity.getService_account());
                intent.putExtra("yourHead", detailsEntity.getService_logo());
                intent.putExtra("myHead", userInfo.getHead_pic());
                startActivity(intent);
                break;
        }
    }

    private void addCart(String goods_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(DetailsActivity.this, "添加购物车成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("m_id", userInfo.getM_id());
        httpParamet.addParameter("goods_id", goods_id);
        HttpMethods.getInstance(this).addCart(progressSubscriber, httpParamet.bulider());
    }

    private void addCollect(String goods_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                detailsEntity.setIs_collect("1");
                try {
                    showUi(detailsEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                T.show(DetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("goods_id", goods_id);
        httpParamet.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).addCollect(progressSubscriber, httpParamet.bulider());

    }

    private void delCollect(String goods_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                detailsEntity.setIs_collect("0");
                try {
                    showUi(detailsEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                T.show(DetailsActivity.this, "取消收藏成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("goods_id", goods_id);
        httpParamet.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).deleteCollect(progressSubscriber, httpParamet.bulider());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
