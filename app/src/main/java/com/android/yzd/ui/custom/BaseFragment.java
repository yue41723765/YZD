package com.android.yzd.ui.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.google.gson.Gson;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/16.
 * Fragment封装
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context context;
    protected View mRootView;

    public Intent intent;

    public Gson gson = new Gson();
    public ProgressSubscriber progressSubscriber;
    public HttpParameterBuilder builder = new HttpParameterBuilder();

    public void setProgressSubscriber(SubscriberOnNextListener onNextListener) {
        progressSubscriber = new ProgressSubscriber(onNextListener, context, false);
    }

    public void setProgressSubscriber(SubscriberOnNextListener onNextListener, boolean isShow) {
        progressSubscriber = new ProgressSubscriber(onNextListener, context, false, isShow);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, mRootView);
        this.context = getActivity();
        onCreateView(savedInstanceState);
        return mRootView;
    }

    public abstract int getContentViewId();

    protected abstract void onCreateView(Bundle savedInstanceState);


    public void onClick(View v) {
    }

    public UserInfoEntity getUserInfo() {
        return (UserInfoEntity) SPUtils.get(context, K.USERINFO, UserInfoEntity.class);
    }
}
