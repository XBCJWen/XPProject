package com.jm.xpproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 个人信息
 *
 * @author jinXiong.Xie
 */

public class UserInfoBean implements Parcelable {

    private String no;
    private String signature;
    private int sex;
    private String dyImage;
    private String regionName;
    private String mobile;
    private int userId;
    private String nick;
    private String head;
    private double balance;
    private int regionId;
    private String createTime;
    private int id;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDyImage() {
        return dyImage;
    }

    public void setDyImage(String dyImage) {
        this.dyImage = dyImage;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.no);
        dest.writeString(this.signature);
        dest.writeInt(this.sex);
        dest.writeString(this.dyImage);
        dest.writeString(this.regionName);
        dest.writeString(this.mobile);
        dest.writeInt(this.userId);
        dest.writeString(this.nick);
        dest.writeString(this.head);
        dest.writeDouble(this.balance);
        dest.writeInt(this.regionId);
        dest.writeString(this.createTime);
        dest.writeInt(this.id);
    }

    protected UserInfoBean(Parcel in) {
        this.no = in.readString();
        this.signature = in.readString();
        this.sex = in.readInt();
        this.dyImage = in.readString();
        this.regionName = in.readString();
        this.mobile = in.readString();
        this.userId = in.readInt();
        this.nick = in.readString();
        this.head = in.readString();
        this.balance = in.readDouble();
        this.regionId = in.readInt();
        this.createTime = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

}
