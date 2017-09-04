package com.android.yzd.ui;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.yzd.tools.CrashHandler;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2016/10/23 0023.
 */

public class ECApplication extends Application {

    public static ECApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        CrashHandler.getInstance().init(this);
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
        EaseUI.getInstance().init(this, null);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        MultiDex.install(newBase);
        super.attachBaseContext(newBase);
    }
}
