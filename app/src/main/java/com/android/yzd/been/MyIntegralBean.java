package com.android.yzd.been;

import com.google.gson.Gson;

/**
 * Created by 33181 on 2017/9/12.
 */

public class MyIntegralBean {

    /**
     * symbol : 0
     * description : 兑换10元优惠券1张
     * integral_num : 10000
     * create_time : 1504689353
     */

    private String symbol;
    private String description;
    private String integral_num;
    private String create_time;

    public static MyIntegralBean objectFromData(String str) {

        return new Gson().fromJson(str, MyIntegralBean.class);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIntegral_num() {
        return integral_num;
    }

    public void setIntegral_num(String integral_num) {
        this.integral_num = integral_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
