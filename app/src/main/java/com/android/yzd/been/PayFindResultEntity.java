package com.android.yzd.been;

/**
 * Created by 33181 on 2017/7/22.
 */

public class PayFindResultEntity {
    /**
     * flag : success
     * message : 查询成功
     * data : {"status":"0"}
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
         * status : 0
         */

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
