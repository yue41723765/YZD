package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class IntegralListBean implements Parcelable {
    /**
     * i_g_id : 3
     * goods_name : 简约时尚衣架
     * goods_logo : http://yzd.txunda.com/Uploads/IntegralGoods/2016-12-07/5847f83941e3c.png
     * goods_brief : 使用多种空间，无需打孔自由伸缩，可360度旋转，加粗碳钢30KG承重 衣服宝宝五件随心挂
     * need_integral : 10
     */

    private String i_g_id;
    private String goods_name;
    private String goods_logo;
    private String goods_brief;
    private String need_integral;

    public String getI_g_id() {
        return i_g_id;
    }

    public void setI_g_id(String i_g_id) {
        this.i_g_id = i_g_id;
    }

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

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getNeed_integral() {
        return need_integral;
    }

    public void setNeed_integral(String need_integral) {
        this.need_integral = need_integral;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.i_g_id);
        dest.writeString(this.goods_name);
        dest.writeString(this.goods_logo);
        dest.writeString(this.goods_brief);
        dest.writeString(this.need_integral);
    }

    public IntegralListBean() {
    }

    protected IntegralListBean(Parcel in) {
        this.i_g_id = in.readString();
        this.goods_name = in.readString();
        this.goods_logo = in.readString();
        this.goods_brief = in.readString();
        this.need_integral = in.readString();
    }

    public static final Parcelable.Creator<IntegralListBean> CREATOR = new Parcelable.Creator<IntegralListBean>() {
        @Override
        public IntegralListBean createFromParcel(Parcel source) {
            return new IntegralListBean(source);
        }

        @Override
        public IntegralListBean[] newArray(int size) {
            return new IntegralListBean[size];
        }
    };
}
