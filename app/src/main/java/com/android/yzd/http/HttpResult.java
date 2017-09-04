package com.android.yzd.http;

/**
 * Created by Administrator on 2016/8/29.
 */

public class HttpResult<T> {


    private String flag;
    private String message;
    private T data;


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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "flag='" + flag + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
