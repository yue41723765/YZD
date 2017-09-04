package com.android.yzd.been;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class GoodsInfoEntity {

    /**
     * goods_id : 10
     * goods_logo : http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png
     * goods_name : 美涂士强力乳胶漆 墙
     * goods_price : 10.00
     * sales : 0
     */

    private String goods_id;
    private String goods_logo;
    private String goods_name;
    private String goods_price;
    private String sales;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_logo() {
        return goods_logo;
    }

    public void setGoods_logo(String goods_logo) {
        this.goods_logo = goods_logo;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
