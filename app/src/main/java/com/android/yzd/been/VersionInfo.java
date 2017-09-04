package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

public class VersionInfo implements Parcelable {

    String code;
    String uri;
    String message;
    String name;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.uri);
        dest.writeString(this.message);
        dest.writeString(this.name);
    }

    public VersionInfo() {
    }

    protected VersionInfo(Parcel in) {
        this.code = in.readString();
        this.uri = in.readString();
        this.message = in.readString();
        this.name = in.readString();
    }

    public static final Creator<VersionInfo> CREATOR = new Creator<VersionInfo>() {
        @Override
        public VersionInfo createFromParcel(Parcel source) {
            return new VersionInfo(source);
        }

        @Override
        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };
}
