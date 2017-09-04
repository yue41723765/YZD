package com.android.yzd.been;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 33181 on 2017/7/22.
 */

public class PayGetWxParamEntity {

    /**
     * flag : success
     * message : 请求成功
     * data : {"sign":"","appid":"w","nonce_str":","package":"S,"time_stamp":"15","prepay_id":"w","mch_id":"11"}
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
         * sign :
         * appid :
         * nonce_str :
         * package :
         * time_stamp :
         * prepay_id :
         * mch_id :
         */

        private String sign;
        private String appid;
        private String nonce_str;
        @SerializedName("package")
        private String packageX;
        private String time_stamp;
        private String prepay_id;
        private String mch_id;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getTime_stamp() {
            return time_stamp;
        }

        public void setTime_stamp(String time_stamp) {
            this.time_stamp = time_stamp;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }
    }
}
