package com.android.yzd.been;

/**
 * Created by 33181 on 2017/7/22.
 */

public class PayAddOrderEntity {


    /**
     * flag : success
     * message : 下单成功
     * data : {"order_id":"56"}
     */

    private String flag;
    private String message;
    private DataBean data;

    private String delivery_price;
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public static class DataBean {
        /**
         * order_id : 56
         */

        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
