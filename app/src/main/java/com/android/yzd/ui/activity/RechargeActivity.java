package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/9 0009.
 * 充值
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.pay_text)
    TextView payText;
    @BindView(R.id.edit_money)
    EditText editMoney;
    @BindView(R.id.balance)
    TextView balance;

    @BindView(R.id.title_1)
    TextView title1;
    @BindView(R.id.title_2)
    TextView title2;
    @BindView(R.id.pay_type)
    LinearLayout payType;
    @BindView(R.id.status_1)
    LinearLayout status1;
    @BindView(R.id.status_2)
    TextView status2;
    @BindView(R.id.recharge)
    Button recharge;


    PopupWindow popupWindow;
    View view;
    @BindView(R.id.withdraw_deposit)
    TextView withdrawDeposit;

    @Override
    public int getContentViewId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        int status = getIntent().getExtras().getInt(K.STATUS);
        switch (status) {
            case 1:
                break;
            case 2:
                title.setText("提现");
                title1.setText("提现方式");
                title2.setText("提出现金");
                editMoney.setHint("请输入提现金额");
                recharge.setText("提现");
                status2.setText("提现成功! ");
                withdrawDeposit.setVisibility(View.VISIBLE);
                break;
        }


        view = getLayoutInflater().inflate(R.layout.popup_recharge, null);
        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                U.backgroundAlpha(RechargeActivity.this, 1f);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.pay_type:
                U.backgroundAlpha(RechargeActivity.this, 0.5f);
                popupWindow.setContentView(view);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.popup_alipay:
                payText.setText("支付宝支付");
                popupWindow.dismiss();
                break;
            case R.id.popup_wechate:
                payText.setText("微信支付");
                popupWindow.dismiss();
                break;
            case R.id.add_other:
                intent = new Intent(this, BankCardActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
                break;
            case R.id.withdraw_deposit:
                //全部提现
                break;
            case R.id.recharge:
                //充值
                status1.setVisibility(View.GONE);
                status2.setVisibility(View.VISIBLE);
                recharge.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
