package com.jm.core.common.tools.click;

import android.view.View;

import java.util.Calendar;

/**
 * 防止快速多次点击按钮
 * Created by Jinxiong.Xie on 2017/3/6 15:31.
 * 邮箱：173500512@qq.com
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);

}