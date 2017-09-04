package com.android.yzd.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.QuestionEntity;
import com.android.yzd.been.SystemMessEntity;
import com.android.yzd.been.UserRegAgr;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.ui.custom.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/23.
 */

public class WebView extends BaseActivity {
    @BindView(R.id.titleBar_title)
    TextView titleBarTitle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.contents)
    android.webkit.WebView contents;

    @Override
    public int getContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        Parcelable parcelable = getIntent().getParcelableExtra(K.DATA);

//        //支持JS
//        WebSettings settings = contents.getSettings();
//        settings.setJavaScriptEnabled(true);
//        //支持屏幕缩放
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
        if (parcelable instanceof UserRegAgr) {
            UserRegAgr info = (UserRegAgr) parcelable;
            title.setText(info.getTitle());
            titleBarTitle.setText(info.getTitle());
            contents.loadDataWithBaseURL("about:blank", info.getContent(), "text/html", "utf-8", null);
        } else if (parcelable instanceof SystemMessEntity) {
            SystemMessEntity entity = (SystemMessEntity) parcelable;
            title.setText(entity.getTitle());
            titleBarTitle.setText(entity.getTitle());
            contents.loadDataWithBaseURL("about:blank", entity.getContent(), "text/html", "utf-8", null);
        } else if (parcelable instanceof QuestionEntity) {
            QuestionEntity entity = (QuestionEntity) parcelable;
            titleBarTitle.setText(entity.getTitle());
            contents.loadDataWithBaseURL("about:blank", entity.getContent(), "text/html", "utf-8", null);
        }
    }

    private String fmtString(String str) {
        String notice = "";
        try {
            notice = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return notice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            StatusBarUtil.StatusBarLightMode(this);
        }
    }
}
