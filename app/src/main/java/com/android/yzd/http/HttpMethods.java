package com.android.yzd.http;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/29.
 */

public class HttpMethods {


    public static final String BASE_URL = "http://www.tjyizhanda.com/";


    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private HttpService httpService;
    private static Context mContext;

    private HttpMethods(Context context) {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .client(getCilent(context).build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods(mContext);
    }


    //获取单例
    public static HttpMethods getInstance(Context context) {
        mContext = context;
        return SingletonHolder.INSTANCE;
    }

    //okhttp配置retrofit2
    private OkHttpClient.Builder getCilent(Context context) {

        File httpCacheDirectory = new File(mContext.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 100 * 1024 * 1024);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addNetworkInterceptor(interceptor);
        client.interceptors().add(new ReceivedCookiesInterceptor(context));//这些可以分开建立，先建立retrofit再添加builder
        client.interceptors().add(new AddCookiesInterceptor(context));
        client.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);//设置超时
        //client.retryOnConnectionFailure(true);//错误重连
        client.cache(cache);//配置缓存
        return client;
    }

    // 设置max-age为60s之后，这60s之内不管你有没有网,都读缓存。（这也就说明了为什么不能实现我上面说的功能）；
    // max-stale设置为4周，意思是，网络未连接的情况下设置缓存时间为4周。----这里没有写
    Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60";
            }
          /*  else {
                //无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }*/
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)//header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        }
    };

    //添加线程管理并订阅
    private void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getMessage().equals("暂无优惠券"))
                return httpResult.getData();
            if (httpResult.getMessage().equals("暂无数据"))
                return httpResult.getData();
            if (httpResult.getMessage().equals("无更多数据"))
                return httpResult.getData();
            if (httpResult.getMessage().equals("无收货地址"))
                return httpResult.getData();
            if (httpResult.getFlag().equals("error")) {
                throw new ApiException(httpResult.getMessage());
            }
            return httpResult.getData();
        }
    }


    /**
     * 获取验证码
     *
     * @param subscriber
     * @param param
     */
    public void sendVerify(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.sendVerify(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 验证验证码
     *
     * @param subscriber
     * @param param
     */
    public void checkVerify(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.checkVerify(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 绑定手机号
     *
     * @param subscriber
     * @param param
     */
    public void bindPhone(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.bindPhone(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
    /**
     * 注册协议
     *
     * @param subscriber
     */
    public void userRegAgr(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.userRegAgr(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber
     * @param param
     */
    public void register(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.register(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 忘记密码
     *
     * @param subscriber
     * @param param
     */
    public void forgetPassword(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.forgetPassword(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 登陆
     *
     * @param subscriber
     * @param param
     */
    public void login(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.login(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码
     *
     * @param subscriber
     * @param param
     */
    public void modifyPassword(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.modifyPassword(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码
     *
     * @param subscriber
     * @param param
     */
    public void setPassword(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.setPassword(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 个人中心
     *
     * @param subscriber
     * @param param
     */
    public void memberCenter(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.memberCenter(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 绑定手机号
     *
     * @param subscriber
     * @param param
     */
    public void modifyAccount(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.modifyAccount(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 用户资料
     *
     * @param subscriber
     * @param param
     */
    public void memberBaseData(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.memberBaseData(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改个人资料
     *
     * @param subscriber
     * @param param
     */
    public void modifyMemberBaseData(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.modifyMemberBaseData(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }


    /**
     * 第三方登录
     *
     * @param subscriber
     * @param param
     */
    public void getThreeLogin(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.threeLogin(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }


    /**
     * 优惠券列表
     *
     * @param subscriber
     * @param param
     */
    public void couponList(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.couponList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //地址模块

    /**
     * 新增收货地址
     *
     * @param subscriber
     * @param param
     */
    public void addAddress(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.addAddress(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 收货地址列表
     *
     * @param subscriber
     * @param param
     */
    public void addressList(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.addressList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 收货地址详情
     *
     * @param subscriber
     * @param param
     */
    public void addressInfo(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.addressInfo(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 编辑收货地址
     *
     * @param subscriber
     * @param param
     */
    public void modifyAddress(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.modifyAddress(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 设置默认收货地址
     *
     * @param subscriber
     * @param param
     */
    public void setDefaultAddress(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.setDefaultAddress(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 删除收货地址
     *
     * @param subscriber
     * @param param
     */
    public void deleteAddress(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.deleteAddress(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }


    //消息模块

    /**
     * 信息首页
     *
     * @param subscriber
     * @param param
     */
    public void messageIndex(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.messageIndex(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 系统消息列表
     *
     * @param subscriber
     * @param param
     */
    public void systemMessageList(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.systemMessageList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 系统消息详情
     *
     * @param subscriber
     * @param param
     */
    public void systemMessageInfo(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.systemMessageInfo(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 订单提醒列表
     *
     * @param subscriber
     * @param param
     */
    public void orderNoticeList(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.orderNoticeList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 首页商品
     *
     * @param subscriber
     * @param param
     */
    public void index(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.index(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 热门
     *
     * @param subscriber
     */
    public void hotSearch(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.hotSearch(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 商品分类列表
     *
     * @param subscriber
     */
    public void goodsTypeList(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.goodsTypeList(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 搜索
     *
     * @param subscriber
     */
    public void searchResult(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.searchResult(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 热销
     *
     * @param subscriber
     */
    public void hotSalesList(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.hotSalesList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 商品列表
     *
     * @param subscriber
     */
    public void goodsList(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.goodsList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 商品详情
     *
     * @param subscriber
     */
    public void goodsInfo(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.goodsInfo(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 新增收藏
     *
     * @param subscriber
     */
    public void addCollect(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.addCollect(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 删除收藏
     *
     * @param subscriber
     */
    public void deleteCollect(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.deleteCollect(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加购物车
     *
     * @param subscriber
     */
    public void addCart(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.addCart(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 收藏列表
     *
     * @param subscriber
     */
    public void collectList(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.collectList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 收藏列表
     *
     * @param subscriber
     */
    public void modifyCart(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.modifyCart(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 修改购物车
     *
     * @param subscriber
     */
    public void deleteCart(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.deleteCart(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 购物车列表
     *
     * @param subscriber
     */
    public void cartList(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.cartList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 购物车移入收藏
     *
     * @param subscriber
     */
    public void cartToCollect(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.cartToCollect(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 发现首页
     *
     * @param subscriber
     */
    public void findIndex(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.findIndex(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     *新发现首页
     *
     * @param subscriber
     */
    public void newFindIndex(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.newFindIndex(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
    /**
     * 积分商城
     *
     * @param subscriber
     */
    public void integralShop(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.integralShop(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 新积分商城
     *
     * @param subscriber
     */
    public void newIntegralShop(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.newIntegralShop(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 积分兑换记录
     *
     * @param subscriber
     */
    public void exchangeLog(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.exchangeLog(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
    /**
     * 新积分兑换记录
     *
     * @param subscriber
     */
    public void newExchangeLog(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.newExchangeLog(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 兑换次数
     *
     * @param subscriber
     */
    public void getMyExchange(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.getMyExchange(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 我的积分
     *
     * @param subscriber
     */
    public void getMyIntegral(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.getMyIntegral(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 我的等级
     *
     * @param subscriber
     */
    public void getMyDegree(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.getMyDegree(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取一条收货地址
     *
     * @param subscriber
     */
    public void getOneAddress(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.getOneAddress(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 兑换商品
     *
     * @param subscriber
     */
    public void exchangeGoods(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.exchangeGoods(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 新的兑换商品
     *
     * @param subscriber
     */
    public void exchangeCoupon(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.exchangeCoupon(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
    /**
     * 新增订单
     *
     * @param subscriber
     */
    public void addOrder(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.addOrder(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 积分问题
     *
     * @param subscriber
     */
    public void scoreProblem(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.scoreProblem(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 支付说明
     *
     * @param subscriber
     */
    public void payDescription(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.payDescription(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
    /**
     * 积分规则
     *
     * @param subscriber
     */
    public void getIntegralRule(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.getIntegralRule(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 等级规则
     *
     * @param subscriber
     */
    public void getDegreeRule(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.getDegreeRule(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 订单列表
     *
     * @param subscriber
     */
    public void orderList(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.orderList(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 取消订单
     *
     * @param subscriber
     */
    public void cancelOrder(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.cancelOrder(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 删除订单
     *
     * @param subscriber
     */
    public void deleteOrder(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.deleteOrder(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 意见反馈
     *
     * @param subscriber
     */
    public void addFeedback(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.addFeedback(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 订单详情
     *
     * @param subscriber
     */
    public void orderInfo(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.orderInfo(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 订单详情
     *
     * @param subscriber
     */
    public void confirmOrder(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.confirmOrder(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 设置界面
     *
     * @param subscriber
     */
    public void setPage(Subscriber<HttpResult> subscriber, Map<String, String> param) {
        Observable observable = httpService.setPage(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 安卓检查更新
     *
     * @param subscriber
     */
    public void upgrade(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.upgrade(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 请求支付订单
     *
     * @param subscriber
     */
    public void addPayOrder(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.addPayOrder(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 请求阿里支付
     *
     * @param subscriber
     */
    public void getAliParam(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.getAliParam(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 请求微信支付
     *
     * @param subscriber
     */
    public void getWxParam(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.getWxParam(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 请求支付结果
     *
     * @param subscriber
     */
    public void findPayResult(Subscriber<HttpResult> subscriber, Map<String, RequestBody> param) {
        Observable observable = httpService.findPayResult(param)
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 请求运费
     *
     * @param subscriber
     */
    public void getDeliveryFee(Subscriber<HttpResult> subscriber) {
        Observable observable = httpService.getDeliveryFee(new HashMap<String, String>())
                .map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

}
