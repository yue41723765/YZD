package com.android.yzd.been;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class MyInfoEntity {


    /**
     * m_id : 2
     * account : 18166485393
     * nickname : 18166485393
     * sex : 0
     * head_pic : http://yzd.txunda.com/Uploads/Member/default.png
     */

    private String m_id;
    private String account;
    private String nickname;
    private String sex;
    private String head_pic;
    private String is_set_password;
    private String invite_code;
    private String parent_id;
    private String parent_invite_code;

    public String getIs_set_password() {
        return is_set_password;
    }

    public void setIs_set_password(String is_set_password) {
        this.is_set_password = is_set_password;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getParent_invite_code() {
        return parent_invite_code;
    }

    public void setParent_invite_code(String parent_invite_code) {
        this.parent_invite_code = parent_invite_code;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }
}
