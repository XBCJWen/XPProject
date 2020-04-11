package com.jm.xpproject.config.change;

import android.Manifest;
import android.content.Context;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.ui.login.act.LoginAct;
import com.jm.xpproject.ui.main.act.MainAct;

/**
 *
 * 在更改头像后，需要更新本地头像：    SharedAccount.getInstance(getContext()).saveUserAvatar(avatar);
 * 在更改名称后，需要更改本地名称：    SharedAccount.getInstance(getContext()).saveUserName(nick);
 * 用于APP数据类型的一些配置信息
 *
 * @author jinXiong.Xie
 */
public class DataConfig {

    //  登录
    /**
     * 登录时，需要的全部权限
     */
    public static final String[] LOGIN_ALL_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION};
    /**
     * 登录的页面（必填）
     */
    public static final Class LOGIN_CLASS = LoginAct.class;
//    public static Class LOGIN_CLASS = LoginAct.class;//例如登录页
    /**
     * 是否跳过登录（为false则是：不自动登录，直接跳转至主页）
     */
    public static final boolean NOT_JUMP_OVER_LOGIN = true;
    /**
     * 是否显示引导页
     */
    public static final boolean LOGIN_SHOW_GUIDE_VIEW = true;

    /**
     * 是否显示启动页
     */
    public static final boolean LOGIN_SHOW_START_VIEW = true;

    /**
     * 是否显示广告页
     */
    public static final boolean LOGIN_SHOW_ADVERTISING_VIEW = true;


    // 自动检测更新
    /**
     * 是否具有自动检测更新功能
     */
    public static final boolean AUTO_CHECK_VERSION = true;

    /**
     * 检测更新延迟时间
     */
    public static final int CHECK_VERSION_DELAY_TIME = 180000;

    // 第三方帐号配置
    /**
     * Bugly的AppID
     */
    public static final String BUGLY_APP_ID = "c39d1ecf7d";
    /**
     * 微信AppID
     */
    public static final String WECHAT_APP_ID = "wxe1677ca8e653707d";
    /**
     * 微信AppSecret
     */
    public static final String WECHAT_APP_SECRET = "a67a4f190e03da5e761a4abe7a5c47d4";
    /**
     * QQ的AppID
     */
    public static final String QQ_APP_ID = "1106267585";
    /**
     * QQ的AppSecret
     */
    public static final String QQ_APP_SECRET = "eU0gmKeThgeRN63Y";
    /**
     * 新浪的AppID
     */
    public static final String SINA_APP_ID = "4233772940";
    /**
     * 新浪的AppSecret
     */
    public static final String SINA_APP_SECRET = "128ea34fadb3dd2db5eb47b70d80662d";
    /**
     * 新浪的redirect_url
     */
    public static final String SINA_REDIRECT_URL = "http://sns.whalecloud.com";
    /**
     * 新浪的redirect_url
     */
    public static final String UMENG_KEY = "59c61c0a04e20531f10000c8";


    // 其它配置
    /**
     * 密码最短长度
     */
    public static final int PSW_MIN_LENGTH = 6;

    /**
     * 跳转至主页(MainAct.class或Main2Act.class)
     */
    public static final void turnToMain(Context context) {
        IntentUtil.intentToActivity(context, MainAct.class);
    }

    /**
     * 跳转至登录页
     */
    public static final void turnToLogin(Context context) {
        IntentUtil.intentToActivity(context, LOGIN_CLASS);
    }

    /**
     * 是否保存用户信息到本地
     */
    public static final boolean SAVE_USER_INFO = true;

    /**
     * 设计图转换为 dp 尺寸的大小（宽度）
     */
    public static final int DESIGN_WIDTH_IN_PX = 360;

    /**
     * 是否为内置更新
     */
    public static final boolean BUILT_IN_UPDATE = true;

}
