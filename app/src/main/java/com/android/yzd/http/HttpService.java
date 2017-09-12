package com.android.yzd.http;


import com.android.yzd.been.PayAddOrderEntity;
import com.android.yzd.been.PayFindResultEntity;
import com.android.yzd.been.PayGetAliParamEntity;
import com.android.yzd.been.PayGetWxParamEntity;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/29.
 */

public interface HttpService {

    @FormUrlEncoded
    @POST("index.php/Api/Sms/sendVerify")
    Observable<HttpResult> sendVerify(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Sms/checkVerify")
    Observable<HttpResult> checkVerify(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST("index.php/Api/Article/userRegAgr")
    Observable<HttpResult> userRegAgr(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/register")
    Observable<HttpResult> register(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST("index.php/Api/Member/forgetPass")
    Observable<HttpResult> forgetPassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/login")
    Observable<HttpResult> login(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/modifyPass")
    Observable<HttpResult> modifyPassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/setPass")
    Observable<HttpResult> setPassword(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/memberCenter")
    Observable<HttpResult> memberCenter(@FieldMap Map<String, String> param);


    @FormUrlEncoded
    @POST("index.php/Api/Member/threeBindAccount")
    Observable<HttpResult> bindPhone(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/modifyAccount")
    Observable<HttpResult> modifyAccount(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/memberBaseData")
    Observable<HttpResult> memberBaseData(@FieldMap Map<String, String> param);

    @Multipart
    @POST("index.php/Api/Member/modifyMemberBaseData")
    Observable<HttpResult> modifyMemberBaseData(@PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("index.php/Api/Member/couponList")
    Observable<HttpResult> couponList(@FieldMap Map<String, String> param);

    //地址模块

    @FormUrlEncoded
    @POST("index.php/Api/Address/addAddress")
    Observable<HttpResult> addAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/addressList")
    Observable<HttpResult> addressList(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/addressInfo")
    Observable<HttpResult> addressInfo(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/modifyAddress")
    Observable<HttpResult> modifyAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/setDefaultAddress")
    Observable<HttpResult> setDefaultAddress(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Address/deleteAddress")
    Observable<HttpResult> deleteAddress(@FieldMap Map<String, String> param);


    //消息模块
    @FormUrlEncoded
    @POST("index.php/Api/Message/messageIndex")
    Observable<HttpResult> messageIndex(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Message/systemMessageList")
    Observable<HttpResult> systemMessageList(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Message/systemMessageInfo")
    Observable<HttpResult> systemMessageInfo(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Message/orderNoticeList")
    Observable<HttpResult> orderNoticeList(@FieldMap Map<String, String> param);

    //    商品模块
    @Multipart
    @POST("index.php/Api/Goods/index")
    Observable<HttpResult> index(@PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("index.php/Api/Goods/hotSearch")
    Observable<HttpResult> hotSearch(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Goods/goodsTypeList")
    Observable<HttpResult> goodsTypeList(@FieldMap Map<String, String> param);

    @Multipart
    @POST("index.php/Api/Goods/searchResult")
    Observable<HttpResult> searchResult(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/hotSalesList")
    Observable<HttpResult> hotSalesList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/goodsList")
    Observable<HttpResult> goodsList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/goodsInfo")
    Observable<HttpResult> goodsInfo(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/addCollect")
    Observable<HttpResult> addCollect(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/deleteCollect")
    Observable<HttpResult> deleteCollect(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/addCart")
    Observable<HttpResult> addCart(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/collectList")
    Observable<HttpResult> collectList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/modifyCart")
    Observable<HttpResult> modifyCart(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/deleteCart")
    Observable<HttpResult> deleteCart(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/cartList")
    Observable<HttpResult> cartList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Goods/cartToCollect")
    Observable<HttpResult> cartToCollect(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Find/findIndex")
    Observable<HttpResult> findIndex(@PartMap Map<String, RequestBody> param);


    @Multipart
    @POST("index.php/Api/FindNew/findIndex")
    Observable<HttpResult> newFindIndex(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Find/integralShop")
    Observable<HttpResult> integralShop(@PartMap Map<String, RequestBody> param);


    @Multipart
    @POST("index.php/Api/FindNew/integralShop")
    Observable<HttpResult> newIntegralShop(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Find/exchangeLog")
    Observable<HttpResult> exchangeLog(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/FindNew/exchangeLog")
    Observable<HttpResult> newExchangeLog(@PartMap Map<String, RequestBody> param);


    @Multipart
    @POST("index.php/Api/Member/myExchange")
    Observable<HttpResult> getMyExchange(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Member/myIntegral")
    Observable<HttpResult> getMyIntegral(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Member/myDegree")
    Observable<HttpResult> getMyDegree(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Address/getOneAddress")
    Observable<HttpResult> getOneAddress(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Find/exchangeGoods")
    Observable<HttpResult> exchangeGoods(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/FindNew/exchangeCoupon")
    Observable<HttpResult> exchangeCoupon(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/addOrder")
    Observable<HttpResult> addOrder(@PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("index.php/Api/Article/scoreProblem")
    Observable<HttpResult> scoreProblem(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Article/payDescription")
    Observable<HttpResult> payDescription(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Article/integralRule")
    Observable<HttpResult> getIntegralRule(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Article/degreeRule")
    Observable<HttpResult> getDegreeRule(@FieldMap Map<String, String> param);

    @Multipart
    @POST("index.php/Api/Order/orderList")
    Observable<HttpResult> orderList(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/cancelOrder")
    Observable<HttpResult> cancelOrder(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/deleteOrder")
    Observable<HttpResult> deleteOrder(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Article/addFeedback")
    Observable<HttpResult> addFeedback(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/orderInfo")
    Observable<HttpResult> orderInfo(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/confirmOrder")
    Observable<HttpResult> confirmOrder(@PartMap Map<String, RequestBody> param);

    @FormUrlEncoded
    @POST("index.php/Api/Article/setPage")
    Observable<HttpResult> setPage(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("index.php/Api/Upgrade/upgrade")
    Observable<HttpResult> upgrade(@FieldMap Map<String, String> param);

    //运费
    @FormUrlEncoded
    @POST("index.php/Api/Order/getDeliveryFee")
    Observable<HttpResult> getDeliveryFee(@FieldMap Map<String, String> param);
    //支付

    //添加订单 ，这里边写了两个 上边的没用也没删除
    @Multipart
    @POST("index.php/Api/Order/addOrder")
    Observable<HttpResult> addPayOrder(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/getAlipayParameter")
    Observable<HttpResult> getAliParam(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/getWXPayParam")
    Observable<HttpResult> getWxParam(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Order/findPayResult")
    Observable<HttpResult> findPayResult(@PartMap Map<String, RequestBody> param);

    @Multipart
    @POST("index.php/Api/Member/threeLogin")
    Observable<HttpResult> threeLogin(@PartMap Map<String, RequestBody> param);
}
