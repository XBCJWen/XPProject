package com.jm.xpproject.ui.common.act;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;

import com.jm.api.util.IntentUtil;
import com.jm.core.common.tools.font.WebViewUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * 协议
 *
 * @author jinXiong.Xie
 */
public class ProtocolAct extends MyTitleBarActivity {

    @BindView(R.id.webView)
    WebView webView;

    /**
     * 注册协议
     */
    public static final int REGISTER = 0;

    /**
     * 协议类型
     */
    private int type;

    @Override
    protected int layoutResID() {
        return R.layout.activity_protocol;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "协议");
    }

    @Override
    public void initViewAndUtil() {
        initWebView();

    }

    @Override
    public void initNetLink() {

        httpProtocol();
    }


    private void initWebView() {
        WebViewUtil.setWebViewSetting(webView);

//    webView.setBackgroundColor(0);
//    webView.getBackground().setAlpha(0);
    }


    private void httpProtocol() {

        switch (type) {
            case REGISTER:
                setTitle(true, "注册协议");
                HttpCenter.getInstance(this).getUserHttpTool().httpZCXY(new LoadingErrorResultListener(this) {
                    @Override
                    public void normal(int id, JSONObject obj, Object[] data) {
                        showViewData(obj.optString("data"));
                    }
                });
                break;
            default:
                break;
        }

    }

    private void showViewData(final String desc) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                LoadHtml(desc);
            }
        });
    }

    private void LoadHtml(String content) {
        WebViewUtil.loadHtml(content, webView);
    }

    @Override
    protected void initData(Bundle data) {
        super.initData(data);

        type = data.getInt("type");
    }

    public static void actionStart(Context context, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        IntentUtil.intentToActivity(context, ProtocolAct.class, bundle);

    }
}
