package com.android.yzd.ui.activity;

import android.app.Dialog;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.yzd.R;
import com.android.yzd.been.PayFindResultEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.SPUtils;
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

    private String findResult="获取结果失败";
    private String orderid=null;
    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        api= WXAPIFactory.createWXAPI(this,APP_ID,false);
        api.registerApp(APP_ID);
        orderid= (String) SPUtils.get(PayResultActivity.this, K.ORDER_NUMBER,"");
        title_tools.setNavigationIcon(R.mipmap.arrow_left_white);
        title_tools.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PayResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //加载数据
        initHttpResult();
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
    //开启微信分享
    private void shareSDK() {
        WXWebpageObject wxWebpageObject=new WXWebpageObject();
        wxWebpageObject.webpageUrl="http://www.tjyizhanda.com/Wap/Index/show.html";
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
    /*
 * 返回结果
 *
 */
    private PayFindResultEntity.DataBean find;
    private void initHttpResult() {
        if (orderid==null||"".equals(orderid)){
            Toast.makeText(this, "订单有误，请重试", Toast.LENGTH_SHORT).show();
            return;}
        httpParamet.clear();
        SubscriberOnNextListener onNextListener=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                find=gson.fromJson(gson.toJson(o),PayFindResultEntity.DataBean.class);
                findResult=find.getStatus();
                initData();
            }
        };
        setProgressSubscriber(onNextListener,true);
        httpParamet.addParameter("order_id", orderid);
        HttpMethods.getInstance(this).findPayResult(progressSubscriber,httpParamet.bulider());
    }

    private void initData() {
        if ("0".equals(findResult)){
            payTv.setText("支付失败");
            payImg.setImageResource(R.drawable.pay_error);
        }else if ("1".equals(findResult)){
            payTv.setText("购买成功");
            payImg.setImageResource(R.drawable.pay_success);
            useDialog();
        }
    }
}
