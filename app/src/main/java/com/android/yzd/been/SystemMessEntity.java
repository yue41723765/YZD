package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class SystemMessEntity implements Parcelable {


    /**
     * sys_mess_id : 消息id
     * title : 标题
     * content : 内容
     * create_time : 时间
     * status : 状态 0未读 1已读
     */

    private String sys_mess_id;
    private String title;
    private String content;
    private String create_time;
    private String status;

    public String getSys_mess_id() {
        return sys_mess_id;
    }

    public void setSys_mess_id(String sys_mess_id) {
        this.sys_mess_id = sys_mess_id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sys_mess_id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.create_time);
        dest.writeString(this.status);
    }

    public SystemMessEntity() {
    }

    protected SystemMessEntity(Parcel in) {
        this.sys_mess_id = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.create_time = in.readString();
        this.status = in.readString();
    }

    public static final Creator<SystemMessEntity> CREATOR = new Creator<SystemMessEntity>() {
        @Override
        public SystemMessEntity createFromParcel(Parcel source) {
            return new SystemMessEntity(source);
        }

        @Override
        public SystemMessEntity[] newArray(int size) {
            return new SystemMessEntity[size];
        }
    };
}
