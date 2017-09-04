package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class CartListBean implements Parcelable {
    /**
     * goods_name : 商品id
     * goods_logo : 商品logo
     * goods_price : 商品价格
     * number : 购物车数量
     */

    private String goods_name;
    private String goods_logo;
    private float goods_price;
    private int number;
    private String goods_id;
    private String cart_id;
    private String models_type;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public float getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(float goods_price) {
        this.goods_price = goods_price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getModels_type() {
        return models_type;
    }

    public void setModels_type(String models_type) {
        this.models_type = models_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goods_name);
        dest.writeString(this.goods_logo);
        dest.writeFloat(this.goods_price);
        dest.writeInt(this.number);
        dest.writeString(this.goods_id);
        dest.writeString(this.cart_id);
        dest.writeString(this.models_type);
    }

    public CartListBean() {
    }

    protected CartListBean(Parcel in) {
        this.goods_name = in.readString();
        this.goods_logo = in.readString();
        this.goods_price = in.readFloat();
        this.number = in.readInt();
        this.goods_id = in.readString();
        this.cart_id = in.readString();
        this.models_type=in.readString();
    }

    public static final Parcelable.Creator<CartListBean> CREATOR = new Parcelable.Creator<CartListBean>() {
        @Override
        public CartListBean createFromParcel(Parcel source) {
            return new CartListBean(source);
        }

        @Override
        public CartListBean[] newArray(int size) {
            return new CartListBean[size];
        }
    };
}
