package com.android.yzd.been;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/29.
 * 登陆返回数据
 */

public class UserInfoEntity implements Serializable {

    /**
     * m_id : 2
     * account : 18166485393
     * easemob_account : 201612062139446
     * easemob_password : 1481031593
     * head_pic : http://yzd.txunda.com/Uploads/Member/2016-12-07/58481542c0b96.png
     * nickname : 测试
     * status : 1
     * not_read : 0
     * exchange_num : 已经兑换的次数
     * integral : 积分
     * degree : 等级
     */


    private String m_id;
    private String account;
    private String easemob_account;
    private String easemob_password;
    private String head_pic;
    private String nickname;
    private String status;
    private String balance;
    private String not_read;
    private String exchange_num;
    private String integral;
    private String degree;

    public static UserInfoEntity objectFromData(String str) {

        return new Gson().fromJson(str, UserInfoEntity.class);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
