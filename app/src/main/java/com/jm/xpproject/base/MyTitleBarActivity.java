package com.jm.xpproject.base;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.jm.api.base.ApiTitleBarActivity;
import com.jm.core.common.tools.net.NetworkStatus;
import com.jm.core.common.tools.safety.AntiHijackingUtil;
import com.jm.core.common.tools.utils.ScreenUtils;
import com.jm.xpproject.R;
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
 * 基于ApiTitleBarActivity的修改
 *
 * @author jinXiong.Xie
 */

public abstract class MyTitleBarActivity extends ApiTitleBarActivity implements EventBusInterface, NetworkInterface {

    private MonitorNetworkUtils monitorNetworkUtils;

    private View replaceView;


    public String getSessionId() {
        UserData userData = UserData.getInstance();
        String strSessionId = userData.getSessionId();
        if (TextUtils.isEmpty(strSessionId)) {
            showOtherLoginDialog();
        }
        return strSessionId;
    }

    @Override
    protected void initSetting() {
        super.initSetting();

        monitorNetworkUtils = new MonitorNetworkUtils(getActivity());

        ScreenUtils.adaptScreen4VerticalSlide(this, DataConfig.DESIGN_WIDTH_IN_PX);
    }

    @Override
    protected void init() {

        //设置默认无网络的时候需要替换的View
//        setLayoutReplaceView();
        removeReplaceView();

        initViewAndUtil();
        initNetLink();
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
     * 移除replaceView
     */
    @Override
    public void removeReplaceView() {
        this.replaceView = null;
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
        this.replaceView = inflateLayout(replaceViewLayoutId);
    }

    /**
     * 将ReplaceView替换为整个窗口的View
     */
    @Override
    public void setContentReplaceView() {
        setReplaceView((getActivity().getWindow().getDecorView().findViewById(android.R.id.content)));
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
     */
    public abstract void initViewAndUtil();

    /**
     * 初始化网络连接
     */
    public abstract void initNetLink();


    /**
     * 显示帐号信息异常的Dialog
     */
    public void showOtherLoginDialog() {
        if (this == null) {
            return;
        }
        DialogUtil util = DialogUtil.getInstance(this, DataConfig.LOGIN_CLASS);
        util.showOtherLoginDialog();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        registerEventBus();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
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

                    initNetLink();
                } else if (netState == NetworkStatus.TYPE_NO_CONNECTED) {
                    monitorNetworkUtils.showView(replaceView);
                }
            }

        }

        onEventCallBack(event);

    }

    /**
     * 事件回调
     *
     * @param event
     */
    @Override
    public void onEventCallBack(MessageEvent event) {

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

    @Override
    public void onResume() {
        super.onResume();

        AntiHijackingUtil.initSafePackages(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 白名单
                boolean safe = AntiHijackingUtil.checkActivity(getApplicationContext());
                if (safe) {
                    return;
                } else {
                    showActivityHijackToast();
                }
                // 系统桌面
                boolean isHome = AntiHijackingUtil.isHome(getApplicationContext());
                if (isHome) {
                    return;
                } else {
                    showActivityHijackToast();
                }
                // 锁屏操作
                boolean isReflectScreen = AntiHijackingUtil.isReflectScreen(getApplicationContext());
                if (isReflectScreen) {
                    return;
                } else {
                    showActivityHijackToast();
                }
            }
        }).start();

    }

    /**
     * 显示Activity已被劫持提示
     */
    private void showActivityHijackToast() {
        Looper.prepare();
        Toast.makeText(getApplicationContext(), getString(R.string.app_name) + getString(R.string.activity_safe_warning),
                Toast.LENGTH_LONG).show();
        Looper.loop();
    }

}
