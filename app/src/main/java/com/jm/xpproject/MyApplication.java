package com.jm.xpproject;

import android.content.Context;
import android.content.IntentFilter;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.jm.api.util.UMengUtil;
import com.jm.core.common.tools.layout.RefreshLoadTool;
import com.jm.core.common.tools.utils.Utils;
import com.jm.core.common.tools.utils.VersionUtil;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.config.change.UIConfig;
import com.jm.xpproject.http.api.BaseCloudApi;
import com.jm.xpproject.receiver.NetBroadcastReceiver;

import org.litepal.LitePal;

/**
 * 自定义App(项目全局配置)
 *
 * @author jinXiong.Xie
 */

public class MyApplication extends MultiDexApplication {

    //微信、QQ、新浪配置
    {
        PlatformConfig.setWeixin(DataConfig.WECHAT_APP_ID, DataConfig.WECHAT_APP_SECRET);
        PlatformConfig.setQQZone(DataConfig.QQ_APP_ID, DataConfig.QQ_APP_SECRET);
        PlatformConfig.setSinaWeibo(DataConfig.SINA_APP_ID, DataConfig.SINA_APP_SECRET, DataConfig.SINA_REDIRECT_URL);
    }

    private NetBroadcastReceiver receiver;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //刷新IP
        BaseCloudApi.refreshIP(this);

        //LitePal
        LitePal.initialize(this);

        //Bugly
        loadBugly();

        Utils.init(this);
//        //初始化Api的Module的数据
//        ApiConstant.init(DataConfig.LOGIN_CLASS, DataConfig.WECHAT_APP_ID);
        UMengUtil.needAuth(getApplicationContext(), true);

        //初始化刷新的样式
        InitRL();
        //初始化友盟
        //58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMConfigure.init(this, DataConfig.UMENG_KEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        registerNetworkReceiver();
        //UMShareAPI.get(this);
    }

    /**
     * 初始化Bugly
     */
    private void loadBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = VersionUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, DataConfig.BUGLY_APP_ID, false, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
    }

    /**
     * 注册网络广播
     */
    private void registerNetworkReceiver() {
        if (receiver == null) {
            receiver = new NetBroadcastReceiver();

            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(receiver, filter);
        }
    }


    private void InitRL() {
        RefreshLoadTool.init(UIConfig.REFRESH_BG_COLOR, UIConfig.REFRESH_FONT_COLOR);
    }
}
