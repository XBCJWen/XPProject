package com.jm.core.common.widget.titlebar.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.core.common.widget.titlebar.MyTitleBar;

/**
 * MyTitleBar需要实现的功能
 *
 * @author jinXiong.Xie
 */

public interface MyTitleBarImpl {


    /**
     * 替换之前的右布局
     *
     * @param rightView
     * @return MyTitleBar 返回标题
     */
    MyTitleBar addRightView(View rightView);

    /**
     * 替换之前的右布局
     *
     * @param layoutResID
     * @return MyTitleBar 返回标题
     */
    View addRightView(int layoutResID);

    /**
     * 设置左边Icon
     *
     * @param resId
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setLeftImageResource(int resId);

    /**
     * 设置右边Icon
     *
     * @param resId
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setRightImageResource(int resId);


    /**
     * 设置左边文本
     *
     * @param leftText
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setLeftText(String leftText);

    /**
     * 设置右边文本
     *
     * @param rightText
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setRightText(String rightText);

    /**
     * 设置左边监听
     *
     * @param listener
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setLeftLayoutClickListener(View.OnClickListener listener);

    /**
     * 设置标题监听
     *
     * @param listener
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setTitleClickListener(View.OnClickListener listener);

    /**
     * 设置右边监听
     *
     * @param listener
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setRightLayoutClickListener(View.OnClickListener listener);

    /**
     * 设置左边能见度
     *
     * @param visibility
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setLeftLayoutVisibility(int visibility);

    /**
     * 设置右边能见度
     *
     * @param visibility
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setRightLayoutVisibility(int visibility);

    /**
     * 设置标题
     *
     * @param title
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setTitleText(String title);

    /**
     * 设置标题为左边形式
     *
     * @return MyTitleBar 返回标题
     */
    MyTitleBar setLeftTitleStyle();


    /**
     * 设置标题
     *
     * @param leftVisibility 左边是否可见（true为可见）
     * @param title          标题名字
     * @param rightText      右边文字（为null则不显示）
     */
    void setTitle(boolean leftVisibility, String title,
                  String rightText);

    /**
     * 设置标题
     *
     * @param leftVisibility  左边是否可见（true为可见）
     * @param title           标题名字
     * @param rightDrawableId 右边图片（为0则不显示）
     */
    void setTitle(boolean leftVisibility, String title,
                  int rightDrawableId);

    void setTitle(boolean leftVisibility, String title);

    void setTitle(String leftText, String title);

    void setTitle(String leftText, String title, String rightText);

    void setTitle(String leftText, String title, int rightDrawableId);

    void setTitle(int leftDrawableId, String title);

    void setTitle(int leftDrawableId, String title, String rightText);

    void setTitle(int leftDrawableId, String title, int rightDrawableId);

    void setTitle(String title);

    /**
     * 设置未读数
     *
     * @param num
     */
    void setUnReadNum(int num);

    /**
     * 设置背景颜色
     *
     * @param color
     */
    void setTitleBarBackgroundColor(int color);

    LinearLayout getLeftLayout();

    ImageView getLeftImage();

    TextView getLeftTextView();

    LinearLayout getRightLayout();

    ImageView getRightImage();

    TextView getRightTextView();

    TextView getTitleView();

    RelativeLayout getTitleLayout();
}
