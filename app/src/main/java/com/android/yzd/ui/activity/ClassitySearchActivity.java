package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.android.yzd.R;
import com.android.yzd.been.BrandList;
import com.android.yzd.been.ClassListEntity;
import com.android.yzd.been.ClassityListEntity;
import com.android.yzd.been.TypeListBean;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.android.yzd.ui.view.recyclerview.EndlessRecyclerOnScrollListener;
import com.android.yzd.ui.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.android.yzd.ui.view.recyclerview.LoadingFooter;
import com.android.yzd.ui.view.recyclerview.RecyclerViewUtils;
import com.android.yzd.ui.view.recyclerview.utils.RecyclerViewStateUtils;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.yzd.tools.F.SIZE;

/**
 * Created by Administrator on 2016/10/5 0005.
 */
public class ClassitySearchActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.search_show)
    ImageView searchShow;
    @BindView(R.id.classify_search)
    RelativeLayout classifySearch;
    @BindView(R.id.synthesize)
    RadioButton synthesize;
    @BindView(R.id.sales_volume)
    RadioButton salesVolume;
    @BindView(R.id.price)
    RadioButton price;
    @BindView(R.id.screen)
    RadioButton screen;
    @BindView(R.id.hot_recycler_1)
    RecyclerView hotRecycler1;
    @BindView(R.id.hot_recycler_2)
    RecyclerView hotRecycler2;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.start_price)
    EditText startPrice;
    @BindView(R.id.end_price)
    EditText endPrice;
    @BindView(R.id.hot_recycler)
    RecyclerView hotRecycler;
    @BindView(R.id.popup_3_clear)
    Button popup3Clear;
    @BindView(R.id.popup_3_sure)
    Button popup3Sure;
    @BindView(R.id.search_drawer)
    DrawerLayout searchDrawer;

    int price_status = 0;
    CommonAdapter adapter_1;
    CommonAdapter adapter_2;
    CommonAdapter popupAdapter;
    CommonAdapter brandAdapter;
    SparseBooleanArray isCheck = new SparseBooleanArray();
    SparseBooleanArray brandCheck = new SparseBooleanArray();
    String sec_type_id;
    String brand_id;
    int sort = 1;//1综合排序，2销量排序;3价格升序；4价格降序；

    float d_price = 0;
    float h_price = 0;

    int p = 1;
    List<ClassListEntity> goodsList = new ArrayList<>();
    List<TypeListBean> typeList = new ArrayList<>();
    List<BrandList> brandList = new ArrayList<>();

    boolean isData = true;
    @BindView(R.id.brand_recycler)
    RecyclerView brandRecycler;

    @Override
    public int getContentViewId() {
        return R.layout.activity_classity_search;
    }


    private void getListData() {

        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                if (p == 1) {
                    goodsList.clear();
                }
                typeList.clear();
                brandList.clear();
                ClassityListEntity list = gson.fromJson(gson.toJson(o), ClassityListEntity.class);
                if (list.getGoods_list().size() == 0) {
                    RecyclerViewStateUtils.setFooterViewState(ClassitySearchActivity.this, hotRecycler1, SIZE, LoadingFooter.State.TheEnd, null);
                    RecyclerViewStateUtils.setFooterViewState(ClassitySearchActivity.this, hotRecycler2, SIZE, LoadingFooter.State.TheEnd, null);
                    isData = false;
                } else {
                    RecyclerViewStateUtils.setFooterViewState(hotRecycler1, LoadingFooter.State.Normal);
                    RecyclerViewStateUtils.setFooterViewState(hotRecycler2, LoadingFooter.State.Normal);
                }
                goodsList.addAll(list.getGoods_list());
                if (goodsList.size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.GONE);
                }


                typeList.addAll(list.getType_list());
                brandList.addAll(list.getBrand_list());

                adapter_1.notifyDataSetChanged();
                popupAdapter.notifyDataSetChanged();
                brandAdapter.notifyDataSetChanged();
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("sec_type_id", sec_type_id);
        httpParamet.addParameter("sort", sort + "");
        httpParamet.addParameter("d_price", d_price == 0 ? "" : "" + d_price);
        httpParamet.addParameter("h_price", h_price == 0 ? "" : "" + h_price);
        httpParamet.addParameter("brand_id", brand_id);
        httpParamet.addParameter("p", p + "");

        Map<String, Object> map = new HashMap<>();
        map.put("sec_type_id", sec_type_id);
        map.put("sort", sort + "");
        map.put("d_price", d_price == 0 ? "" : "" + d_price);
        map.put("h_price", h_price == 0 ? "" : "" + h_price);
        map.put("brand_id", brand_id);
        map.put("p", p + "");

        HttpMethods.getInstance(this).goodsList(progressSubscriber, httpParamet.bulider());
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        sec_type_id = getIntent().getExtras().getString(K.SEC_TYPE_ID);

        //模式一
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        hotRecycler1.setLayoutManager(gridLayoutManager);
        hotRecycler1.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 10, 0, 0));
        //模式二
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        hotRecycler2.setLayoutManager(manager);
        hotRecycler2.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, getResources().getColor(R.color.background), 10, 0, 0));

        //热门
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        hotRecycler.setLayoutManager(layoutManager);
        hotRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));

        //品牌
        final GridLayoutManager layoutManager2 = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        brandRecycler.setLayoutManager(layoutManager2);
        brandRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, Color.WHITE, DensityUtils.dp2px(this, 10), 0, 0));


        hotRecycler1.addOnScrollListener(onScrollListener1);
        hotRecycler2.addOnScrollListener(onScrollListener2);

        setAdapter();
        setHotAdapter();
        setBrandAdapter();
        getListData();

    }

    public void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setFooterView(recyclerView, new LoadingFooter(this));
