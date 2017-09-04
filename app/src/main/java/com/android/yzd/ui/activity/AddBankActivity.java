package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class AddBankActivity extends BaseActivity {
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_banNumber)
    EditText editBanNumber;
    @BindView(R.id.from_bank)
    TextView fromBank;
    @BindView(R.id.edit_id_card)
    EditText editIdCard;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.addBank_next)
    Button addBankNext;
    @BindView(R.id.status_2)
    LinearLayout status2;
    @BindView(R.id.status_1)
    LinearLayout status1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.from_bank:
                intent = new Intent(this, BankCardTypeActivity.class);
                startActivity(intent);
                break;
        }
    }

}
