package com.android.yzd.been;

import com.google.gson.Gson;

/**
 * Created by 33181 on 2017/9/4.
 */

public class WXErrorInfo {
    /**
     * errcode : 40029
     * errmsg : invalid code
     */

    private int errcode;
    private String errmsg;

    public static WXErrorInfo objectFromData(String str) {

        return new Gson().fromJson(str, WXErrorInfo.class);
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
