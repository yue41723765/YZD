package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class ClassifyToolsEntity {

    /**
     * fir_type_id : 1
     * type_name : 水
     * sec_list : [{"sec_type_id":"6","type_name":"PPR水管","type_pic":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"sec_type_id":"7","type_name":"PVC排水管","type_pic":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"},{"sec_type_id":"8","type_name":"PB管即管件","type_pic":"http://yzd.txunda.com/Uploads/Goods/2016-12-06/5846b39960f5d.png"}]
     */

    private String fir_type_id;
    private String type_name;
    private List<SecListBean> sec_list;

    public String getFir_type_id() {
        return fir_type_id;
    }

    public void setFir_type_id(String fir_type_id) {
        this.fir_type_id = fir_type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<SecListBean> getSec_list() {
        return sec_list;
    }

    public void setSec_list(List<SecListBean> sec_list) {
        this.sec_list = sec_list;
    }

}
