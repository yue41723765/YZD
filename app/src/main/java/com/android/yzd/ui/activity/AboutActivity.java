package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.SetEntity;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.company)
    TextView company;
    @BindView(R.id.copyright)
    TextView copyright;

    @Override
    public int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        SetEntity setEntity = getIntent().getExtras().getParcelable(K.DATA);
        company.setText(setEntity.getCompany_name());
        copyright.setText(setEntity.getCopyright());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
