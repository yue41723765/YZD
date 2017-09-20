package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.ClassifyToolsEntity;
import com.android.yzd.been.SecListBean;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.U;
import com.android.yzd.ui.activity.ClassitySearchActivity;
import com.android.yzd.ui.activity.HomeSearchActivity;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.MainActivity;
import com.android.yzd.ui.activity.MessageManagerActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class ClassifyFragment extends BaseFragment {
    @BindView(R.id.classify_more)
    ImageView classifyMore;
    @BindView(R.id.classify_search)
    RelativeLayout classifySearch;
    @BindView(R.id.classify_recycler)
    RecyclerView classifyRecycler;
    @BindView(R.id.classify_contents)
    RecyclerView classifyContents;
    @BindView(R.id.optimization_goods)
    ImageView optimizationGoods;
    @BindView(R.id.head_content)
    FrameLayout headContent;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_head)
    RecyclerViewHeader recyclerHead;

    CommonAdapter toolsAdapter;
//    ClassifyContentAdapter contentAdapter;


    Map<Integer, Boolean> isCheck = new HashMap<>();

    PopupWindow popupWindow;
    View view;

    List<ClassifyToolsEntity> list = new ArrayList<>();


    @Override
    public int getContentViewId() {
        return R.layout.fragment_classify;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        LinearLayoutManager toolsManager = new LinearLayoutManager(getContext());
        toolsManager.setOrientation(OrientationHelper.VERTICAL);
        classifyRecycler.setLayoutManager(toolsManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        classifyContents.setLayoutManager(gridLayoutManager);
//        classifyContents.setItemAnimator(new DefaultItemAnimator());
        recyclerHead.attachTo(classifyContents);


        classifyRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, getResources().getColor(R.color.background), 2, 10, 10));
        classifyContents.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_HORIZONTAL, getResources().getColor(R.color.background), DensityUtils.dp2px(context, 3), 0, 0));
        classifyContents.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_VERTICAL, getResources().getColor(R.color.background), DensityUtils.dp2px(context, 3), 0, 0));

        getTools();
        setPopup();
        setToolsAdapter();

    }


    private void getTools() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                ClassifyFragment.this.list.clear();
                List<ClassifyToolsEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<ClassifyToolsEntity>>() {
                }.getType());
                ClassifyFragment.this.list.addAll(list);
                toolsAdapter.notifyDataSetChanged();

                if (list.size() > 0) {
                    title.setText(list.get(0).getType_name());
                    setContentAdapter(list.get(0).getSec_list());
                }


                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        isCheck.put(i, true);
                    } else {
                        isCheck.put(i, false);
                    }
                }
            }
        };
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(context).goodsTypeList(progressSubscriber);
    }

    private void setToolsAdapter() {
        toolsAdapter = new CommonAdapter<ClassifyToolsEntity>(getContext(), R.layout.item_classify_tools, list) {
            @Override
            protected void convert(ViewHolder holder, ClassifyToolsEntity classifyInfo, int position) {
                CheckBox checkBox = holder.getView(R.id.checkbox);
                checkBox.setText(classifyInfo.getType_name());
                if (!isCheck.containsKey(position)) {
                    isCheck.put(position, false);
                }
                checkBox.setTag(position);
                checkBox.setChecked(isCheck.get(position));
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int position = (int) buttonView.getTag();
                        isCheck.put(position,isChecked);
                    }
                });
            }
        };

        toolsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int temp = -1;
                for (int i = 0; i < isCheck.size(); i++) {
                    if (isCheck.get(i)) {
                        temp = i;
                    }
                    isCheck.put(i, false);
                }
                if (temp != -1) {
                    toolsAdapter.notifyItemChanged(temp);
                }
                isCheck.put(position, true);
                toolsAdapter.notifyItemChanged(position);
                title.setText(list.get(position).getType_name());
                if (position != 0) {
                    headContent.setVisibility(View.GONE);
                } else {
                    headContent.setVisibility(View.VISIBLE);
                }

                setContentAdapter(list.get(position).getSec_list());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        classifyRecycler.setAdapter(toolsAdapter);
    }

    private void setContentAdapter(final List<SecListBean> sec_list) {
        CommonAdapter adapter = new CommonAdapter<SecListBean>(getContext(), R.layout.item_classity_content, sec_list) {
            @Override
            protected void convert(ViewHolder holder, SecListBean classifyInfo, int position) {
                holder.setText(R.id.item_title, classifyInfo.getType_name());
                if (!classifyInfo.getType_pic().isEmpty())
                    Glide.with(context).load(classifyInfo.getType_pic()).into((ImageView) holder.getView(R.id.item_image));
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(getContext(), ClassitySearchActivity.class);
                intent.putExtra(K.SEC_TYPE_ID, sec_list.get(position).getSec_type_id());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
//        contentAdapter = new ClassifyContentAdapter(context, classifyInfos);
        classifyContents.setAdapter(adapter);

    }

    @OnClick({R.id.classify_more, R.id.classify_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.classify_more:
                popupWindow = U.showPopup(context, view, classifyMore, DensityUtils.dp2px(context, 15), DensityUtils.dp2px(context, 10));
                break;
            case R.id.popup_refresh:
                getTools();
                popupWindow.dismiss();
                break;
            case R.id.popup_message:
                if (getUserInfo() == null) {
                    intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getContext(), MessageManagerActivity.class);
                    startActivity(intent);
                }
                popupWindow.dismiss();
                break;
            case R.id.popup_home:
                intent = new Intent(MainActivity.REFRESH);
                intent.putExtra(K.STATUS, 1);
                context.sendBroadcast(intent);
                popupWindow.dismiss();
                break;
            case R.id.classify_search:
                intent = new Intent(context, HomeSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setPopup() {
        view = LayoutInflater.from(context).inflate(R.layout.popup_classity, null);
        TextView relresh = (TextView) view.findViewById(R.id.popup_refresh);
        TextView message = (TextView) view.findViewById(R.id.popup_message);
        TextView home = (TextView) view.findViewById(R.id.popup_home);
        relresh.setOnClickListener(this);
        message.setOnClickListener(this);
        home.setOnClickListener(this);
    }


}
