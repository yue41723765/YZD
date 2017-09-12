package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by 33181 on 2017/9/11.
 */

public class NewIntegralListBean implements Parcelable {
    /**
     * cou_id : 优惠券id
     * cou_value : 优惠券面值
     * need_integral : 兑换需要积分数
     * expired_day : 有效期（单位天，APP显示的时候直接显示 有效期xx天即可）
     */

    private String cou_id;
    private String cou_value;
    private String need_integral;
    private String expired_day;

    protected NewIntegralListBean(Parcel in) {
        cou_id = in.readString();
        cou_value = in.readString();
        need_integral = in.readString();
        expired_day = in.readString();
    }


    public static final Creator<NewIntegralListBean> CREATOR = new Creator<NewIntegralListBean>() {
        @Override
        public NewIntegralListBean createFromParcel(Parcel in) {
            return new NewIntegralListBean(in);
        }

        @Override
        public NewIntegralListBean[] newArray(int size) {
            return new NewIntegralListBean[size];
        }
    };

    public String getCou_id() {
        return cou_id;
    }

    public void setCou_id(String cou_id) {
        this.cou_id = cou_id;
    }

    public String getCou_value() {
        return cou_value;
    }

    public void setCou_value(String cou_value) {
        this.cou_value = cou_value;
    }

    public String getNeed_integral() {
        return need_integral;
    }

    public void setNeed_integral(String need_integral) {
        this.need_integral = need_integral;
    }

    public String getExpired_day() {
        return expired_day;
    }

    public void setExpired_day(String expired_day) {
        this.expired_day = expired_day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cou_id);
        dest.writeString(this.cou_value);
        dest.writeString(this.need_integral);
        dest.writeString(this.expired_day);
    }
}
