package com.android.yzd.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.yzd.R;
import com.android.yzd.been.HomeDataEntity;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 33181 on 2017/7/19.
 */

public class NetworkImageHolderView  implements Holder<String>{
    ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Picasso.with(context).load(data).placeholder(R.mipmap.guide_1).into(imageView);
    }

}
