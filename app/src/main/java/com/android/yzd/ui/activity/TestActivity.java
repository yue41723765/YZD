package com.android.yzd.ui.activity;

import android.app.Dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.Gravity;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;



import butterknife.BindView;


/**
 * Created by 33181 on 2017/9/4.
 */

public class TestActivity extends BaseActivity  {
    @BindView(R.id.pay_result_tv)TextView test;
    @BindView(R.id.pay_result_img)ImageView payImg;

    private IWXAPI api;
    //微信
    private static final String APP_ID="wx5c5be12f9933f83d";
    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        api= WXAPIFactory.createWXAPI(this,APP_ID,false);
        api.registerApp(APP_ID);
        test.setText("测试文本");
        payImg.setImageResource(R.mipmap.wechate);
        useDialog();
    }

    private void useDialog() {
        Dialog dialog=new Dialog(this);
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
