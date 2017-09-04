package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class AddressEntity implements Parcelable {

    /**
     * address_id : 地址id
     * consignee : 收货人
     * mobile : 手机号
     * address : 详细地址
     * is_default : 是否是默认地址，0不是 1是
     */

    private String address_id;
    private String consignee;
    private String mobile;
    private String address;
    private int is_default;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address_id);
        dest.writeString(this.consignee);
        dest.writeString(this.mobile);
        dest.writeString(this.address);
        dest.writeInt(this.is_default);
    }

    public AddressEntity() {
    }

    protected AddressEntity(Parcel in) {
        this.address_id = in.readString();
        this.consignee = in.readString();
        this.mobile = in.readString();
        this.address = in.readString();
        this.is_default = in.readInt();
    }

    public static final Creator<AddressEntity> CREATOR = new Creator<AddressEntity>() {
        @Override
        public AddressEntity createFromParcel(Parcel source) {
            return new AddressEntity(source);
        }

        @Override
        public AddressEntity[] newArray(int size) {
            return new AddressEntity[size];
        }
    };
}
