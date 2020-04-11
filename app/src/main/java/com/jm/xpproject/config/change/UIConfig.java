package com.jm.xpproject.config.change;

import com.jm.xpproject.R;
import com.jm.xpproject.ui.main.fgm.GuideFgm;

/**
 * 用于APP UI类型的一些配置信息
 *
 * @author jinXiong.Xie
 */
public class UIConfig {

    //启动页
    /**
     * 启动页默认显示的图片
     */
    public static final int SPLASH_IMAGE_VIEW = R.mipmap.ic_launcher;

    // 刷新模块
    /**
     * 刷新的背景颜色
     */
    public static final int REFRESH_BG_COLOR = R.color.white;
    /**
     * 刷新的字体颜色
     */
    public static final int REFRESH_FONT_COLOR = R.color.color262626;

    // 引导页
    /**
     * 引导页的图片
     */
    public static final int[] GUIDE_IMAGE = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    /**
     * 引导页的选择的下标背景
     */
    public static final int GUIDE_INDEX_SELECT_BG = R.drawable.bg_guide_index_select;
    /**
     * 引导页的正常的下标背景
     */
    public static final int GUIDE_INDEX_NORMAL_BG = R.drawable.bg_guide_index_normal;


    // 主页面的导航栏
    /**
     * 导航栏的Fragment
     */
    public static final Class[] GUIDE_FGM = new Class[]{GuideFgm.class, GuideFgm.class, GuideFgm.class, GuideFgm.class};
    /**
     * 导航栏的下标名字
     */
    public static final String[] GUIDE_NAME = new String[]{"One", "Two", "Three", "Four"};
    /**
     * 导航栏的下标选择的图标样式
     */
    public static final int[] GUIDE_SELECT_ICON = new int[]{R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
    /**
     * 导航栏的下标正常的图标样式
     */
    public static final int[] GUIDE_NORMAL_ICON = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    /**
     * 导航栏的下标名字选择后的颜色
     */
    public static final int GUIDE_SELECT_TEXT_COLOR = R.color.colorAccent;
    /**
     * 导航栏的下标名字未选择的颜色
     */
    public static final int GUIDE_NORMAL_TEXT_COLOR = R.color.colorPrimary;

}
