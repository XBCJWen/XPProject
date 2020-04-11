package com.jm.core.common.tools.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断联网状态
 */
public class NetworkStatus {

    private Context context;
    private ConnectivityManager cm;
    private NetworkInfo info;
    /**
     * 连接wifi状态 == 1
     */
    public static final int TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
    /**
     * 连接流量状态 == 0
     */
    public static final int TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    /**
     * 没有联网
     */
    public static final int TYPE_NO_CONNECTED = -1;


    public NetworkStatus(Context context) {
        this.context = context;
    }

    private void getNetWorkInfo() {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        info = cm.getActiveNetworkInfo();
    }

    /**
     * 判断是否有联网
     *
     * @return true or false
     */
    public boolean isNetAvilable() {
        if (cm == null && info == null) {
            getNetWorkInfo();
        }
        return info != null && info.isAvailable();
    }

    /**
     * 判断是否连接wifi
     *
     * @return true or false
     */
    public boolean isWifiConnected() {
        if (cm == null && info == null) {
            getNetWorkInfo();
        }
        return TYPE_WIFI == info.getType();
    }


    /**
     * 获取联网状态
     *
     * @return true or false
     */
    public int getNetWorkStatus() {
        getNetWorkInfo();
        if (info != null && info.isConnected()) {
            return info.getType();
        } else {
            return TYPE_NO_CONNECTED;
        }
    }



    /**
     * 判断网络状态
     *
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        } else {
            return TYPE_NO_CONNECTED;
        }

        return TYPE_NO_CONNECTED;
    }


}