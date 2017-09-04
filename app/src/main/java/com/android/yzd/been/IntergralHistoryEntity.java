package com.android.yzd.been;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class IntergralHistoryEntity {

    /**
     * goods_name : 兑换商品名称
     * use_integral : 使用积分
     * status : 状态 0待处理，1兑换成功
     */

    private String goods_name;
    private String use_integral;
    private int status;
    private String create_time;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUse_integral() {
        return use_integral;
    }

    public void setUse_integral(String use_integral) {
        this.use_integral = use_integral;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
