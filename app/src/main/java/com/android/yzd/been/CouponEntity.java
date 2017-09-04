package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class CouponEntity implements Parcelable {


    /**
     * m_c_id : 优惠券id
     * title : 优惠券名称
     * value : 面值
     * condition : 使用条件
     * end_time : 有效期
     * is_use : 是否已经使用 0未使用 1已使用
     * create_time : 领取时间
     * account : 限定使用人
     */

    private String m_c_id;
    private String title;
    private String value;
    private String condition;
    private String end_time;
    private String is_use;
    private String create_time;
    private String account;

    public String getM_c_id() {
        return m_c_id;
    }

    public void setM_c_id(String m_c_id) {
        this.m_c_id = m_c_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIs_use() {
        return is_use;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.m_c_id);
        dest.writeString(this.title);
        dest.writeString(this.value);
        dest.writeString(this.condition);
        dest.writeString(this.end_time);
        dest.writeString(this.is_use);
        dest.writeString(this.create_time);
        dest.writeString(this.account);
    }

    public CouponEntity() {
    }

    protected CouponEntity(Parcel in) {
        this.m_c_id = in.readString();
        this.title = in.readString();
        this.value = in.readString();
        this.condition = in.readString();
        this.end_time = in.readString();
        this.is_use = in.readString();
        this.create_time = in.readString();
        this.account = in.readString();
    }

    public static final Parcelable.Creator<CouponEntity> CREATOR = new Parcelable.Creator<CouponEntity>() {
        @Override
        public CouponEntity createFromParcel(Parcel source) {
            return new CouponEntity(source);
        }

        @Override
        public CouponEntity[] newArray(int size) {
            return new CouponEntity[size];
        }
    };
}
