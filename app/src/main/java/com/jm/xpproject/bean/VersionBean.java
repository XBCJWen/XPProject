package com.jm.xpproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 版本
 *
 * @author jinXiong.Xie
 */

public class VersionBean implements Parcelable {

    private String createTime;
    private String name;
    private String updateTime;
    private int id;
    private int state;
    private int type;
    private int version;
    private String url;
    private String desc;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeString(this.name);
        dest.writeString(this.updateTime);
        dest.writeInt(this.id);
        dest.writeInt(this.state);
        dest.writeInt(this.type);
        dest.writeInt(this.version);
        dest.writeString(this.url);
        dest.writeString(this.desc);
    }

    public VersionBean() {
    }

    protected VersionBean(Parcel in) {
        this.createTime = in.readString();
        this.name = in.readString();
        this.updateTime = in.readString();
        this.id = in.readInt();
        this.state = in.readInt();
        this.type = in.readInt();
        this.version = in.readInt();
        this.url = in.readString();
        this.desc = in.readString();
    }

    public static final Creator<VersionBean> CREATOR = new Creator<VersionBean>() {
        @Override
        public VersionBean createFromParcel(Parcel source) {
            return new VersionBean(source);
        }

        @Override
        public VersionBean[] newArray(int size) {
            return new VersionBean[size];
        }
    };
}
