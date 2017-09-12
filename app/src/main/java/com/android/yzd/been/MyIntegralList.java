package com.android.yzd.been;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 33181 on 2017/9/12.
 */

public class MyIntegralList {
    /**
     * integral : 90000
     * list : []
     */

    private String integral;
    private List<MyIntegralBean> list;

    public static MyIntegralList objectFromData(String str) {

        return new Gson().fromJson(str, MyIntegralList.class);
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public List<MyIntegralBean> getList() {
        return list;
    }

    public void setList(List<MyIntegralBean> list) {
        this.list = list;
    }
}
