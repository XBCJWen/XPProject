package com.jm.core.framework;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.core.R;
import com.jm.core.common.tools.base.ColorUtil;
import com.jm.core.common.widget.titlebar.MyTitleBar;
import com.jm.core.common.widget.titlebar.impl.MyTitleBarImpl;

/**
 * 默认具有标题的Activity
 *
 * @author jinXiong.Xie
 */

public abstract class BaseTitleBarActivity extends BaseActivity implements MyTitleBarImpl {

    protected MyTitleBar titleBar;

    private View rootView;

    public View getRootView() {
        return rootView;
    }

    /**
     * 是否浮动
     */
    private boolean floatTitle = false;

    public void setFloatTitle(boolean floatTitle) {
        this.floatTitle = floatTitle;
    }

    @Override
    protected View layoutView() {
        RelativeLayout relativeLayout = (RelativeLayout) inflateLayout(R.layout.activity_title_bar);
        titleBar = new MyTitleBar(getActivity());
        rootView = LayoutInflater.from(this).inflate(layoutResID(), relativeLayout, false);

        if (floatTitle) {
            relativeLayout.addView(rootView);
            relativeLayout.addView(titleBar);
        } else {
            titleBar.setId(R.id.title_bar);

            relativeLayout.addView(titleBar);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            lp.addRule(RelativeLayout.BELOW, relativeLayout.getChildAt(0).getId());


            rootView.setLayoutParams(lp);

            relativeLayout.addView(rootView);
        }


        //默认设置左边布局的返回键是关闭当前页面
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return relativeLayout;
    }


    /**
     * 设置标题的隐藏或显示
     *
     * @param visibility
     */
    protected void setTitleBarVisibility(int visibility) {
        titleBar.setVisibility(visibility);
    }

    /**
     * 隐藏TitleBar
     */
    protected void hideTitleBar() {
        setTitleBarVisibility(View.GONE);
    }

    /**
     * 布局Id
     *
     * @return
     */
    protected abstract int layoutResID();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    @Override
    protected void initView() {
        initTitle();
        init();
    }

    /**
     * 初始化
     */
    protected abstract void init();


    /**
     * 替换之前的右布局
     *
     * @param rightView
     * @return
     */
    @Override
    public MyTitleBar addRightView(View rightView) {
        return titleBar.addRightView(rightView);
    }

    /**
     * 替换之前的右布局
     *
     * @param layoutResID
     * @return
     */
    @Override
    public View addRightView(int layoutResID) {
        return titleBar.addRightView(layoutResID);
    }


    @Override
    public MyTitleBar setLeftImageResource(int resId) {
        return titleBar.setLeftImageResource(resId);
    }

    @Override
    public MyTitleBar setRightImageResource(int resId) {
        return titleBar.setRightImageResource(resId);
    }

    @Override
    public MyTitleBar setLeftText(String leftText) {
        return titleBar.setLeftText(leftText);
    }

    @Override
    public MyTitleBar setRightText(String rightText) {
        return titleBar.setRightText(rightText);
    }

    @Override
    public MyTitleBar setLeftLayoutClickListener(View.OnClickListener listener) {
        return titleBar.setLeftLayoutClickListener(listener);
    }

    @Override
    public MyTitleBar setTitleClickListener(View.OnClickListener listener) {
        return titleBar.setTitleClickListener(listener);
    }

    @Override
    public MyTitleBar setRightLayoutClickListener(View.OnClickListener listener) {
        return titleBar.setRightLayoutClickListener(listener);
    }

    @Override
    public MyTitleBar setLeftLayoutVisibility(int visibility) {
        return titleBar.setLeftLayoutVisibility(visibility);
    }

    @Override
    public MyTitleBar setRightLayoutVisibility(int visibility) {
        return titleBar.setRightLayoutVisibility(visibility);
    }

    @Override
    public MyTitleBar setTitleText(String title) {
        return titleBar.setTitleText(title);
    }

    @Override
    public MyTitleBar setLeftTitleStyle() {
        return titleBar.setLeftTitleStyle();
    }

    @Override
    public void setTitle(boolean leftVisibility, String title, String rightText) {
        titleBar.setTitle(leftVisibility, title, rightText);
    }

    @Override
    public void setTitle(boolean leftVisibility, String title, int rightDrawableId) {
        titleBar.setTitle(leftVisibility, title, rightDrawableId);
    }

    @Override
    public void setTitle(boolean leftVisibility, String title) {
        titleBar.setTitle(leftVisibility, title);
    }

    @Override
    public void setTitle(String leftText, String title) {
        titleBar.setTitle(leftText, title);
    }

    @Override
    public void setTitle(String leftText, String title, String rightText) {
        titleBar.setTitle(leftText, title, rightText);
    }

    @Override
    public void setTitle(String leftText, String title, int rightDrawableId) {
        titleBar.setTitle(leftText, title, rightDrawableId);
    }

    @Override
    public void setTitle(int leftDrawableId, String title) {
        titleBar.setTitle(leftDrawableId, title);
    }

    @Override
    public void setTitle(int leftDrawableId, String title, String rightText) {
        titleBar.setTitle(leftDrawableId, title, rightText);
    }

    @Override
    public void setTitle(int leftDrawableId, String title, int rightDrawableId) {
        titleBar.setTitle(leftDrawableId, title, rightDrawableId);
    }

    @Override
    public void setTitle(String title) {
        titleBar.setTitle(title);
    }

    @Override
    public void setTitleBarBackgroundColor(int color) {
        titleBar.setBackgroundColor(ColorUtil.getColor(this, color));
    }

    @Override
    public void setUnReadNum(int num) {
        titleBar.setUnReadNum(num);
    }

    @Override
    public LinearLayout getLeftLayout() {
        return titleBar.getLeftLayout();
    }

    @Override
    public ImageView getLeftImage() {
        return titleBar.getLeftImage();
    }

    @Override
    public TextView getLeftTextView() {
        return titleBar.getLeftTextView();
    }

    @Override
    public LinearLayout getRightLayout() {
        return titleBar.getRightLayout();
    }

    @Override
    public ImageView getRightImage() {
        return titleBar.getRightImage();
    }

    @Override
    public TextView getRightTextView() {
        return titleBar.getRightTextView();
    }

    @Override
    public TextView getTitleView() {
        return titleBar.getTitleView();
    }

    @Override
    public RelativeLayout getTitleLayout() {
        return titleBar.getTitleLayout();
    }
}
