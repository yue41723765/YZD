package com.android.yzd.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/21.
 */

public class StartActivity extends Activity {

    int time = 3000;//间隔几秒跳转


    Intent intent;
    @BindView(R.id.start_image)
    ImageView startImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(time);
                    Message message = new Message();
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Runtime.getRuntime().gc();
            boolean isRun = (boolean) SPUtils.get(StartActivity.this, K.ISRUN, true);//是否进入导航页
            intent = new Intent(StartActivity.this, WelComeActivity.class);
            if (isRun) {
                intent = new Intent(StartActivity.this, WelComeActivity.class);
            } else {
//                boolean isLog = (boolean) SPUtils.get(StartActivity.this, K.ISLOG, true);//是否登陆
                intent = new Intent(StartActivity.this, MainActivity.class);
//                if (isLog) {
//                    intent = new Intent(StartActivity.this, LoginActivity.class);
//                } else {
//                }
            }
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//放到线程会无效
            finish();
        }
    };

}
