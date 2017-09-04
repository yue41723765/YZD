package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class UserRegAgr implements Parcelable {


    /**
     * title : 用户端-注册协议
     * content : <p>用户端注册协议</p>
     * create_time : 1477226762
     */

    private String title;
    private String content;
    private String create_time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.create_time);
    }

    public UserRegAgr() {
    }

    protected UserRegAgr(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.create_time = in.readString();
    }

    public static final Creator<UserRegAgr> CREATOR = new Creator<UserRegAgr>() {
        @Override
        public UserRegAgr createFromParcel(Parcel source) {
            return new UserRegAgr(source);
        }

        @Override
        public UserRegAgr[] newArray(int size) {
            return new UserRegAgr[size];
        }
    };
}
