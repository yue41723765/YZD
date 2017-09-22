package com.android.yzd.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.yzd.R;
import com.android.yzd.been.PayAddOrderEntity;
import com.android.yzd.been.PayFindResultEntity;
import com.android.yzd.been.PayGetAliParamEntity;
import com.android.yzd.been.PayGetWxParamEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.L;
import com.android.yzd.ui.custom.BaseActivity;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class PayActivity extends BaseActivity {

    //支付回调
    private static final int SDK_PAY_FLAG=1;


    //微信API
    private IWXAPI api;

    //订单统计
    private String orderInfo;
    //订单编号
    private String orderid;
    private String findResult;

    //获取订单编号参数
    private String m_id, cart_json, address_id, m_c_id, remark;
    //参数-wx
    private PayReq req;
    //结果参数
    private static final int BAOM_ALI_YANZHENG = 100;

    private static final int BAOM_WEIXIN_YANZHENG = 101;

    private static final int BAOM_ARRIVE_YANZHENG = 102;

    //键的集合
    @BindView(R.id.pay_group)RadioGroup pay_group;
    @BindView(R.id.pay_wechate)RadioButton pay_wechate;
    @BindView(R.id.pay_alipay)RadioButton pay_alipay;
    @BindView(R.id.sure_pay)Button sure_pay;
    @BindView(R.id.pay_title)TextView pay_title;
    //微信
    private static final String APP_ID="wx5c5be12f9933f83d";
    //公用的
    private int number=BAOM_ARRIVE_YANZHENG;
    //钱
    private Float money=0.0f;
    private String price=null;
    private String delivery_price;

    private PayAddOrderEntity.DataBean add;
    private PayGetAliParamEntity.DataBean ali;
    private PayGetWxParamEntity.DataBean wx;
    private PayFindResultEntity.DataBean find;
    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_order;
    }
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        //承接前边的参数
        intent=getIntent();
        m_id=intent.getStringExtra("m_id");
        cart_json=intent.getStringExtra("cart_json");
        address_id=intent.getStringExtra("address_id");
        m_c_id=intent.getStringExtra("m_c_id");
        remark=intent.getStringExtra("remark");
        money=intent.getFloatExtra("money",0);
        delivery_price=intent.getStringExtra("delivery_price");
        orderid=intent.getStringExtra("order_id");
        price=intent.getStringExtra("order_price");
        //微信注册在APP内
        api = WXAPIFactory.createWXAPI(PayActivity.this,null);
        api.registerApp(APP_ID);
        //三个radioButton的选项
        pay_group.setOnCheckedChangeListener(isPayGroupOnclick);
        if (money==0.0f){
            pay_title.setText("共计：¥"+price+"元");
        }else {
        pay_title.setText("共计：¥"+money+"元");
        }
        if (orderid==null||"".equals(orderid)){
            initHttpAddOrder();
        }
    }

    private RadioGroup.OnCheckedChangeListener isPayGroupOnclick=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId){
                case R.id.pay_alipay:
                    number=BAOM_ALI_YANZHENG;
                    break;
                case R.id.pay_wechate:
                    number=BAOM_WEIXIN_YANZHENG;
                    break;
                case R.id.pay_balance:
                    number=BAOM_ARRIVE_YANZHENG;
                    break;
                default:break;
            }
        }
    };
    //确认订单
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sure_pay:
                if (number==BAOM_ALI_YANZHENG){
                    initHttpAli();
                }else if (number==BAOM_WEIXIN_YANZHENG){
                    initHttpWeiXin();;
                }else if (number==BAOM_ARRIVE_YANZHENG){
                    intent=new Intent(PayActivity.this,PayResultActivity.class);
                    intent.putExtra("PayResult","arr");
                    startActivity(intent);
                }break;
        }
    }
        /*
         * 添加订单号
         *
         */
    private void initHttpAddOrder() {
        SubscriberOnNextListener onNextListener=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                add=gson.fromJson(gson.toJson(o),PayAddOrderEntity.DataBean.class);
                if (add.getOrder_id()!=null) {
                    orderid = add.getOrder_id();
                }else {
                    Toast.makeText(PayActivity.this, "add订单参数不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("m_id", m_id);
        httpParamet.addParameter("cart_json",cart_json);
        httpParamet.addParameter("address_id", address_id);
        httpParamet.addParameter("m_c_id", m_c_id);
        httpParamet.addParameter("remark", remark);
        httpParamet.addParameter("delivery_price",delivery_price);
        HttpMethods.getInstance(PayActivity.this).addPayOrder(progressSubscriber,httpParamet.bulider());
    }
    /*
   * 支付宝 支付前的验证
   *
   */
    private void initHttpAli() {
        SubscriberOnNextListener onNextListener=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                ali=gson.fromJson(gson.toJson(o),PayGetAliParamEntity.DataBean.class);
                if (ali.getPay_string()!=null){
                    orderInfo=ali.getPay_string();
                    Runnable payRunnable=new Runnable() {
                        @Override
                        public void run() {
                            PayTask aliPay=new PayTask(PayActivity.this);
                            Map<String,String> result=aliPay.payV2(orderInfo,true);
                            Message msg=new Message();
                            msg.what=SDK_PAY_FLAG;
                            msg.obj=result;
                            mHandler.sendMessage(msg);
                        }
                    };
                    Thread payThread=new Thread(payRunnable);
                    payThread.start();
                }else {
                    Toast.makeText(PayActivity.this, "ali订单参数不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("order_id", orderid);
        HttpMethods.getInstance(this).getAliParam(progressSubscriber,httpParamet.bulider());
    }
    /*
    * 微信 支付前的验证
    *
    */
    private void initHttpWeiXin() {
        SubscriberOnNextListener onNextListener=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                L.d("TAG","订单参数----------"+o);
                wx=gson.fromJson(gson.toJson(o),PayGetWxParamEntity.DataBean.class);
                if (wx!=null){
                    req=new PayReq();
                    req.appId=wx.getAppid();
                    req.sign=wx.getSign();
                    req.nonceStr=wx.getNonce_str();
                    req.packageValue=wx.getPackageX();
                    req.timeStamp=wx.getTime_stamp();
                    req.prepayId=wx.getPrepay_id();
                    req.partnerId=wx.getMch_id();
                    toPay();
                }else {
                    Toast.makeText(PayActivity.this, "wx订单参数不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("order_id", orderid);
        HttpMethods.getInstance(this).getWxParam(progressSubscriber,httpParamet.bulider());
    }
    /*
     * 返回结果
     *
     */
    private void initHttpResult() {
        httpParamet.clear();
        SubscriberOnNextListener onNextListener=new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                find=gson.fromJson(gson.toJson(o),PayFindResultEntity.DataBean.class);
                if (find!=null){
                    findResult=find.getStatus();
                    Intent intent=new Intent(PayActivity.this,PayResultActivity.class);
                    intent.putExtra("PayResult",findResult);
                    startActivity(intent);
                }else {
                    Toast.makeText(PayActivity.this, "find订单参数不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("order_id", orderid);
        HttpMethods.getInstance(this).findPayResult(progressSubscriber,httpParamet.bulider());
    }

    //支付端
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    initHttpResult();
                    break;
                }
                default:
                    break;
            }
        };
    };
    private void toPay() {
        //    api.registerApp(Constants.APP_ID);
        api.sendReq(req);
    }
}
