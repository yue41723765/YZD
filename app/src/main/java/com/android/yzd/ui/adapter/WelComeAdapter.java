package com.android.yzd.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.yzd.tools.K;
import com.android.yzd.ui.fragment.WelComeFragment;


/**
 * Created by Administrator on 2016/9/21.
 */

public class WelComeAdapter extends FragmentPagerAdapter {

    int[] mPics;

    public WelComeAdapter(FragmentManager fm, int[] mPics) {
        super(fm);
        this.mPics = mPics;
    }

    @Override
    public Fragment getItem(int position) {
        return newInstance(position);
    }


    @Override
    public int getCount() {
        return mPics.length;
    }

    public Fragment newInstance(int position) {
        WelComeFragment fragment = new WelComeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(K.IMAGE, mPics[position]);
        fragment.setArguments(bundle);
        return fragment;
    }
}
