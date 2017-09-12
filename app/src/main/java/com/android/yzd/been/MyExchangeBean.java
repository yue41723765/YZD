package com.android.yzd.been;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 33181 on 2017/9/9.
 */

public class MyExchangeBean {


        /**
         * cou_value : 兑换优惠券面值
         * num : 兑换优惠券的张数
         * create_time : 兑换时间
         */

        private String cou_value;
        private String num;
        private String create_time;



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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
}
