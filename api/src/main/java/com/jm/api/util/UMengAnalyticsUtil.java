package com.jm.api.util;

import com.umeng.analytics.MobclickAgent;

/**
 * 友盟统计工具类
 *
 * @author jinXiong.Xie
 */
public class UMengAnalyticsUtil {

    /**
     * 统计帐号
     *
     * @param id
     */
    public static void onProfileSignIn(String id) {
        MobclickAgent.onProfileSignIn(null, id);
    }

    /**
     * 统计帐号
     *
     * @param provider 帐号类别
     * @param id       帐号ID
     */
    public static void onProfileSignIn(String provider, String id) {
        MobclickAgent.onProfileSignIn(provider, id);
    }

    /**
     * 账号登出时需调用此接口，调用之后不再发送账号相关内容
     */
    public static void onProfileSignOff() {
        MobclickAgent.onProfileSignOff();
    }
}
