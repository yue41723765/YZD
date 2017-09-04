package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.adapter.FragmentViewPagerAdapter;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class OrderActivity extends BaseActivity {


    @BindView(R.id.order_tablayout)
    TabLayout orderTablayout;
    @BindView(R.id.order_viewpager)
    ViewPager orderViewpager;

    String[] titles = new String[]{"全部", "待支付", "待发货", "待收货", "已完成"};

    List<OrderFragment> orderFragments = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        int status = getIntent().getExtras().getInt(K.STATUS);

        orderTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        for (int i = 0; i < titles.length; i++) {
            orderTablayout.addTab(orderTablayout.newTab().setText(titles[i]));
            OrderFragment orderFragment = new OrderFragment();
            Bundle bundle = new Bundle();
            bundle.putString(K.STATUS, titles[i]);
            bundle.putInt(K.POSITION, i);
            orderFragment.setArguments(bundle);

            orderFragments.add(orderFragment);
        }


        FragmentViewPagerAdapter viewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), titles, orderFragments);
        orderViewpager.setAdapter(viewPagerAdapter);
        orderTablayout.setupWithViewPager(orderViewpager);
        orderViewpager.setCurrentItem(status);

        orderViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                orderFragments.get(position).onRefresh();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
