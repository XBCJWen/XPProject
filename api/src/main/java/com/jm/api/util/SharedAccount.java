package com.jm.api.util;

import android.content.Context;

import com.jm.core.common.tools.base.SharedPreferencesTool;

import java.lang.ref.WeakReference;

/**
 * 存储用户信息
 *
 * @author jinXiong.Xie
 */
public class SharedAccount {

    private static SharedPreferencesTool share;
    private static WeakReference<Context> wr;

    private SharedAccount(Context context) {
        wr = new WeakReference<>(context);
        share = new SharedPreferencesTool(wr.get(), "account");

    }

    private static SharedAccount instance = null;
    /**
     * 手机号
     */
    private final String MOBILE = "mobile";
    /**
     * 是否为第一次打开
     */
    private final String IS_FIRST = "isFirst";
    /**
     * 用户信息
     */
    private final String USER_INFO = "userInfo";
    /**
     * 用户头像
     */
    private final String USER_AVATAR = "userAvatar";
    /**
     * 用户名称
     */
    private final String USER_NAME = "userName";



    public static SharedAccount getInstance(Context context) {
        if (instance == null || wr.get() == null || share == null) {
            instance = new SharedAccount(context);
        }
        return instance;
    }

    public void saveMobileLogin(String mobile) {
        share.setParam(MOBILE, mobile);
    }

    public void alterMobile(String mobile) {
        share.setParam(MOBILE, mobile);
    }

    public void saveUserInfo(String userInfo) {
        share.setParam(USER_INFO, userInfo);
    }

    public void save(boolean isFirst) {
        share.setParam(IS_FIRST, isFirst);
    }

    public void saveAvatarAndName(String avatar, String name) {
        share.setParam(USER_AVATAR, avatar);
        share.setParam(USER_NAME, name);
    }

    public void saveUserName(String name) {
        share.setParam(USER_NAME, name);
    }

    public void saveUserAvatar(String avatar) {
        share.setParam(USER_AVATAR, avatar);
    }

    public void delete() {
        share.clear(MOBILE);
//        share.clear(IS_FIRST);
        share.clear(USER_INFO);
        share.clear(USER_AVATAR);
        share.clear(USER_NAME);
    }


    public String getMobile() {
        return (String) share.getParam(MOBILE, "");
    }

    public boolean isFirst() {
        return (boolean) share.getParam(IS_FIRST, true);
    }

    public String getUserInfo() {
        return (String) share.getParam(USER_INFO, "");
    }

    public String getUserName() {
        return (String) share.getParam(USER_NAME, "");
    }

    public String getUserAvatar() {
        return (String) share.getParam(USER_AVATAR, "");
    }
}
