package com.android.yzd.ui.activity;

import android.app.Dialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 33181 on 2017/7/21.
 */

public class PayResultActivity extends BaseActivity {
    @BindView(R.id.pay_result_tv)TextView payTv;
    @BindView(R.id.title_tools)Toolbar title_tools;
    @BindView(R.id.pay_result_img)ImageView payImg;


    private IWXAPI api;
    private static final String APP_ID="wx5c5be12f9933f83d";
    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        api= WXAPIFactory.createWXAPI(this,APP_ID,false);
        api.registerApp(APP_ID);

        title_tools.setNavigationIcon(R.mipmap.arrow_left_white);
        title_tools.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PayResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        intent=getIntent();
        String result=intent.getStringExtra("PayResult");
        String wxResult=intent.getStringExtra("WXResult");
        if ("arr".equals(result)){
            payTv.setText("已成功下单,请保持手机通畅");
            payImg.setVisibility(View.INVISIBLE);
        }else  if ("0".equals(result)){
            payTv.setText("支付失败");
            payImg.setImageResource(R.mipmap.alipay);
        }else if ("1".equals(result)){
            payTv.setText("支付成功");
            payImg.setImageResource(R.mipmap.alipay);
            useDialog();
        }else if ("0".equals(wxResult)){
            payTv.setText("支付成功");
            payImg.setImageResource(R.mipmap.wechate);
            useDialog();
        }else if ("-1".equals(wxResult)){
            payTv.setText("支付失败");
            payImg.setImageResource(R.mipmap.wechate);
        }else if ("-2".equals(wxResult)){
            payTv.setText("支付失败");
            payImg.setImageResource(R.mipmap.wechate);
        }else {
            payImg.setVisibility(View.INVISIBLE);
            payTv.setText("抱歉，查询不到此订单");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(PayResultActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
   //弹出死丑窗口
    private void useDialog() {
        Dialog dialog=new Dialog(this,R.style.Theme_AppCompat_Dialog);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.pay_share_dialog);
        dialog.show();
        dialog.findViewById(R.id.share_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSDK( );
            }
        });
    }
    //开启微信分享
    private void shareSDK() {
        WXWebpageObject wxWebpageObject=new WXWebpageObject();
        wxWebpageObject.webpageUrl="http://www.tjyizhanda.com";
        WXMediaMessage msg=new WXMediaMessage(wxWebpageObject);
        msg.title="一站达";
        msg.description="听说了么，家装建材免费送，价格也比市场价低";
        SendMessageToWX.Req req=new SendMessageToWX.Req();
        Bitmap  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.share_icon);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap,200,200,true);
        msg.setThumbImage(thumbBmp);
        thumbBmp.recycle();
        req.transaction=String.valueOf(System.currentTimeMillis());
        req.message=msg;
        req.scene=SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }
}
