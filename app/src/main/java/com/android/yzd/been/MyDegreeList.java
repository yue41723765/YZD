package com.android.yzd.been;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 33181 on 2017/9/12.
 */

public class MyDegreeList {
    /**
     * degree : 等级
     * list : []
     */

    private String degree;
    private List<MyDegreeBean> list;

    public static MyDegreeList objectFromData(String str) {

        return new Gson().fromJson(str, MyDegreeList.class);
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<MyDegreeBean> getList() {
        return list;
    }

    public void setList(List<MyDegreeBean> list) {
        this.list = list;
    }
}
