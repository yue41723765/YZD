package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.yzd.R;
import com.android.yzd.been.QuestionEntity;
import com.android.yzd.ui.custom.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class IntegralQuestionActivity extends BaseActivity {

    @BindView(R.id.question_recycler)
    RecyclerView questionRecycler;


    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_integral_question;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        getQuestionList();
    }


    private void setList(List<QuestionEntity> list) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        questionRecycler.setLayoutManager(linearLayoutManager);
        adapter = new CommonAdapter<QuestionEntity>(this, R.layout.item_question, list) {

            @Override
            protected void convert(ViewHolder holder, QuestionEntity s, int position) {
                holder.setText(R.id.question_title, "Q" + (position + 1) + ":" + s.getTitle());
                holder.setText(R.id.question_content, s.getContent());
            }
        };
        questionRecycler.setAdapter(adapter);
    }
}
