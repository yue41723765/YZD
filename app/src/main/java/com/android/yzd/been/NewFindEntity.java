package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 33181 on 2017/9/9.
 */

public class NewFindEntity {
    /**
     * not_read : 0
     * coupon_list : [{"cou_id":"优惠券id","cou_value":"优惠券面值","need_integral":"兑换需要积分数","expired_day":"有效期（单位天，APP显示的时候直接显示 有效期xx天即可）"}]
     */

    private String not_read;
    private List<NewIntegralListBean> coupon_list;

    public static NewFindEntity objectFromData(String str) {

        return new Gson().fromJson(str, NewFindEntity.class);
    }

    public String getNot_read() {
        return not_read;
    }

    public void setNot_read(String not_read) {
        this.not_read = not_read;
    }

    public List<NewIntegralListBean> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(List<NewIntegralListBean> coupon_list) {
        this.coupon_list = coupon_list;
    }
}
