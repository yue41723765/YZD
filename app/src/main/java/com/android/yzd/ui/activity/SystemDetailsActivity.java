package com.android.yzd.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.SystemMessEntity;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class SystemDetailsActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.contents)
    TextView contents;

    @Override
    public int getContentViewId() {
        return R.layout.activity_system_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        SystemMessEntity parcelable = getIntent().getParcelableExtra(K.DATA);
//        if (parcelable instanceof SystemMessEntity) {
//            SystemMessEntity entity = (SystemMessEntity) parcelable;
//            title.setText(entity.getTitle());
//            contents.setText(entity.getContent());
//            date.setText(U.timeStampToStr(entity.getCreate_time()));
//        } else if (parcelable instanceof MessageEntity) {
//            MessageEntity entity = (MessageEntity) parcelable;
//            title.setText(entity.getTitle());
//            contents.setText(entity.getContent());
//            date.setText(U.timeStampToStr(entity.getCreate_time()));
//        } else if (parcelable instanceof UserRegAgr) {
//            UserRegAgr info = (UserRegAgr) parcelable;
//            title.setText(info.getTitle());
//            contents.setText(info.getContent());
//            date.setText(U.timeStampToStr(info.getCreate_time()));
//        }
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
