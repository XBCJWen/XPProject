package com.jm.xpproject.http.api;

/**
 * 用户模块链接
 *
 * @author jinXiong.Xie
 */

public class UserCloudApi extends BaseCloudApi {


    // 登录注册
    /**
     * 欢迎页接口
     */
    public static String HYY = getHttpUrl("configure/get?name=hyy");
    /**
     * 引导页接口
     */
    public static String ADS_GUIDE = getHttpUrl("ads/guide");
    /**
     * 注册协议接口
     */
    public static String ZCXY = getHttpUrl("configure/get?name=zcxy");
    /**
     * 用户登录
     */
    public static String USER_LOGIN = getHttpUrl("user/login");
    /**
     * 授权登录
     */
    public static String USER_AUTHORIZE = getHttpUrl("user/authorize");
    /**
     * 获取验证码接口
     */
    public static String USER_CODE = getHttpUrl("user/code");
    /**
     * 注册接口
     */
    public static String USER_REGISTER = getHttpUrl("user/register");
    /**
     * 找回密码
     */
    public static String USER_FIND = getHttpUrl("user/find");
    /**
     * 修改密码
     */
    public static String userUpdatePwd = getHttpUrl("user/updatePwd");
    /**
     * 用户退出
     */
    public static String USER_LOGOUT = getHttpUrl("user/logout");

    /**
     * 意见箱接口
     */
    public static String PROBLEM_SAVE = getHttpUrl("problem/save");
    /**
     * 关于我们接口
     */
    public static String CONFIGURE_GET = getHttpUrl("configure/get");
    /**
     * 获取最新版本接口
     */
    public static String APP_VERSION_GET = getHttpUrl("appversion/get");
    /**
     * 修改绑定的手机号码
     */
    public static String USER_UPDATE_MOBILE = getHttpUrl("user/updateMobile");
    /**
     * 个人资料
     */
    public static String USER_HOME = getHttpUrl("user/home");
}
