package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/25 0025.
 */

public class OrderDetailsEntity {

    /**
     * order_id : 2
     * order_sn : 14826327672704
     * goods_list : [{"goods_id":"4","goods_name":"美涂士强力乳胶漆 墙","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_price":"10.00","number":"1"},{"goods_id":"3","goods_name":"美涂士强力乳胶漆 墙","goods_logo":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png","goods_price":"10.00","number":"1"}]
     * goods_price : 20.00
     * delivery_price : 10.00
     * is_use_coupon : 0
     * coupon_value : 0.00
     * order_price : 30.00
     * name : tt
     * mobile : 18166485393
     * address : dfddd
     * remark :
     * create_time : 1482632767
     * status : 0
     */

    private String order_id;
    private String order_sn;
    private String goods_price;
    private String delivery_price;
    private String is_use_coupon;
    private String coupon_value;
    private String order_price;
    private String name;
    private String mobile;
    private String address;
    private String remark;
    private String create_time;
    private String status;
    private List<GoodsListBean> goods_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getIs_use_coupon() {
        return is_use_coupon;
    }

    public void setIs_use_coupon(String is_use_coupon) {
        this.is_use_coupon = is_use_coupon;
    }

    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_id : 4
         * goods_name : 美涂士强力乳胶漆 墙
         * goods_logo : http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png
         * goods_price : 10.00
         * number : 1
         */

        private String goods_id;
        private String goods_name;
        private String goods_logo;
        private String goods_price;
        private String number;

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

        public String getGoods_logo() {
            return goods_logo;
        }

        public void setGoods_logo(String goods_logo) {
            this.goods_logo = goods_logo;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
