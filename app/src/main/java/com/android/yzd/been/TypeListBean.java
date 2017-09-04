package com.android.yzd.been;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class TypeListBean {
    /**
     * sec_type_id : 6
     * type_name : PPR水管
     */

    private String sec_type_id;
    private String type_name;

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

    @Override
    public String toString() {
        return "TypeListBean{" +
                "sec_type_id='" + sec_type_id + '\'' +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
