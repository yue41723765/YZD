package com.android.yzd.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.CartListBean;
import com.android.yzd.been.DeliverFeeEntity;
import com.android.yzd.been.DetailsEntity;
import com.android.yzd.been.ScEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.activity.AddOrderActivity;
import com.android.yzd.ui.activity.DetailsActivity;
import com.android.yzd.ui.activity.MessageManagerActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class ShoppingCartFragment extends BaseFragment {

    @BindView(R.id.sc_message)
    ImageView scMessage;
    @BindView(R.id.sc_edit)
    TextView scEdit;
    @BindView(R.id.top_tools)
    RelativeLayout topTools;
    @BindView(R.id.cs_choose_all)
    CheckBox csChooseAll;
    @BindView(R.id.sc_total_title)
    TextView scTotalTitle;
    @BindView(R.id.sc_total_price)
    TextView scTotalPrice;
    @BindView(R.id.close_an_account)
    TextView closeAnAccount;
    @BindView(R.id.sc_status_1)
    RelativeLayout scStatus1;
    @BindView(R.id.add_collect)
    Button addCollect;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.sc_status_2)
    LinearLayout scStatus2;
    @BindView(R.id.bottom_tools)
    RelativeLayout bottomTools;
    @BindView(R.id.sc_recycler)
    RecyclerView scRecycler;

    //之前的ischeck都被map替代了，原因是checkbox记不住状态
    //解决之后懒得改回来了 可以看看文档使用方法改回来http://www.cnblogs.com/RGogoing/p/5095168.html
    //SparseBooleanArray isCheck = new SparseBooleanArray();
    private Map<Integer,Boolean> map=new HashMap<>();

    CommonAdapter adapter;
    List<CartListBean> cartList = new ArrayList<>();
    UserInfoEntity userInfo;

    float delivery_price = 0;//运费

    @Override
    public int getContentViewId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        csChooseAll.setChecked(false);
        init();
        setAdapter();
    }

    private void getCartData() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                cartList.clear();
                ScEntity sc = gson.fromJson(gson.toJson(o), ScEntity.class);
                cartList.addAll(sc.getCart_list());
                adapter.notifyDataSetChanged();
                if (sc.getNot_read().equals("1")) {
                    scMessage.setImageResource(R.mipmap.home_message_);
                } else {
                    scMessage.setImageResource(R.mipmap.home_message);
                }
                delivery_price = sc.getDelivery_price();

                if (cartList.size() == 0) {
                    csChooseAll.setChecked(false);
                    scTotalPrice.setText("￥" + 0);
                }
                for (int i=0;i<cartList.size();i++){
                    map.put(i,false);
                }

            }
        };
        setProgressSubscriber(onNextListener);
        builder.clear();
        builder.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(context).cartList(progressSubscriber, builder.bulider());
    }

    private void setAdapter() {
        adapter = new CommonAdapter<CartListBean>(getContext(), R.layout.item_shopping_cart, cartList) {
            @Override
            protected void convert(final ViewHolder holder, final CartListBean s, final int position) {
                holder.setText(R.id.goods_title, s.getGoods_name());
                holder.setText(R.id.goods_price, "￥" + s.getGoods_price());
                holder.setText(R.id.order_buy_number, s.getNumber() + "");
                Picasso.with(getContext()).load(s.getGoods_logo()).into((ImageView) holder.getView(R.id.image));
                final CheckBox checkBox = holder.getView(R.id.item_check);
                if (map.containsKey(position)){
                    checkBox.setChecked(map.get(position));
                }else {
                    checkBox.setChecked(false);
                }

                checkBox.setChecked(map.get(position));
                holder.getView(R.id.item_check).setTag(position);
                checkBox.setTag(position);
                //添加商品
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()){
                            map.put(position,true);
                            totalPrice();
                        }else {
                            map.put(position,false);
                            csChooseAll.setChecked(false);
                        }
                    }
                });

                holder.setOnClickListener(R.id.add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int number = cartList.get(position).getNumber();
                        number += 1;
                        cartList.get(position).setNumber(number);
                        notifyDataSetChanged();

                        List<String> list = new ArrayList<String>();
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("goods_id", s.getGoods_id());
                        params.put("number", number + "");
                        list.add(gson.toJson(params));
                        //修改
                        modifyCart(list.toString());
                    }
                });
                //减少商品
                holder.setOnClickListener(R.id.order_minus, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int number = Integer.valueOf(cartList.get(position).getNumber());
                        number -= 1;
                        if (number < 0) {
                            //拼装格式，删除操作
                            List<String> list = new ArrayList<String>();
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("goods_id", s.getGoods_id());
                            list.add(gson.toJson(params));
                            removeShoppingCartGoods(list.toString());
                            cartList.remove(position);
                        } else {
                            cartList.get(position).setNumber(number);
                            List<String> list = new ArrayList<String>();
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("goods_id", s.getGoods_id());
                            params.put("number", number + "");
                            list.add(gson.toJson(params));
                            //修改
                            modifyCart(list.toString());
                        }
                        notifyDataSetChanged();
                    }
                });

                //中间文字 添加监听输入数字
                holder.setOnClickListener(R.id.order_buy_number, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int number=Integer.valueOf(cartList.get(position).getNumber());
                        showDialog(position,number,s.getGoods_id());
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                notifyDataSetChanged();
                            }
                        });
                    }
                });

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(K.GOODS_ID, cartList.get(position).getGoods_id());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        scRecycler.setAdapter(adapter);
    }

    //购物车改变数量的dialog
    //这个写的为初步
    private Dialog dialog;
    private void showDialog(final int position, int number, final String s){
        dialog=new Dialog(context);
        dialog.setContentView(R.layout.fragment_shopping_cart_dialog);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        dialog.show();
        final EditText initput= (EditText) dialog.findViewById(R.id.initPutDialog);
        //这个地方出现过两次错误标注一下 不能为int类型
        initput.setText(number+"");
        initput.setSelection(initput.getText().length());
        dialog.findViewById(R.id.addDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ede=Integer.parseInt(String.valueOf(initput.getText()));
                ede+=1;
                initput.setText(ede+"");
                initput.setSelection(initput.getText().length());
            }
        });
        dialog.findViewById(R.id.lessenDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ede=Integer.parseInt(String.valueOf(initput.getText()));

                if (ede>0){
                    ede-=1;
                    initput.setText(ede+"");
                    initput.setSelection(initput.getText().length());
                }
            }
        });
        dialog.findViewById(R.id.cancelDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.determineDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numS=String.valueOf(initput.getText());
                if (numS==null||"".equals(numS)){
                    Toast.makeText(context, "此处不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    int number=Integer.valueOf(numS);
                    cartList.get(position).setNumber(number);
                    List<String> list = new ArrayList<String>();
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("goods_id", s);
                    params.put("number", number + "");
                    list.add(gson.toJson(params));
                    //修改
                    modifyCart(list.toString());
                    dialog.dismiss();
                }
            }
        });
    }
    private void modifyCart(String goods_id) {
        SubscriberOnNextListener modifyCart = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                totalPrice();
            }
        };
        setProgressSubscriber(modifyCart);
        builder.clear();
        builder.addParameter("goods_json", goods_id);
        builder.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(context).modifyCart(progressSubscriber, builder.bulider());

    }

    //计算总价格
    private void totalPrice() {
        float price = 0;
        for (int i = 0; i < cartList.size(); i++) {
            if (map.get(i)) {
                CartListBean goodsBean = cartList.get(i);
                price += goodsBean.getNumber() * goodsBean.getGoods_price();
            }
        }
        Log.i("TAG", "price--------------" + price);
        //保留两位小数
        DecimalFormat df = new java.text.DecimalFormat("#.##");
        scTotalPrice.setText("￥" + df.format(price));
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        scRecycler.setLayoutManager(linearLayoutManager);
        scRecycler.setItemAnimator(new DefaultItemAnimator());

        userInfo = (UserInfoEntity) SPUtils.get(context, K.USERINFO, UserInfoEntity.class);
    }

    //删除购物车商品
    private void removeShoppingCartGoods(final String goods_id) {
        final SubscriberOnNextListener remove = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                getCartData();
                totalPrice();
                T.show(context, "操作成功", Toast.LENGTH_SHORT);
            }
        };

        android.support.v7.app.AlertDialog.Builder builder_ = new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("是否删除商品?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setProgressSubscriber(remove);
                        builder.clear();
                        builder.addParameter("goods_json", goods_id);
                        builder.addParameter("m_id", userInfo.getM_id());
                        HttpMethods.getInstance(context).deleteCart(progressSubscriber, builder.bulider());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder_.create().show();


    }

    private void cartToCollect(String goods_id) {
        SubscriberOnNextListener remove = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                getCartData();
                T.show(context, "操作成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(remove);
        builder.clear();
        builder.addParameter("goods_json", goods_id);
        builder.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(context).cartToCollect(progressSubscriber, builder.bulider());
    }

    @OnClick({R.id.sc_edit, R.id.delete, R.id.sc_message, R.id.cs_choose_all, R.id.add_collect, R.id.close_an_account})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sc_message:
                intent = new Intent(getContext(), MessageManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.sc_edit:
                if (scEdit.getText().toString().equals("编辑")) {
                    scEdit.setText("完成");

                    scStatus1.setVisibility(View.GONE);
                    scStatus2.setVisibility(View.VISIBLE);
                } else {
                    scEdit.setText("编辑");
                    scStatus1.setVisibility(View.VISIBLE);
                    scStatus2.setVisibility(View.GONE);
                }
                break;
            case R.id.close_an_account:
                //结算
                List<CartListBean> goodsBeanList = new ArrayList<>();
                for (int i = 0; i < cartList.size(); i++) {
                    if (map.get(i)) {
                        goodsBeanList.add(cartList.get(i));
                    }
                }
                if (goodsBeanList.size() == 0) {
                    T.show(context, "请选择商品", Toast.LENGTH_SHORT);
                    return;
                }
                intent = new Intent(context, AddOrderActivity.class);
                //intent.putExtra("delivery_price", delivery_price);
                intent.putParcelableArrayListExtra(K.DATA, (ArrayList<? extends Parcelable>) goodsBeanList);
                startActivity(intent);
                break;
            case R.id.add_collect:
                //收藏
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < cartList.size(); i++) {
                    if (map.get(i)) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("goods_id", cartList.get(i).getGoods_id());
                        list.add(gson.toJson(params));
                    }
                }
                if (list.size() > 0) {
                    cartToCollect(list.toString());
                } else {
                    T.show(context, "请选中商品", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.delete:
                list = new ArrayList<String>();
                //删除

                for (int i = 0; i < cartList.size(); i++) {
                    if (map.get(i)) {
                        //拼装格式，删除操作
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("goods_id", cartList.get(i).getGoods_id());
                        list.add(gson.toJson(params));
                    }
                }

                if (list.size() > 0) {
                    removeShoppingCartGoods(list.toString());
                    csChooseAll.setChecked(false);
                    totalPrice();
                } else {
                    T.show(context, "请选中商品", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.cs_choose_all:
                boolean isAllCheck = csChooseAll.isChecked();
                if (isAllCheck) {
                    for (int i = 0; i < cartList.size(); i++) {
                        map.put(i, true);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < cartList.size(); i++) {
                        map.put(i, false);
                    }
                    adapter.notifyDataSetChanged();
                }
                totalPrice();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCartData();
    }
}
