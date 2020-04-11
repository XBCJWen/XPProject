package com.jm.xpproject.http.tool;

import android.text.TextUtils;

import com.jm.xpproject.http.HttpTool;
import com.jm.core.common.http.okhttp.ResultListener;
import com.jm.xpproject.http.api.UserCloudApi;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块模块
 *
 * @author jinXiong.Xie
 */

public class UserHttpTool extends BaseHttpTool {

    private static UserHttpTool userHttpTool;

    private UserHttpTool(HttpTool httpTool) {
        super(httpTool);
    }

    public static UserHttpTool getInstance(HttpTool httpTool) {
        if (userHttpTool == null) {
            userHttpTool = new UserHttpTool(httpTool);
        }
        return userHttpTool;
    }

    /**
     * 获取启动页图片
     */
    public void httpGetWelcomeView(ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();


        httpTool.httpLoadNoNetTipPost(UserCloudApi.HYY, map, resultListener);

    }

    /**
     * 获取广告页图片
     */
    public void httpGetAdView(ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();

        httpTool.httpLoadNoNetTipPost(UserCloudApi.ADS_GUIDE, map, resultListener);

    }


    /**
     * 帐号密码式登录
     *
     * @param strPhone
     * @param strPsw
     */
    public void httpLogin(final String strPhone, final String strPsw, ResultListener resultListener) {

        Map<String, String> map = new HashMap<>();
        map.put("mobile", strPhone);
        map.put("password", strPsw);
        httpTool.httpLoadNoNetTipPost(UserCloudApi.USER_LOGIN, map, resultListener);
    }


    /**
     * 第三方登录
     *
     * @param unionId
     * @param head
     * @param nick
     * @param otherLoginType
     */
    public void httpAuthorize(final String unionId, final String head, final String nick, final int otherLoginType, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("ids", unionId);
        map.put("head", head);
        map.put("nick", nick);
        //0微信1新浪 2qq
        map.put("type", String.valueOf(otherLoginType));

        httpTool.httpLoadNoNetTipPost(UserCloudApi.USER_AUTHORIZE, map, resultListener);
    }

    /**
     * 注册
     *
     * @param mobile
     * @param password
     * @param code
     * @param resultListener
     */
    public void httpRegister(String mobile, final String password, final String code, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("code", code);

        httpTool.httpLoadPost(UserCloudApi.USER_REGISTER, map, resultListener);
    }

    /**
     * 获取验证码
     *
     * @param mobile
     * @param type
     * @param resultListener
     */
    public void httpGetCode(final String mobile, final int type, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        //1：注册  2：找回密码
        map.put("type", String.valueOf(type));

        httpTool.httpLoadPost(UserCloudApi.USER_CODE, map, resultListener);
    }

    /**
     * 注册协议
     *
     * @param resultListener
     */
    public void httpZCXY(ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();

        httpTool.httpLoadPostSaveData(UserCloudApi.ZCXY, map, resultListener);
    }

    /**
     * 忘记密码
     *
     * @param mobile
     * @param password
     * @param code
     * @param resultListener
     */
    public void httpFindPsw(String mobile, final String password, String code, ResultListener resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("code", code);
        httpTool.httpLoadPost(UserCloudApi.USER_FIND, map, resultListener);
    }

    /**
     * 修改密码
     *
     * @param sessionId
     * @param newPwd
     * @param oldPwd
     * @param code
     * @param resultListener
     */
    public void httpAlterPsw(String sessionId, String newPwd, final String oldPwd, String code, ResultListener resultListener) {

        if (checkSessionIdNull(sessionId)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("sessionId", sessionId);
        map.put("newPwd", newPwd);
        map.put("oldPwd", oldPwd);
        map.put("code", code);
        httpTool.httpLoadPost(UserCloudApi.userUpdatePwd, map, resultListener);
    }

    /**
     * 退出登录
     *
     * @param sessionId
     * @param resultListener
     */
    public void httpLogout(String sessionId, ResultListener resultListener) {

        if (checkSessionIdNull(sessionId)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("sessionId", sessionId);
        httpTool.httpLoadPost(UserCloudApi.USER_LOGOUT, map, resultListener);
    }

    /**
     * 提交意见
     *
     * @param sessionId
     * @param resultListener
     */
    public void httpSubmitFeedBack(String sessionId, String strMessage, String strContent, ResultListener resultListener) {

        if (checkSessionIdNull(sessionId)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("content", strMessage);
        if (!TextUtils.isEmpty(strContent)) {
            map.put("contact", strContent);
        }
        map.put("sessionId", sessionId);
        httpTool.httpLoadPost(UserCloudApi.PROBLEM_SAVE, map, resultListener);
    }

    /**
     * 关于我们
     *
     * @param resultListener
     */
    public void httpAboutUs(ResultListener resultListener) {

        Map<String, String> map = new HashMap<>();
        map.put("name", "gywm");
        httpTool.httpLoadPostSaveData(UserCloudApi.CONFIGURE_GET, map, resultListener);
    }

    /**
     * 检测版本
     *
     * @param resultListener
     */
    public void httpCheckVersion(ResultListener resultListener) {

        Map<String, String> map = new HashMap<>();
        //0:android 1:ios
        map.put("type", String.valueOf(0));
        httpTool.httpLoadPost(UserCloudApi.APP_VERSION_GET, map, resultListener);
    }

    /**
     * 绑定手机号
     *
     * @param sessionId
     * @param mobile
     * @param code
     * @param resultListener
     */
    public void httpBindMobile(String sessionId, String mobile, String code, ResultListener resultListener) {

        if (checkSessionIdNull(sessionId)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("sessionId", sessionId);
        map.put("mobile", mobile);
        map.put("code", code);
        httpTool.httpLoadPost(UserCloudApi.USER_UPDATE_MOBILE, map, resultListener);
    }

    /**
     * 绑定手机号
     *
     * @param sessionId
     * @param resultListener
     */
    public void httpUserInfo(String sessionId, ResultListener resultListener) {

        if (checkSessionIdNull(sessionId)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("sessionId", sessionId);
        httpTool.httpLoadPost(UserCloudApi.USER_HOME, map, resultListener);
    }
}
