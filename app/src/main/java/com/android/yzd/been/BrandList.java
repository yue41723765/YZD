package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class BrandList implements Parcelable {
    String brand_id;
    String brand_name;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.brand_id);
        dest.writeString(this.brand_name);
    }

    public BrandList() {
    }

    protected BrandList(Parcel in) {
        this.brand_id = in.readString();
        this.brand_name = in.readString();
    }

    public static final Parcelable.Creator<BrandList> CREATOR = new Parcelable.Creator<BrandList>() {
        @Override
        public BrandList createFromParcel(Parcel source) {
            return new BrandList(source);
        }

        @Override
        public BrandList[] newArray(int size) {
            return new BrandList[size];
        }
    };

    @Override
    public String toString() {
        return "BrandList{" +
                "brand_id='" + brand_id + '\'' +
                ", brand_name='" + brand_name + '\'' +
                '}';
    }
}
