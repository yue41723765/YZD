package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class RemarkActivity extends BaseActivity {

    public static final String RemarkResult = "remark";
    public static final int ResultNumber = 11;
    @BindView(R.id.remark_edit)
    EditText remarkEdit;

    @Override
    public int getContentViewId() {
        return R.layout.activity_remark;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
    }


    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.remark_save:
                intent = new Intent();
                intent.putExtra(RemarkResult, remarkEdit.getText().toString());
                setResult(ResultNumber, intent);
                finish();
                break;
        }
    }
}
