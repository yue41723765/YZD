package com.android.yzd.been;

import java.util.List;

/**
 * Created by 33181 on 2017/9/12.
 */

public class MyExchangeList {
    /**
     * exchange_num : 兑换次数
     * list : [{"cou_value":"兑换优惠券面值","num":"兑换优惠券的张数","create_time":"兑换时间"}]
     */
    private String exchange_num;
    private List<MyExchangeBean> list;

    public String getExchange_num() {
        return exchange_num;
    }

    public void setExchange_num(String exchange_num) {
        this.exchange_num = exchange_num;
    }

    public List<MyExchangeBean> getList() {
        return list;
    }

    public void setList(List<MyExchangeBean> list) {
        this.list = list;
    }
}
