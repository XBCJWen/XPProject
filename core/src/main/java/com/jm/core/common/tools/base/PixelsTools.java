package com.jm.core.common.tools.base;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 获取手机像素
 *
 * @author xiejinxiong
 */
public class PixelsTools {

    private PixelsTools() {
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWidthPixels(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getHeightPixels(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int dip2Px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
