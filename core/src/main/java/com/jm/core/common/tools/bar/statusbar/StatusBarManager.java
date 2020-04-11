package com.jm.core.common.tools.bar.statusbar;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.jm.core.R;


public class StatusBarManager {
    //===================单例模式========================
    private static class SingletonHolder {
        private static StatusBarManager instance = new StatusBarManager();
    }

    private StatusBarManager() {
    }

    public static StatusBarManager newInstance() {
        return SingletonHolder.instance;
    }
    //===================单例模式========================

    public void initStatusBar(AppCompatActivity baseActivity, boolean isNightMode) {

        int statusbarColor;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusbarColor = isNightMode ? R.color.white_background_night : R.color.white_background;
        } else {
            statusbarColor = R.color.white_background_night;
        }

        StatusBarUtils.setWindowStatusBarColor(baseActivity, statusbarColor, isNightMode);

        StatusBarUtils.setMeizuStatusBarDarkIcon(baseActivity, false);//设置  魅族状态栏样式
        StatusBarUtils.setMiuiStatusBarDarkMode(baseActivity, false);//设置  小米状态栏样式
    }
}
