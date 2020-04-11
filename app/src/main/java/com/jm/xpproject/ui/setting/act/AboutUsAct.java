package com.jm.xpproject.ui.setting.act;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import com.jm.api.appupdater.ApkInstallUtil;
import com.jm.api.util.IntentUtil;
import com.jm.core.common.tools.font.WebViewUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.ui.setting.util.XPAboutUsUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于我们
 *
 * @author jinXiong.Xie
 */
public class AboutUsAct extends MyTitleBarActivity {

    @BindView(R.id.webView)
    WebView webView;

    private XPAboutUsUtil xpAboutUsUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "关于我们");
    }

    @Override
    public void initViewAndUtil() {

        xpAboutUsUtil = new XPAboutUsUtil(this);

        initWebView();
    }

    @Override
    public void initNetLink() {

        xpAboutUsUtil.loadUsInfo(webView);
    }


    private void initWebView() {

        WebViewUtil.setWebViewSetting(webView);
    }


    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Bundle bundle = new Bundle();
        IntentUtil.intentToActivity(context, AboutUsAct.class, bundle);
    }


    @OnClick(R.id.tv_check_version)
    public void onViewClicked() {
        xpAboutUsUtil.checkVersion();
    }

    @Override
    protected void onRestart() {
        ApkInstallUtil.openAPKFile(this);
        super.onRestart();
    }

    @Override
    public void onEventCallBack(MessageEvent event) {
        super.onEventCallBack(event);

        if (event.getId() == MessageEvent.DOWNLOAD_FAILED) {
            xpAboutUsUtil.closeMustUpdateDialog();
        }
    }
}
