package com.jm.core.framework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.jm.core.R;
import com.jm.core.common.tools.utils.TitleBarUtil;
import com.jm.core.common.widget.layout.LoadingLayout;
import com.jm.core.common.widget.titlebar.TitleBar;

/**
 * 默认具有Title和loading状态的Activity
 */

public abstract class TitleLoadingActivity extends BaseActivity{

  protected TitleBar titleBar;
  protected LoadingLayout vLoading;

  @Override
  protected View layoutView() {
    LinearLayout linearLayout = (LinearLayout) inflateLayout(R.layout.activity_title_loading);
    titleBar = (TitleBar) linearLayout.findViewById(R.id.titleBar);
    vLoading = (LoadingLayout) linearLayout.findViewById(R.id.loading);
    View root = LayoutInflater.from(this).inflate(layoutResID(), vLoading, false);
    vLoading.addView(root);
    vLoading = LoadingLayout.wrap(root);

    return linearLayout;
  }

  protected abstract int layoutResID();

  protected abstract void initTitle();

  @Override
  protected void initView() {

    initTitle();
    init();
  }

  protected abstract void init();

  /**
   * 设置标题
   *
   * @param leftVisibility 左边是否可见（true为可见）
   * @param title 标题名字
   * @param rightText 右边文字（为null则不显示）
   */
  protected void setTitle(boolean leftVisibility, String title, String rightText) {

    TitleBarUtil.setTitle(titleBar, leftVisibility, title, rightText);
  }

  /**
   * 设置标题
   *
   * @param leftVisibility 左边是否可见（true为可见）
   * @param title 标题名字
   * @param rightDrawableId 右边图片（为0则不显示）
   */
  protected void setTitle(boolean leftVisibility, String title, int rightDrawableId) {

    TitleBarUtil.setTitle(titleBar, leftVisibility, title, rightDrawableId);
  }

  /**
   * 设置右布局点击事件
   */
  protected void setRightClick(OnClickListener onClickListener) {
    TitleBarUtil.setRightClick(titleBar, onClickListener);
  }
  /**
   * 设置左布局点击事件
   */
  protected void setLeftClick(OnClickListener onClickListener) {
    TitleBarUtil.setLeftClick(titleBar, onClickListener);
  }
  protected void setTitle(String title) {
    setTitle(false, title, "");
  }

  protected void setTitle(boolean leftVisibility, String title) {
    setTitle(leftVisibility, title, "");
  }

  protected void setTitle(String leftText, String title) {
    setTitle(leftText, title, "");
  }

  protected void setTitle(String leftText, String title, String rightText) {
    TitleBarUtil.setTitle(titleBar, leftText, title, rightText);
  }
  protected void  setTitle(String leftText, String title, int rightDrawableId) {
    TitleBarUtil.setTitle(titleBar, leftText, title, rightDrawableId);
  }
}