//        LinearLayout view = new LinearLayout(context);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(0, DensityUtils.dp2px(context, 20), 0, 0);
//        view.setLayoutParams(params);
//        RecyclerViewUtils.setHeaderView(recyclerView, view);
    }

    EndlessRecyclerOnScrollListener onScrollListener1 = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(hotRecycler1);
            if (state == LoadingFooter.State.Loading) {
                return;
            }
            if (isData) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(ClassitySearchActivity.this, hotRecycler1, SIZE, LoadingFooter.State.Loading, null);
                p++;
                getListData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(ClassitySearchActivity.this, hotRecycler1, SIZE, LoadingFooter.State.TheEnd, null);
            }
        }
    };
    EndlessRecyclerOnScrollListener onScrollListener2 = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(hotRecycler2);
            if (state == LoadingFooter.State.Loading) {
                return;
            }
            if (isData) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(ClassitySearchActivity.this, hotRecycler2, SIZE, LoadingFooter.State.Loading, null);
                p++;
                getListData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(ClassitySearchActivity.this, hotRecycler2, SIZE, LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void setBrandAdapter() {

        brandAdapter = new CommonAdapter<BrandList>(this, R.layout.item_hot_popup, brandList) {

            @Override
            protected void convert(ViewHolder holder, BrandList s, int position) {
                CheckBox checkBox = holder.getView(R.id.hot_classify);
                checkBox.setBackgroundResource(R.drawable.check_blue90_blue);
                checkBox.setText(s.getBrand_name());
                checkBox.setChecked(brandCheck.get(position));
            }
        };
        brandAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                brandCheck.clear();
                brandCheck.put(position, true);
                brandAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        brandRecycler.setAdapter(brandAdapter);
    }

    private void setHotAdapter() {

        popupAdapter = new CommonAdapter<TypeListBean>(this, R.layout.item_hot_popup, typeList) {

            @Override
            protected void convert(ViewHolder holder, TypeListBean s, int position) {
                CheckBox checkBox = holder.getView(R.id.hot_classify);
                checkBox.setBackgroundResource(R.drawable.check_blue90_blue);
                checkBox.setText(s.getType_name());
                checkBox.setChecked(isCheck.get(position));
            }
        };
        popupAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                isCheck.clear();
                isCheck.put(position, true);
                popupAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        hotRecycler.setAdapter(popupAdapter);
    }

    private void setAdapter() {
        adapter_1 = new CommonAdapter<ClassListEntity>(this, R.layout.item_hot_2, goodsList) {

            @Override
            protected void convert(ViewHolder holder, ClassListEntity s, int position) {
                try {
                    Glide.with(ClassitySearchActivity.this).load(s.getGoods_logo()).skipMemoryCache(true).into((ImageView) holder.getView(R.id.hot_2_image));
                } catch (Exception e) {
                    holder.setImageResource(R.id.hot_2_image, R.mipmap.logo);
                }
                try {
                    holder.setText(R.id.hot_2_title, s.getGoods_name());
                    holder.setText(R.id.hot_2_price, "￥" + s.getGoods_price());
                    holder.setText(R.id.hot_2_number, s.getSales() + "人付款");
                } catch (Exception e) {

                }
            }
        };
        adapter_1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(ClassitySearchActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(ClassitySearchActivity.this, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, goodsList.get(position).getGoods_id());
                    startActivity(intent);
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        hotRecycler1.setAdapter(adapter_1);
//        setAdapter(hotRecycler1, adapter_1);
        adapter_2 = new CommonAdapter<ClassListEntity>(this, R.layout.item_hot_1, goodsList) {

            @Override
            protected void convert(ViewHolder holder, ClassListEntity goodsInfoEntity, int position) {
                try {
                    Glide.with(ClassitySearchActivity.this).load(goodsInfoEntity.getGoods_logo()).skipMemoryCache(true).into((ImageView) holder.getView(R.id.hot_image));
                } catch (Exception e) {
                    holder.setImageResource(R.id.hot_image, R.mipmap.logo);
                }
                try {
                    holder.setText(R.id.hot_title, goodsInfoEntity.getGoods_name());
                    holder.setText(R.id.hot_price, "￥" + goodsInfoEntity.getGoods_price());
                    holder.setText(R.id.hot_number, goodsInfoEntity.getSales() + "人付款");
                } catch (Exception e) {
                }
            }
        };
        adapter_2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (getUserInfo() == null) {
                    intent = new Intent(ClassitySearchActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(ClassitySearchActivity.this, DetailsActivity.class);
                    intent.putExtra(K.GOODS_ID, goodsList.get(position).getGoods_id());
                    startActivity(intent);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        setAdapter(hotRecycler2, adapter_2);
    }

    int showStatus = 1;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.synthesize:
                p = 1;
                sort = 1;
                getListData();
                break;
            case R.id.sales_volume:
                p = 1;
                sort = 2;
                getListData();
                break;

            case R.id.classify_search:
                intent = new Intent(this, HomeSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.search_show:
                if (showStatus == 1) {
                    showStatus = 2;
                    searchShow.setImageResource(R.mipmap.show_black);
                    hotRecycler1.setVisibility(View.GONE);
                    hotRecycler2.setVisibility(View.VISIBLE);

                } else {
                    showStatus = 1;
                    searchShow.setImageResource(R.mipmap.list_black);
                    hotRecycler1.setVisibility(View.VISIBLE);
                    hotRecycler2.setVisibility(View.GONE);

                }
                break;
            case R.id.screen:
                searchDrawer.openDrawer(Gravity.RIGHT);
                break;
            case R.id.price:
                price_status++;
                Drawable drawable;
                if (price_status % 2 == 0) {
                    drawable = getResources().getDrawable(R.mipmap.price_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    price.setCompoundDrawables(null, null, drawable, null);

                    //升序
                    p = 1;
                    sort = 3;
                    isData = true;
                    getListData();
                } else {
                    drawable = getResources().getDrawable(R.mipmap.price_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    price.setCompoundDrawables(null, null, drawable, null);
                    p = 1;
                    sort = 4;
                    isData = true;
                    getListData();
                }
                break;
            case R.id.popup_3_sure:
                String start = startPrice.getText().toString();
                String end = endPrice.getText().toString();

                for (int i = 0; i < typeList.size(); i++) {
                    if (isCheck.get(i)) {
                        sec_type_id = typeList.get(i).getSec_type_id();
                    }
                }

                for (int i = 0; i < brandList.size(); i++) {
                    if (brandCheck.get(i)) {
                        brand_id = brandList.get(i).getBrand_id();
                    }
                }

                d_price = Float.valueOf(start.equals("") ? "0" : start);
                h_price = Float.valueOf(end.equals("") ? "0" : end);

                isData = true;
                p = 1;
                empty.setVisibility(View.GONE);

                getListData();
                goodsList.clear();
                adapter_1.notifyDataSetChanged();
                adapter_2.notifyDataSetChanged();

                searchDrawer.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.popup_3_clear:
                startPrice.setText("");
                endPrice.setText("");
                isCheck.clear();
                popupAdapter.notifyDataSetChanged();

                brandCheck.clear();
                brandAdapter.notifyDataSetChanged();

                brand_id = "";
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
