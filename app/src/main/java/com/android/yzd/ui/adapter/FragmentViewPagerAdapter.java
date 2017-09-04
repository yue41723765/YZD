package com.android.yzd.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.yzd.ui.fragment.OrderFragment;

import java.util.List;


/**
 * Created by Administrator on 2016/9/21.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    String[] mTitle;
    List<OrderFragment> orderFragments;

    public FragmentViewPagerAdapter(FragmentManager fm, String[] mTitle, List<OrderFragment> orderFragments) {
        super(fm);
        this.mTitle = mTitle;
        this.orderFragments = orderFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return orderFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

}