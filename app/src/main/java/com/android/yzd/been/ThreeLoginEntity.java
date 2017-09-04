package com.android.yzd.been;

/**
 * Created by 33181 on 2017/9/1.
 */

public class ThreeLoginEntity {
    /*"flag": "success",
    "message": "登录成功",
    "data": {
        "m_id": "用户id",
        "account": "账号",//如果账号为空 跳转绑定手机号界面
        "easemob_account": "环信账号",
        "easemob_password": "环信密码",
        "head_pic": "头像",
        "nickname": "昵称",
        "balance": "余额",
        "not_read": "是否有未读消息，0没有 1有"
      "exchange_num": "已经兑换的次数",
         "integral": "积分",
        "degree": "等级"
    }*/
    private String flag;
    private String message;
    private DataBean data;
    public static class DataBean {

        private String  m_id;
        private String  account;
        private String  easemob_account;
        private String  easemob_password;
        private String  head_pic;
        private String  nickname;
        private String  balance;
        private String  not_read;
        private String  exchange_num;
        private String  integral;
        private String  degree;

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getEasemob_account() {
            return easemob_account;
        }

        public void setEasemob_account(String easemob_account) {
            this.easemob_account = easemob_account;
        }

        public String getEasemob_password() {
            return easemob_password;
        }

        public void setEasemob_password(String easemob_password) {
            this.easemob_password = easemob_password;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getNot_read() {
            return not_read;
        }

        public void setNot_read(String not_read) {
            this.not_read = not_read;
        }

        public String getExchange_num() {
            return exchange_num;
        }

        public void setExchange_num(String exchange_num) {
            this.exchange_num = exchange_num;
        }
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}
