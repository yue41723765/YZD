package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/25 0025.
 */

public class QuestionEntity implements Parcelable {

    /**
     * title : 标题
     * content : 内容
     * create_time : 发布时间
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

    public QuestionEntity() {
    }

    protected QuestionEntity(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.create_time = in.readString();
    }

    public static final Parcelable.Creator<QuestionEntity> CREATOR = new Parcelable.Creator<QuestionEntity>() {
        @Override
        public QuestionEntity createFromParcel(Parcel source) {
            return new QuestionEntity(source);
        }

        @Override
        public QuestionEntity[] newArray(int size) {
            return new QuestionEntity[size];
        }
    };
}
