package com.android.yzd.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.yzd.R;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.ui.custom.BaseActivity;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/23 0023.
 */

public class MessageActivity extends BaseActivity {

    FragmentManager manager;
    FragmentTransaction transaction;
    @BindView(R.id.message_content)
    FrameLayout messageContent;

    @Override
    public int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        String nick = getIntent().getExtras().getString("nameNick");
        String ecId = getIntent().getExtras().getString("ecId");
        String yourHead = getIntent().getExtras().getString("yourHead");
        String myHead = getIntent().getExtras().getString("myHead");

        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, ecId);
        args.putString("nickName", nick);
        args.putString("yourHead", yourHead);
        args.putString("myHead", myHead);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.message_content, chatFragment).commit();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            StatusBarUtil.StatusBarLightMode(this);
        }
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
