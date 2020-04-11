package com.jm.api.base;

import com.umeng.analytics.MobclickAgent;
import com.jm.core.common.tools.bar.compat.StatusBarCompat;
import com.jm.core.common.tools.bar.statusbar.StatusBarManager;
import com.jm.core.common.tools.bar.util.StatusBarUtil;
import com.jm.core.common.tools.base.PixelsTools;
import com.jm.core.framework.BaseTitleBarActivity;

/**
 * 基于BaseTitleBarActivity的修改
 *
 * @author jinXiong.Xie
 */

public abstract class ApiTitleBarActivity extends BaseTitleBarActivity {

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(int colorId) {
        StatusBarUtil.setColor(this, getResources().getColor(colorId), 0);

    }

    /**
     * 设置状态栏为白色字体
     */
    public void setStatusBarWhiteFont() {
        StatusBarManager.newInstance().initStatusBar(this, true);
    }
    /**
     * 设置状态栏为黑色字体
     */
    public void setStatusBarBlackFont() {
        StatusBarManager.newInstance().initStatusBar(this, false);
    }
    /**
     * 隐藏状态栏
     */
    public void hideStatusBar() {

        hideStatusBar(false);
    }

    /**
     * 隐藏状态栏
     */
    public void hideStatusBar(boolean hideStatusBarBackground) {

        StatusBarCompat.translucentStatusBar(this, hideStatusBarBackground);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    public void showDataErrorToast() {
        showToast("数据异常");
    }


    public int dip2Px(int dpValue) {
        return PixelsTools.dip2Px(getActivity(), dpValue);
    }

}
