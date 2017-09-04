package com.android.yzd.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/21.
 */

public class WelComeFragment extends BaseFragment {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.go_main)
    Button goMain;

    @Override
    public int getContentViewId() {
        return R.layout.item_image;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int pic = bundle.getInt(K.IMAGE);
        switch (pic) {
            case R.drawable.guide_1:
                image.setBackgroundResource(android.R.color.holo_blue_bright);
                break;
            case R.drawable.guide_2:
                image.setBackgroundResource(R.color.colorPrimary);
                break;
            case R.drawable.guide_3:
                goMain.setVisibility(View.VISIBLE);
                break;
        }
    }
}
