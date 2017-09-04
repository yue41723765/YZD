package com.android.yzd.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/25 0025.
 */

public class SetEntity implements Parcelable {

    /**
     * service_line : 客服热线
     * company_name : 公司名称
     * copyright : 版权
     */

    private String service_line;
    private String company_name;
    private String copyright;

    public String getService_line() {
        return service_line;
    }

    public void setService_line(String service_line) {
        this.service_line = service_line;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.service_line);
        dest.writeString(this.company_name);
        dest.writeString(this.copyright);
    }

    public SetEntity() {
    }

    protected SetEntity(Parcel in) {
        this.service_line = in.readString();
        this.company_name = in.readString();
        this.copyright = in.readString();
    }

    public static final Parcelable.Creator<SetEntity> CREATOR = new Parcelable.Creator<SetEntity>() {
        @Override
        public SetEntity createFromParcel(Parcel source) {
            return new SetEntity(source);
        }

        @Override
        public SetEntity[] newArray(int size) {
            return new SetEntity[size];
        }
    };


}
