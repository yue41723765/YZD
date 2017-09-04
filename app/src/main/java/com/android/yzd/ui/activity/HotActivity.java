package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.GoodsInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class HotActivity extends BaseActivity {

    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.titleBar_right_image)
    ImageView right_img;
    @BindView(R.id.hot_show_type)
    ImageView hotShowType;
    @BindView(R.id.label)
    TextView label;
    @BindView(R.id.translucence)
    TextView translucence;
    @BindView(R.id.hot_type)
    RadioButton hotType;
    @BindView(R.id.hot_sort_rank)
    RadioButton hotSortRank;
    @BindView(R.id.hot_screen_filtrate)
    RadioButton hotScreenFiltrate;
    RecyclerView classityRecycler;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.hot_recycler_1)
    RecyclerView hotRecycler1;
    @BindView(R.id.hot_recycler_2)
    RecyclerView hotRecycler2;

    CommonAdapter adapter1;
    CommonAdapter adapter2;


    PopupWindow popupWindow;

    View view1;
    RadioButton popup_hot;
    RadioButton popup_new;
    RadioButton popup_sale;
    View view2;
    View view3;

    CommonAdapter popupAdapter;
    int status = 1;//显示风格1  显示风格2两种
    int showStatus = 1;
    int classityStatus = 1;//1---热销(默认) 2----最新 3----优惠
    int sortRankStatus = 1;//1---智能(默认) 2----人气 3----价格

    int lastVisibleItem = -1;


    Map<Integer, Boolean> isCheck = new HashMap<>();
    int p = 1;
    List<GoodsInfoEntity> hotEntity = new ArrayList<>();
    @BindView(R.id.empty)
    ImageView empty;

    @Override
    public int getContentViewId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setAdapter(hotEntity);
        init();
        getHotData();
        status = getIntent().getExtras().getInt(K.TITLE);
        switch (status) {
            case 1:
                popup_hot.setChecked(true);
                title.setText("热销产品");
                break;
            case 2:
                popup_new.setChecked(true);
                title.setText("最新推荐");
                break;
            case 3:
                popup_sale.setChecked(true);
                title.setText("特价优惠");
                break;
        }


//        popupAdapter = new CommonAdapter<String>(this, R.layout.item_hot_popup, list) {
//
//            @Override
//            protected void convert(ViewHolder holder, String s, int position) {
//                CheckBox checkBox = holder.getView(R.id.hot_classify);
//                checkBox.setText("分类" + position);
//                checkBox.setChecked(isCheck.get(position));
//            }
//        };
//
//        popupAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                for (int i = 0; i < isCheck.size(); i++) {
//                    isCheck.put(i, false);
//                }
//                isCheck.put(position, true);
//                popupAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
//        classityRecycler.setAdapter(popupAdapter);


    }


    private void getHotData() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                if (p == 1)
                    hotEntity.clear();
                List<GoodsInfoEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<GoodsInfoEntity>>() {
                }.getType());
                hotEntity.addAll(list);
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();

                if (hotEntity.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                    hotRecycler1.setVisibility(View.GONE);
                    hotRecycler2.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    if (showStatus == 1) {
                        hotRecycler1.setVisibility(View.VISIBLE);
                    } else {
                        hotRecycler2.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("p", p + "");
        HttpMethods.getInstance(this).hotSalesList(progressSubscriber, httpParamet.bulider());
    }

    private void setAdapter(final List<GoodsInfoEntity> hotEntity) {

        adapter1 = new CommonAdapter<GoodsInfoEntity>(this, R.layout.item_hot_1, hotEntity) {

            @Override
            protected void convert(ViewHolder holder, GoodsInfoEntity s, int position) {
                Picasso.with(HotActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_image));
                holder.setText(R.id.hot_title, s.getGoods_name());
                holder.setText(R.id.hot_price, "￥" + s.getGoods_price());
                holder.setText(R.id.hot_number, s.getSales() + "人付款");
            }
        };
        hotRecycler1.setAdapter(adapter1);
        adapter2 = new CommonAdapter<GoodsInfoEntity>(this, R.layout.item_hot_2, hotEntity) {

            @Override
            protected void convert(ViewHolder holder, GoodsInfoEntity s, int position) {
                Picasso.with(HotActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.hot_2_image));
                holder.setText(R.id.hot_2_title, s.getGoods_name());
                holder.setText(R.id.hot_2_price, "￥" + s.getGoods_price());
                holder.setText(R.id.hot_2_number, s.getSales() + "人付款");
            }
        };
        hotRecycler2.setAdapter(adapter2);

        adapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(HotActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(HotActivity.this, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, hotEntity.get(position).getGoods_id());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        adapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(HotActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(HotActivity.this, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, hotEntity.get(position).getGoods_id());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void init() {

        view1 = getLayoutInflater().inflate(R.layout.popup_hot_1, null);
        view2 = getLayoutInflater().inflate(R.layout.popup_hot_2, null);
        view3 = getLayoutInflater().inflate(R.layout.popup_hot_3, null);
        popup_hot = (RadioButton) view1.findViewById(R.id.popup_hot);
        popup_new = (RadioButton) view1.findViewById(R.id.popup_new);
        popup_sale = (RadioButton) view1.findViewById(R.id.popup_sale);

        classityRecycler = (RecyclerView) view3.findViewById(R.id.hot_recycler);

        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.TopStyle);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                translucence.setVisibility(View.GONE);
                hotType.setChecked(false);
                hotSortRank.setChecked(false);
                hotScreenFiltrate.setChecked(false);
            }
        });

        //风格一
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        hotRecycler1.setLayoutManager(layoutManager);

        hotRecycler1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter1.getItemCount()) {
                    p++;
                    getHotData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        //风格二
        final GridLayoutManager gridManager = new GridLayoutManager(this, 2);
        hotRecycler2.setLayoutManager(gridManager);
        hotRecycler2.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));

        hotRecycler2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == adapter2.getItemCount()) {
                    p++;
                    getHotData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridManager.findLastVisibleItemPosition();
            }
        });


        GridLayoutManager pupupGridManager = new GridLayoutManager(this, 4);
        classityRecycler.setLayoutManager(pupupGridManager);
        classityRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));
    }


    private void showPopup(View view) {
        translucence.setVisibility(View.VISIBLE);
        popupWindow.dismiss();
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(label, 0, 0);
    }

    @OnClick({R.id.titleBar_right_image})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleBar_right_image:

                if (showStatus == 1) {
                    showStatus = 2;
                    right_img.setImageResource(R.mipmap.list_black);
                    hotRecycler1.setVisibility(View.GONE);
                    hotRecycler2.setVisibility(View.VISIBLE);
                } else {
                    showStatus = 1;
                    right_img.setImageResource(R.mipmap.show_black);
                    hotRecycler1.setVisibility(View.VISIBLE);
                    hotRecycler2.setVisibility(View.GONE);
                }

                break;
            case R.id.hot_show_type:

                break;
            case R.id.hot_type:
                showPopup(view1);
                break;
            case R.id.hot_sort_rank:
                showPopup(view2);
                break;
            case R.id.hot_screen_filtrate:
                showPopup(view3);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
