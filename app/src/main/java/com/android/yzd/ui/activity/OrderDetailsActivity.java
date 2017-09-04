package com.android.yzd.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.OrderDetailsEntity;
import com.android.yzd.been.UserRegAgr;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.T;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.fragment.OrderFragment;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.TitleBarView;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.title_tools)
    TitleBarView titleTools;
    @BindView(R.id.link_service)
    TextView linkService;
    @BindView(R.id.order_pay)
    Button orderPay;
    @BindView(R.id.order_cancel)
    Button orderCancel;
    @BindView(R.id.bottom_tools)
    RelativeLayout bottomTools;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.order_recycler)
    RecyclerView orderRecycler;
    @BindView(R.id.order_freight)
    TextView orderFreight;
    @BindView(R.id.order_coupon)
    TextView orderCoupon;
    @BindView(R.id.goods_number)
    TextView goodsNumber;
    @BindView(R.id.order_all_price)
    TextView orderAllPrice;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.invoice_head)
    TextView invoiceHead;
    @BindView(R.id.order_remark)
    TextView orderRemark;
    @BindView(R.id.remark)
    RelativeLayout remark;
    @BindView(R.id.order_name_tel)
    TextView orderNameTel;
    @BindView(R.id.order_address)
    TextView orderAddress;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.order_content)
    ScrollView orderContent;

    String order_id;
    OrderDetailsEntity orderDetails;

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        order_id = getIntent().getStringExtra(K.ORDER_ID);
        getOrderDetails(order_id);
    }

    private void getOrderDetails(String order_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                orderDetails = gson.fromJson(gson.toJson(o), OrderDetailsEntity.class);
                showUi(orderDetails);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("order_id", order_id);
        HttpMethods.getInstance(this).orderInfo(progressSubscriber, httpParamet.bulider());
    }

    private void showUi(OrderDetailsEntity orderDetails) {
        switch (orderDetails.getStatus()) {
            case "0":
                orderPay.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.VISIBLE);
                orderCancel.setText("取消订单");
                orderPay.setText("查看支付方式");
                orderStatus.setText("待支付");
                break;
            case "1":
                orderStatus.setText("待发货");
                orderCancel.setVisibility(View.VISIBLE);
                orderCancel.setText("取消订单");
                break;
            case "2":
                orderStatus.setText("待收货");
                orderPay.setVisibility(View.VISIBLE);
                orderPay.setText("确定收货");
                break;
            case "3":
                orderStatus.setText("已完成");
                orderCancel.setVisibility(View.VISIBLE);
                orderCancel.setText("删除订单");
                break;
            case "4":
                orderStatus.setText("已取消");
                orderCancel.setVisibility(View.VISIBLE);
                orderCancel.setText("删除订单");
                break;
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        orderRecycler.setLayoutManager(linearLayoutManager);
        Drawable drawable1 = getResources().getDrawable(android.R.color.white);
        orderRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable1, DensityUtils.dp2px(this, 3)));
        CommonAdapter adapter = new CommonAdapter<OrderDetailsEntity.GoodsListBean>(this, R.layout.item_order_item, orderDetails.getGoods_list()) {

            @Override
            protected void convert(ViewHolder holder, OrderDetailsEntity.GoodsListBean s, int position) {
                Picasso.with(OrderDetailsActivity.this).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.order_image));
                holder.setText(R.id.order_title_, s.getGoods_name());
                holder.setText(R.id.order_number, "x" + s.getNumber());
                holder.setText(R.id.order_price, "￥" + s.getGoods_price());
            }
        };
        orderRecycler.setAdapter(adapter);


        orderFreight.setText("￥" + orderDetails.getDelivery_price());
        orderRemark.setText(orderDetails.getRemark().equals("") ? "无" : orderDetails.getRemark());
        orderNameTel.setText(orderDetails.getName() + "       " + orderDetails.getMobile());
        orderAddress.setText(orderDetails.getAddress());
        orderNumber.setText(orderDetails.getOrder_sn());
        orderTime.setText(U.timeStampToStr(orderDetails.getCreate_time()));

        orderAllPrice.setText("￥" + orderDetails.getOrder_price());

        if (orderDetails.getIs_use_coupon().equals("0")) {
            orderCoupon.setText("未使用优惠券");
        } else {
            orderCoupon.setText("￥" + orderDetails.getCoupon_value());
        }

    }

    //查看支付方式
    private void payDescription() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr ura = gson.fromJson(gson.toJson(o), UserRegAgr.class);
                intent = new Intent(OrderDetailsActivity.this, WebView.class);
                intent.putExtra(K.DATA, ura);
                startActivity(intent);
            }
        };
        setProgressSubscriber(onNextListener, false);
        HttpMethods.getInstance(this).payDescription(progressSubscriber);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.order_pay:
                switch (orderDetails.getStatus()) {
                    case "0":
                        payDescription();
                        break;
                    case "2":
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示");
                        builder.setMessage("是否确定收货?");
                        builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                confirmOrder(order_id);
                            }
                        });
                        builder.show();
                        break;
                }
                break;
            case R.id.order_cancel:
                switch (orderDetails.getStatus()) {
                    case "0":
                    case "1":
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示");
                        builder.setMessage("是否取消订单?");
                        builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                cancelOrder(order_id);
                            }
                        });

                        builder.show();
                        break;
                    case "3":
                    case "4":
                        builder = new AlertDialog.Builder(this);
                        builder.setTitle("提示");
                        builder.setMessage("是否删除订单?");
                        builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteOrder(order_id);
                            }
                        });

                        builder.show();
                        break;
                }
                break;
        }
    }


    //取消订单
    private void cancelOrder(String order_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(OrderDetailsActivity.this, "取消订单成功", Toast.LENGTH_SHORT);
                intent = new Intent(OrderFragment.REFRESH);
                sendBroadcast(intent);
                finish();
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("order_id", order_id);
        HttpMethods.getInstance(this).cancelOrder(progressSubscriber, httpParamet.bulider());
    }

    //删除订单
    private void deleteOrder(String order_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(OrderDetailsActivity.this, "取消订单成功", Toast.LENGTH_SHORT);
                intent = new Intent(OrderFragment.REFRESH);
                sendBroadcast(intent);
                finish();
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("order_id", order_id);
        HttpMethods.getInstance(this).deleteOrder(progressSubscriber, httpParamet.bulider());
    }

    //确认收货
    private void confirmOrder(final String order_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确认收货？");
        builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
                    @Override
                    public void onNext(Object o) {
                        T.show(OrderDetailsActivity.this, "确认收货成功", Toast.LENGTH_SHORT);
                        intent = new Intent(OrderFragment.REFRESH);
                        sendBroadcast(intent);
                        finish();
                    }
                };
                setProgressSubscriber(onNextListener);
                httpParamet.clear();
                httpParamet.addParameter("order_id", order_id);
                HttpMethods.getInstance(OrderDetailsActivity.this).confirmOrder(progressSubscriber, httpParamet.bulider());
            }
        });
        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
