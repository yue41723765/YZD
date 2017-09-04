package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class SystemMessageEntity implements Parcelable {

    String system_count;
    String system_title;
    String system_time;
    String notice_count;
    String notice_title;
    String notice_time;
    String service_account;
    String service_logo;

    public String getSystem_count() {
        return system_count;
    }

    public void setSystem_count(String system_count) {
        this.system_count = system_count;
    }

    public String getSystem_title() {
        return system_title;
    }

    public void setSystem_title(String system_title) {
        this.system_title = system_title;
    }

    public String getSystem_time() {
        return system_time;
    }

    public void setSystem_time(String system_time) {
        this.system_time = system_time;
    }

    public String getNotice_count() {
        return notice_count;
    }

    public void setNotice_count(String notice_count) {
        this.notice_count = notice_count;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }

    public String getService_account() {
        return service_account;
    }

    public void setService_account(String service_account) {
        this.service_account = service_account;
    }

    public String getService_logo() {
        return service_logo;
    }

    public void setService_logo(String service_logo) {
        this.service_logo = service_logo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.system_count);
        dest.writeString(this.system_title);
        dest.writeString(this.system_time);
        dest.writeString(this.notice_count);
        dest.writeString(this.notice_title);
        dest.writeString(this.notice_time);
        dest.writeString(this.service_account);
        dest.writeString(this.service_logo);
    }

    public SystemMessageEntity() {
    }

    protected SystemMessageEntity(Parcel in) {
        this.system_count = in.readString();
        this.system_title = in.readString();
        this.system_time = in.readString();
        this.notice_count = in.readString();
        this.notice_title = in.readString();
        this.notice_time = in.readString();
        this.service_account = in.readString();
        this.service_logo = in.readString();
    }

    public static final Creator<SystemMessageEntity> CREATOR = new Creator<SystemMessageEntity>() {
        @Override
        public SystemMessageEntity createFromParcel(Parcel source) {
            return new SystemMessageEntity(source);
        }

        @Override
        public SystemMessageEntity[] newArray(int size) {
            return new SystemMessageEntity[size];
        }
    };
}
