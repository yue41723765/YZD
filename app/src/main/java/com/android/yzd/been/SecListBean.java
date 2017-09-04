package com.android.yzd.been;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class SecListBean {
    /**
     * sec_type_id : 6
     * type_name : PPR水管
     * type_pic : http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png
     */

    private String sec_type_id;
    private String type_name;
    private String type_pic;

    public String getSec_type_id() {
        return sec_type_id;
    }

    public void setSec_type_id(String sec_type_id) {
        this.sec_type_id = sec_type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_pic() {
        return type_pic;
    }

    public void setType_pic(String type_pic) {
        this.type_pic = type_pic;
    }


    @Override
    public String toString() {
        return "SecListBean{" +
                "sec_type_id='" + sec_type_id + '\'' +
                ", type_name='" + type_name + '\'' +
                ", type_pic='" + type_pic + '\'' +
                '}';
    }
}
