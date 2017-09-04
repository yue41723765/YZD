package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.feedback_content)
    EditText feedbackContent;
    @BindView(R.id.feedback_tel)
    EditText feedbackTel;
    @BindView(R.id.feedback_submit)
    Button feedbackSubmit;
    @BindView(R.id.editHint)
    TextView editHint;

    @Override
    public int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        feedbackContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editHint.setText(200 - s.length() + "字以内");
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.feedback_submit:
                String content = feedbackContent.getText().toString();
                String link = feedbackTel.getText().toString();

                if (content.equals("")) {
                    T.show(this, "内容不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                if (link.equals("")) {
                    T.show(this, "联系方式不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                addFeedback(content, link);
                break;
        }
    }

    private void addFeedback(String content, String contact) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(FeedBackActivity.this, "意见反馈成功", Toast.LENGTH_SHORT);
                finish();
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("content", content);
        httpParamet.addParameter("contact", contact);
        HttpMethods.getInstance(this).addFeedback(progressSubscriber, httpParamet.bulider());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
