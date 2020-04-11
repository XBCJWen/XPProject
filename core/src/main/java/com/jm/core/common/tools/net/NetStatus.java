package com.jm.core.common.tools.net;

import android.content.Context;

/**
 * 判断是否有网络
 *
 * @author jinXiong.Xie
 */

public class NetStatus {

    private NetStatus() {
    }

    /**
     * 获取网络状态
     *
     * @param context
     * @param netStatusCallBack
     */
    public static void requestNetStatus(Context context, NetStatusCallBack netStatusCallBack) {
        NetworkStatus networkStatus = new NetworkStatus(context);
        if (netStatusCallBack != null) {
            if (networkStatus.isNetAvilable()) {
                netStatusCallBack.onNet();
            } else {
                netStatusCallBack.onNoNet();
            }
        }

    }

    public interface NetStatusCallBack {

        /**
         * 有网
         */
        void onNet();

        /**
         * 无网
         */
        void onNoNet();
    }

}