package com.android.yzd.been;

import com.google.gson.Gson;

/**
 * Created by 33181 on 2017/9/12.
 */

public class MyDegreeBean {
    /**
     * symbol : 1
     * description : 购买商品获得120经验
     * exp_num : 120
     * create_time : 1458989878
     */

    private String symbol;
    private String description;
    private String exp_num;
    private String create_time;

    public static MyDegreeBean objectFromData(String str) {

        return new Gson().fromJson(str, MyDegreeBean.class);
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

    public String getExp_num() {
        return exp_num;
    }

    public void setExp_num(String exp_num) {
        this.exp_num = exp_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
