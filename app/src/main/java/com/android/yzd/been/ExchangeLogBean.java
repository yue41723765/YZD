package com.android.yzd.been;

import com.google.gson.Gson;

/**
 * Created by 33181 on 2017/9/7.
 */

public class ExchangeLogBean {
    /**
     * cou_value : 优惠券面值
     * num : 兑换数量
     * use_integral : 使用的积分数
     * create_time : 兑换时间
     */

    private String cou_value;
    private String num;
    private String use_integral;
    private String create_time;

    public static ExchangeLogBean objectFromData(String str) {

        return new Gson().fromJson(str, ExchangeLogBean.class);
    }

    public String getCou_value() {
        return cou_value;
    }

    public void setCou_value(String cou_value) {
        this.cou_value = cou_value;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUse_integral() {
        return use_integral;
    }

    public void setUse_integral(String use_integral) {
        this.use_integral = use_integral;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
