package com.jm.xpproject.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.jm.api.base.ApiTitleBarFragment;
import com.jm.core.common.tools.net.NetworkStatus;
import com.jm.xpproject.base.impl.EventBusInterface;
import com.jm.xpproject.base.impl.NetworkInterface;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.utils.DialogUtil;
import com.jm.xpproject.utils.MonitorNetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 基于MyTitleBarFragment的修改
 *
 * @author jinXiong.Xie
 */

public abstract class MyTitleBarFragment extends ApiTitleBarFragment implements EventBusInterface, NetworkInterface {

    private MonitorNetworkUtils monitorNetworkUtils;

    private View replaceView;

    /**
     * 是否隐藏标题
     */
    private boolean hideTitleBar;

    /**
     * 是否为隐藏整个Fragment的View
     */
    private boolean hideContentView;

    /**
     * 显示帐号信息异常的Dialog
     */
    public void showOtherLoginDialog() {
        if (getActivity() == null) {
            return;
        }
        DialogUtil util = DialogUtil.getInstance(getActivity(), DataConfig.LOGIN_CLASS);
        util.showOtherLoginDialog();
    }

    @Override
    protected void initSetting() {
        super.initSetting();

        monitorNetworkUtils = new MonitorNetworkUtils(getActivity());
    }

    @Override
    protected void init(View view) {

        hideTitleBar = getTitleBarVisibility() == View.GONE;

        //设置默认无网络的时候需要替换的View
//        setLayoutReplaceView();
        removeReplaceView();

        initViewAndUtil(view);

        if (new NetworkStatus(getActivity()).isNetAvilable()) {
            initNetLink();
        } else {
            initNetLink();
            showNoNetView();
        }
    }

    /**
     * 移除replaceView
     */
    @Override
    public void removeReplaceView() {
        this.replaceView = null;
    }


    /**
     * 设置无网络的布局
     *
     * @param notNetViewLayoutId
     */
    @Override
    public void setNotNetView(int notNetViewLayoutId) {
        monitorNetworkUtils.setNotNetView(inflateLayout(notNetViewLayoutId));
    }

    /**
     * 设置无网络的布局
     *
     * @param notNetView
     */
    @Override
    public void setNotNetView(View notNetView) {
        monitorNetworkUtils.setNotNetView(notNetView);
    }

    /**
     * 设置无网络时替换的View
     *
     * @param replaceView
     */
    @Override
    public void setReplaceView(View replaceView) {
        this.replaceView = replaceView;
    }

    /**
     * 设置无网络时替换的View
     *
     * @param replaceViewLayoutId
     */
    @Override
    public void setReplaceView(int replaceViewLayoutId) {
        setReplaceView(inflateLayout(replaceViewLayoutId));
    }

    /**
     * 将ReplaceView替换为整个窗口的View
     */
    @Override
    public void setContentReplaceView() {
        hideContentView = true;
        setLayoutReplaceView();
    }

    /**
     * 将ReplaceView替换为页面布局文件的View
     */
    @Override
    public void setLayoutReplaceView() {
        setReplaceView(getRootView());
    }

    /**
     * 初始化View和Util
     *
     *
     * @param view
     */
    public abstract void initViewAndUtil(View view);

    /**
     * 初始化网络连接
     */
    public abstract void initNetLink();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        registerEventBus();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        unregisterEventBus();
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getId() == MessageEvent.NETWORK_STATE) {

            int netState = (int) event.getMessage()[0];
            if (replaceView != null) {
                if (netState == NetworkStatus.TYPE_WIFI || netState == NetworkStatus.TYPE_MOBILE) {
                    monitorNetworkUtils.dismissView();

                    if (hideTitleBar) {
                        hideTitleBar();
                    } else {
                        setTitleBarVisibility(View.VISIBLE);
                    }

                    initNetLink();
                } else if (netState == NetworkStatus.TYPE_NO_CONNECTED) {
                    showNoNetView();
                }
            }

        }

        onEventCallBack(event);

    }

    /**
     * 显示无网络的视图
     */
    private void showNoNetView() {
        if (hideContentView) {
            setTitleBarVisibility(View.INVISIBLE);
        }
        monitorNetworkUtils.showView(replaceView);
    }

    /**
     * 事件回调
     *
     * @param event
     */
    @Override
    public void onEventCallBack(MessageEvent event) {

    }


    public String getSessionId() {
        UserData userData = UserData.getInstance();
        String strSessionId = userData.getSessionId();
        if (TextUtils.isEmpty(strSessionId)) {
            showOtherLoginDialog();
        }
        return strSessionId;
    }


    @Override
    public void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void postEvent(int eventId, Object... message) {
        EventBus.getDefault().post(new MessageEvent(eventId, message));
    }
}
