package com.jm.xpproject.http.api;

import android.content.Context;

import com.jm.core.common.tools.base.SharedPreferencesTool;
import com.jm.core.common.tools.safety.Base64Util;
import com.jm.xpproject.config.GlobalConstant;

/**
 * 网络链接
 *
 * @author jinXiong.Xie
 */

public class BaseCloudApi {

    //  	public static String IP = "192.168.1.1";
    private static String IP = "";
    private static final String PROJECT_NAME = "bengangtai";
    /**
     * 是否发布
     */
    private static final boolean RELEASE = false;


    //http://121.14.7.232:8080，http://121.14.7.232:8080
    private static final String[] IP_ARRAY = {"aHR0cDovLzEyMS4xNC43LjIzMjo4MDgw", "aHR0cDovLzEyMS4xNC43LjIzMjo4MDgw"};
    private static final String[] IP_NAME_ARRAY = {"测试服", "正式服"};


    //  http://192.168.1.1/xp/upload/ads/20170802/1501661576866_156601.png

    //  public static String SERVLET_URL_IMAGE = "http://" + IP + "/xp/";
//  public static String SERVLET_URL_IMAGE2 = "http://" + IP + "/xp";
//  public static String SERVLET_URL = SERVLET_URL_IMAGE + "api/";
    private static String SERVLET_URL = "";
    private static String SERVLET_URL_API = "";

    public static boolean isRELEASE() {
        return RELEASE;
    }

    public static String getIP() {
        return IP;
    }

    public static String[] getIpArray() {
        return IP_ARRAY;
    }

    public static String[] getIpNameArray() {
        return IP_NAME_ARRAY;
    }

    /**
     * @param url
     * @return 返回文件的url
     */
    public static String getFileUrl(String url) {
        return SERVLET_URL + url;
    }

    /**
     * @param url
     * @return 返回网络请求的url
     */
    public static String getHttpUrl(String url) {
        return SERVLET_URL_API + url;
    }

    /**
     * 刷新IP
     */
    public static void refreshIP(Context context) {

        if (IP_ARRAY == null || IP_ARRAY.length <= 0) {
            return;
        }
        if (RELEASE) {
            for (int i = 0; i < IP_NAME_ARRAY.length; i++) {
                if (IP_NAME_ARRAY[i].contains("正式服")) {
                    IP = IP_ARRAY[i];
                    break;
                }
            }
        } else {
            SharedPreferencesTool tool = new SharedPreferencesTool(context, GlobalConstant.SERVER_IP);
            IP = (String) tool.getParam(GlobalConstant.SERVER_IP, IP_ARRAY[0]);
        }
        SERVLET_URL = "" + Base64Util.decode(IP) + "/" + PROJECT_NAME;
        SERVLET_URL_API = SERVLET_URL + "/api/";
    }

}
