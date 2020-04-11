package com.jm.xpproject.ui.setting.util;

import android.content.Context;
import android.webkit.WebView;

import com.jm.core.common.tools.font.WebViewUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;
import com.jm.xpproject.utils.xp.XPVersionUtil;

import org.json.JSONObject;

/**
 * 基于小跑的关于我们工具类
 *
 * @author jinXiong.Xie
 */

public class XPAboutUsUtil extends XPBaseUtil {
    private XPVersionUtil versionUtil;

    public XPAboutUsUtil(Context context) {
        super(context);

        versionUtil = new XPVersionUtil(context, context.getResources().getString(R.string.app_name));
    }

    /**
     * 检测更新
     */
    public void checkVersion() {
        if (versionUtil != null) {
            versionUtil.checkVersion();
        }
    }

    /**
     * 关闭正在更新的对话框
     */
    public void closeMustUpdateDialog() {
        if (versionUtil != null) {
            versionUtil.closeMustUpdateDialog();
        }
    }

    /**
     * 加载关于我们的信息
     *
     * @param webView
     */
    public void loadUsInfo(WebView webView) {
        httpAboutUs(webView);
    }

    private void LoadHtml(String content, WebView webView) {
        WebViewUtil.loadHtml(content, webView);
    }


    private void httpAboutUs(final WebView webView) {
        HttpCenter.getInstance(getContext()).getUserHttpTool().httpAboutUs(new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                LoadHtml(obj.optString("data"), webView);
            }
        });
    }

}
