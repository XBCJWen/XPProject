package com.jm.core.common.tools.base;

import android.content.Context;
import android.widget.TextView;

/**
 * 颜色工具类
 *
 * @author jinXiong.Xie
 */
public class ColorUtil {

    private ColorUtil() {
    }

    /**
     * 设置文本颜色
     *
     * @param textView
     * @param color
     */
    public static void setTextColor(TextView textView, int color) {
        textView.setTextColor(textView.getContext().getResources().getColor(color));
    }

    /**
     * 获取颜色
     *
     * @param context
     * @param color
     * @return
     */
    public static int getColor(Context context, int color) {
        return context.getResources().getColor(color);
    }

}
