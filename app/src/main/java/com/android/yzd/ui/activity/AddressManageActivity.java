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
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.AddressEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.TitleBarView;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class AddressManageActivity extends BaseActivity {

    @BindView(R.id.tools_title)
    TitleBarView toolsTitle;
    @BindView(R.id.tools_bottom)
    Button toolsBottom;
    @BindView(R.id.am_recycler)
    RecyclerView amRecycler;

    CommonAdapter adapter;
    UserInfoEntity userInfo;

    List<AddressEntity> addressList = new ArrayList<>();
    Map<Integer, Boolean> isCheck = new HashMap<>();

    int status = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_address_manage;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        init();
        try {
            status = getIntent().getIntExtra(K.STATUS, 0);
        } catch (Exception e) {
        }
    }

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        amRecycler.setLayoutManager(linearLayoutManager);
        Drawable drawable = getResources().getDrawable(R.color.background);
        amRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, DensityUtils.dp2px(this, 10)));

        adapter = new CommonAdapter<AddressEntity>(this, R.layout.item_address, addressList) {
            @Override
            protected void convert(ViewHolder holder, final AddressEntity entity, final int position) {
                CheckBox checkBox = holder.getView(R.id.is_default);
                if (entity.getIs_default() == 1) {
                    checkBox.setChecked(true);
                }
                if (isCheck.get(position) == null)
                    isCheck.put(position, false);
                checkBox.setChecked(isCheck.get(position));
                holder.setText(R.id.address_name, entity.getConsignee());
                holder.setText(R.id.address_tel, entity.getMobile());
                holder.setText(R.id.address_details, entity.getAddress());

                holder.getView(R.id.address_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(AddressManageActivity.this, EditAddressActivity.class);
                        intent.putExtra(K.STATUS, 2);
                        intent.putExtra(K.DATA, entity);
                        startActivity(intent);
                    }
                });
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isCheck.clear();
                        for (int i = 0; i < addressList.size(); i++) {
                            isCheck.put(i, false);
                        }
                        isCheck.put(position, true);
                        setDefaultAddress(entity.getAddress_id());
                        notifyDataSetChanged();
                    }
                });
                holder.setOnClickListener(R.id.address_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(AddressManageActivity.this)
                                .setTitle("提示")
                                .setMessage("是否删除收货地址?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delAddress(entity.getAddress_id());
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.create().show();
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (status == 111) {
                    intent = new Intent();
                    intent.putExtra(K.DATA, addressList.get(position));
                    setResult(100, intent);
                    finish();
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        amRecycler.setAdapter(adapter);
    }

    private void setDefaultAddress(String address_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(AddressManageActivity.this, "设置默认收货地址成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("address_id", address_id);
        params.put("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).setDefaultAddress(progressSubscriber, params);
    }

    //删除地址
    private void delAddress(String address_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                finish();
                T.show(AddressManageActivity.this, "删除收货地址成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("address_id", address_id);
        HttpMethods.getInstance(this).deleteAddress(progressSubscriber, params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    private void getAddressList() {
        addressList.clear();
        adapter.notifyDataSetChanged();
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                addressList.clear();
                List<AddressEntity> list = gson.fromJson(gson.toJson(o), new TypeToken<List<AddressEntity>>() {
                }.getType());
                addressList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        };
        params.clear();
        params.put("m_id", userInfo.getM_id());
        setProgressSubscriber(onNextListener);
        HttpMethods.getInstance(this).addressList(progressSubscriber, params);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tools_bottom:
                intent = new Intent(this, EditAddressActivity.class);
                intent.putExtra(K.STATUS, 1);
                startActivity(intent);
                break;
        }
    }
}
