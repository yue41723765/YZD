package com.android.yzd.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.ClassifyInfo;
import com.android.yzd.tools.F;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class ClassifyContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ClassifyInfo> classifyInfos;
    LayoutInflater inflater;


    public ClassifyContentAdapter(Context context, List<ClassifyInfo> classifyInfos) {
        this.classifyInfos = classifyInfos;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return F.HEAD;
        }
        return F.CONTENT;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == F.HEAD
                            ? gridManager.getSpanCount() : F.CONTENT;
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case F.HEAD:
                view = inflater.inflate(R.layout.item_classity_head, parent, false);
                return new HeadViewHolder(view);
            case F.TEXT:
                view = inflater.inflate(R.layout.item_classity_textview, parent, false);
                return new ContentViewHolder(view);
            case F.CONTENT:
                view = inflater.inflate(R.layout.item_classity_content, parent, false);
                return new TitleViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {

        } else if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            ClassifyInfo info = classifyInfos.get(position);
            titleViewHolder.title.setText(info.getStr());
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.itemTitle.setText("text" + position);
        }
    }


    @Override
    public int getItemCount() {
        return classifyInfos == null ? 0 : classifyInfos.size();
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.optimization_goods)
        ImageView optimizationGoods;
        @BindView(R.id.optimization_prompt_1)
        TextView optimizationPrompt1;
        @BindView(R.id.optimization_prompt_2)
        TextView optimizationPrompt2;
        @BindView(R.id.optimization_recycler)
        RecyclerView optimizationRecycler;
        @BindView(R.id.home_select_details)
        TextView homeSelectDetails;

        HeadViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_image)
        ImageView itemImage;

        ContentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
