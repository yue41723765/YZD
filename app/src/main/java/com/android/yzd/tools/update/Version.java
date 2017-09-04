package com.android.yzd.tools.update;

import android.text.TextUtils;

/**
 * 版本控制
 */
public class Version {

    private String code;
    private String uri;
    private String message = "重大更新";
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMessage() {
        if (TextUtils.isEmpty(message)) {
            message = "发现新版本,修补部分bug";

        }
        return message;
    }

    public void setMessage(String message) {


        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
