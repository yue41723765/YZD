package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class DetailsEntity {

    /**
     * goods_id : 商品id
     * goods_name : 商品名称
     * goods_picture : [{"pic":"轮播图路径"}]
     * goods_price : 价格
     * sales : 销量
     * is_collect : 是否已经收藏，0未收藏，1已收藏
     * cart_number : 购物车商品数量
     * service_account : 客服账号
     * service_logo : 客服头像
     */

    private String goods_id;
    private String goods_name;
    private String goods_price;
    private String sales;
    private String is_collect;
    private String cart_number;
    private String service_account;
    private String service_logo;
    private List<GoodsPictureBean> goods_picture;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
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

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getCart_number() {
        return cart_number;
    }

    public void setCart_number(String cart_number) {
        this.cart_number = cart_number;
    }

    public String getService_account() {
        return service_account;
    }

    public void setService_account(String service_account) {
        this.service_account = service_account;
    }

    public String getService_logo() {
        return service_logo;
    }

    public void setService_logo(String service_logo) {
        this.service_logo = service_logo;
    }

    public List<GoodsPictureBean> getGoods_picture() {
        return goods_picture;
    }

    public void setGoods_picture(List<GoodsPictureBean> goods_picture) {
        this.goods_picture = goods_picture;
    }

}
