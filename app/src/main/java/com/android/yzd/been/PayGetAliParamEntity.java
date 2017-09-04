package com.android.yzd.been;

/**
 * Created by 33181 on 2017/7/22.
 */

public class PayGetAliParamEntity {

    /**
     * flag : success
     * message : 请求成功
     * data : {"pay_string":"调起支付宝支付参数"}
     */

    private String flag;
    private String message;
    private DataBean data;

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

    public static class DataBean {
        /**
         * pay_string : 调起支付宝支付参数
         */

        private String pay_string;

        public String getPay_string() {
            return pay_string;
        }

        public void setPay_string(String pay_string) {
            this.pay_string = pay_string;
        }
    }
}
