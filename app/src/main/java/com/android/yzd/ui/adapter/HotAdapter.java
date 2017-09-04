package com.android.yzd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.L;
import com.android.yzd.tools.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class HotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;
    List<String> str;

    int ItemViewType = 1;
    Context context;

    public HotAdapter(Context context, List<String> str) {
        inflater = LayoutInflater.from(context);
        this.str = str;
        this.context = context;
    }

    public void setItemViewType(int ItemViewType) {
        this.ItemViewType = ItemViewType;
    }

    @Override
    public int getItemViewType(int position) {
        return ItemViewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.item_hot_1, null);
                return new Item1ViewHolder(view);
            case 2:
                view = inflater.inflate(R.layout.item_hot_2, null);
                return new Item2ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Item1ViewHolder) {
            Item1ViewHolder viewHolder = (Item1ViewHolder) holder;
        } else if (holder instanceof Item2ViewHolder) {
            Item2ViewHolder viewHolder = (Item2ViewHolder) holder;
//            if (position % 2 == 2) {
//                int width = ScreenUtils.getScreenWidth(context);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / 2 - DensityUtils.dp2px(context, 10), ViewGroup.LayoutParams.WRAP_CONTENT);
//                viewHolder.itemView.setLayoutParams(params);
//            }

        }
    }

    @Override
    public int getItemCount() {
        return str.size();
    }

    static class Item1ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_image)
        ImageView hotImage;
        @BindView(R.id.hot_title)
        TextView hotTitle;
        @BindView(R.id.hot_price)
        TextView hotPrice;
        @BindView(R.id.hot_number)
        TextView hotNumber;

        Item1ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class Item2ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hot_2_image)
        ImageView hot2Image;
        @BindView(R.id.hot_2_title)
        TextView hot2Title;
        @BindView(R.id.hot_2_price)
        TextView hot2Price;
        @BindView(R.id.hot_2_number)
        TextView hot2Number;

        Item2ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
