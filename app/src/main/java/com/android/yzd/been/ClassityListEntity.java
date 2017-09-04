package com.android.yzd.been;

import java.util.List;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class ClassityListEntity {

    private List<ClassListEntity> goods_list;
    private List<BrandList> brand_list;
    private List<TypeListBean> type_list;

    public List<ClassListEntity> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<ClassListEntity> goods_list) {
        this.goods_list = goods_list;
    }

    public List<BrandList> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<BrandList> brand_list) {
        this.brand_list = brand_list;
    }

    public List<TypeListBean> getType_list() {
        return type_list;
    }

    public void setType_list(List<TypeListBean> type_list) {
        this.type_list = type_list;
    }
}
