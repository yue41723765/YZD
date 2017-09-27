package com.android.yzd.wxapi;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.tools.L;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.ui.activity.LoginActivity;
import com.android.yzd.ui.activity.MainActivity;
import com.android.yzd.ui.activity.PayResultActivity;
import com.android.yzd.ui.custom.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private static final String TAG = "com.android.yzd.WXPayEntryActivity";
	public static final String APP_ID = "wx5c5be12f9933f83d";
    private IWXAPI api;

	@Override
	public int getContentViewId() {
		return R.layout.activity_wx_result;
	}

	@Override
	protected void initAllMembersView(Bundle savedInstanceState) {
		api = WXAPIFactory.createWXAPI(this,APP_ID);

		api.handleIntent(getIntent(), this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			StatusBarUtil.transparencyBar(this);
		}
		ButterKnife.bind(this);

	}
	@Override
	public void onReq(BaseReq baseReq) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Intent intent = new Intent(WXPayEntryActivity.this, PayResultActivity.class);
			//intent.putExtra("WXResult", "" + resp.errCode);
			startActivity(intent);
			finish();
		}else {
			Intent intent = new Intent(WXPayEntryActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

}