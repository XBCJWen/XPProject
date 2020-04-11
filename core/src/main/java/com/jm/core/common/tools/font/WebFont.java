package com.jm.core.common.tools.font;

import android.webkit.WebView;

/**
 * @author xiejinxiong
 *         <p>
 *         对文字进行修改，从而以网页的表示方式显示
 *         <p>
 *         默认：字体：16px，字体颜色：#323232，背景颜色：#FFFFFF
 */
public class WebFont {

    private WebFont() {
    }

    public static void showWebFont(WebView myWebView, String content) {

        showWebFont(myWebView, content, 16);

    }

    public static void showWebFont(WebView myWebView, String content,
                                   int fontSize) {

        showWebFont(myWebView, content, fontSize, "#323232");

    }

    public static void showWebFont(WebView myWebView, String content,
                                   int fontSize, String fontColor) {

        showWebFont(myWebView, content, fontSize, fontColor, "#FFFFFF");

    }

    /**
     * 显示文字
     *
     * @param myWebView       显示文字的webview
     * @param content         显示的文字
     * @param fontSize        文字大小
     * @param fontColor       文字颜色
     * @param backgroundColor 显示文字的背景
     */
    public static void showWebFont(WebView myWebView, String content,
                                   int fontSize, String fontColor, String backgroundColor) {

        // 设置webview不允许竖直滚动
        myWebView.setVerticalScrollBarEnabled(false);

        myWebView.loadDataWithBaseURL("",
                "<![CDATA[<html> <head></head> <body style=\"background-color:"
                        + backgroundColor + ";text-align:justify;font-size:"
                        + fontSize + "px;color:" + fontColor
                        + ";text-indent:2em;\"> " + content
                        + " <p> </body></html>", "text/html", "utf-8", "");

    }
}
