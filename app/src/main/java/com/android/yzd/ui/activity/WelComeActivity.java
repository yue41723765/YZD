package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.yzd.R;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.ui.adapter.ViewPagerAdapter;
import com.android.yzd.ui.custom.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/8/17.
 */

public class WelComeActivity extends BaseActivity {

    @BindView(R.id.welcome_viewPager)
    ViewPager viewpager;
    @BindView(R.id.welcome_circleIndicator)
    CircleIndicator CircleIndicator;

    List<View> views = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        LayoutInflater inflater = getLayoutInflater();
        View guide_1 = inflater.inflate(R.layout.guide_1, null);
        View guide_2 = inflater.inflate(R.layout.guide_2, null);
        View guide_3 = inflater.inflate(R.layout.guide_3, null);
        views.add(guide_1);
        views.add(guide_2);
        views.add(guide_3);
//        int[] pics = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
//        WelComeAdapter adapter = new WelComeAdapter(getSupportFragmentManager(), pics);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(views);
        viewpager.setAdapter(viewPagerAdapter);
        CircleIndicator.setViewPager(viewpager);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_immediately:
//                SPUtils.put(this, K.ISRUN, false);
//                intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                finish();
                SPUtils.put(this, K.ISRUN, false);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.go_main:
                SPUtils.put(this, K.ISRUN, false);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
