package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class MessageEntity implements Parcelable {


    /**
     * title : 标题
     * order_id : 订单id
     * order_sn : 订单编号
     * order_create_time : 下单时间
     * content : 内容
     * create_time : 创建时间
     * status : 0未读 1 已读
     */

    private String title;
    private String order_id;
    private String order_sn;
    private String order_create_time;
    private String content;
    private String create_time;
    private String status;
    private String order_time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_create_time() {
        return order_create_time;
    }

    public void setOrder_create_time(String order_create_time) {
        this.order_create_time = order_create_time;
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

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.order_id);
        dest.writeString(this.order_sn);
        dest.writeString(this.order_create_time);
        dest.writeString(this.content);
        dest.writeString(this.create_time);
        dest.writeString(this.status);
        dest.writeString(this.order_time);
    }

    public MessageEntity() {
    }

    protected MessageEntity(Parcel in) {
        this.title = in.readString();
        this.order_id = in.readString();
        this.order_sn = in.readString();
        this.order_create_time = in.readString();
        this.content = in.readString();
        this.create_time = in.readString();
        this.status = in.readString();
        this.order_time = in.readString();
    }

    public static final Creator<MessageEntity> CREATOR = new Creator<MessageEntity>() {
        @Override
        public MessageEntity createFromParcel(Parcel source) {
            return new MessageEntity(source);
        }

        @Override
        public MessageEntity[] newArray(int size) {
            return new MessageEntity[size];
        }
    };
}
