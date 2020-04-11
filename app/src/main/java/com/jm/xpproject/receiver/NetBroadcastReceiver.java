package com.jm.xpproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.jm.core.common.tools.net.NetworkStatus;
import com.jm.xpproject.config.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 自定义检查手机网络状态是否切换的广播接受器
 *
 * @author cj
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetworkStatus.getNetWorkState(context);

            EventBus.getDefault().post(new MessageEvent(MessageEvent.NETWORK_STATE, netWorkState));
        }
    }


}