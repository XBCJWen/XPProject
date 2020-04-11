package com.jm.core.common.widget.titlebar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.core.R;
import com.jm.core.common.tools.base.UnReadUtil;
import com.jm.core.common.widget.titlebar.impl.MyTitleBarImpl;

/**
 * 标题栏
 *
 * @author jinXiong.Xie
 */
public class MyTitleBar extends RelativeLayout implements MyTitleBarImpl {

    protected LinearLayout llTitleBar;
    protected LinearLayout leftLayout;
    protected ImageView leftImage;
    private TextView leftTextView;
    protected LinearLayout rightLayout;
    protected ImageView rightImage;
    private TextView rightTextView;
    protected TextView titleView;
    protected TextView tvUnReadNum;
    protected RelativeLayout titleLayout;

    public MyTitleBar(Context context) {
        this(context, null);
    }

    public MyTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);

        llTitleBar = (LinearLayout) findViewById(R.id.ll_title_bar);
        leftLayout = (LinearLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (LinearLayout) findViewById(R.id.right_layout);
        rightImage = (ImageView) findViewById(R.id.right_image);
        titleView = (TextView) findViewById(R.id.title);
        rightTextView = (TextView) findViewById(R.id.right_text);
        leftTextView = (TextView) findViewById(R.id.left_text);
        tvUnReadNum = (TextView) findViewById(R.id.tv_unread_num);
        titleLayout = (RelativeLayout) findViewById(R.id.rl_title_bar);
    }

    /**
     * 替换之前的右布局
     *
     * @param rightView
     * @return
     */
    @Override
    public MyTitleBar addRightView(View rightView) {
        rightLayout.setVisibility(VISIBLE);
        rightLayout.removeAllViews();
        rightLayout.addView(rightView);
        return this;
    }

    /**
     * 替换之前的右布局
     *
     * @return
     */
    @Override
    public View addRightView(int layoutResID) {
        View view = LayoutInflater.from(getContext()).inflate(layoutResID, this, false);
        addRightView(view);
        return view;
    }

    /**
     * 设置左边Icon
     *
     * @param resId
     */
    @Override
    public MyTitleBar setLeftImageResource(int resId) {
        leftLayout.setVisibility(VISIBLE);
        leftTextView.setVisibility(GONE);
        leftImage.setVisibility(VISIBLE);
        leftImage.setImageResource(resId);
        return this;
    }

    /**
     * 设置右边Icon
     *
     * @param resId
     */
    @Override
    public MyTitleBar setRightImageResource(int resId) {
        rightLayout.setVisibility(VISIBLE);
        rightTextView.setVisibility(GONE);
        rightImage.setVisibility(VISIBLE);
        rightImage.setImageResource(resId);
        return this;
    }


    /**
     * 设置左边文本
     *
     * @param leftText
     */
    @Override
    public MyTitleBar setLeftText(String leftText) {
        leftLayout.setVisibility(VISIBLE);
        leftTextView.setVisibility(VISIBLE);
        leftImage.setVisibility(GONE);
        leftTextView.setText(leftText);
        return this;
    }

    /**
     * 设置右边文本
     *
     * @param rightText
     */
    @Override
    public MyTitleBar setRightText(String rightText) {
        rightLayout.setVisibility(VISIBLE);
        rightTextView.setVisibility(VISIBLE);
        rightImage.setVisibility(GONE);
        rightTextView.setText(rightText);
        return this;
    }

    /**
     * 设置左边监听
     *
     * @param listener
     */
    @Override
    public MyTitleBar setLeftLayoutClickListener(OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
        return this;
    }

    @Override
    public MyTitleBar setTitleClickListener(OnClickListener listener) {
        titleView.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置右边监听
     *
     * @param listener
     */
    @Override
    public MyTitleBar setRightLayoutClickListener(OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置左边能见度
     *
     * @param visibility
     */
    @Override
    public MyTitleBar setLeftLayoutVisibility(int visibility) {
        leftLayout.setVisibility(visibility);
        return this;
    }

    /**
     * 设置右边能见度
     *
     * @param visibility
     */
    @Override
    public MyTitleBar setRightLayoutVisibility(int visibility) {
        rightLayout.setVisibility(visibility);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public MyTitleBar setTitleText(String title) {
        titleView.setText(title);
        return this;
    }

    /**
     * 设置标题为左边形式
     */
    @Override
    public MyTitleBar setLeftTitleStyle() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.left_layout);

        titleView.setLayoutParams(layoutParams);

        return this;
    }


    /**
     * 设置标题
     *
     * @param leftVisibility 左边是否可见（true为可见）
     * @param title          标题名字
     * @param rightText      右边文字（为null则不显示）
     */
    @Override
    public void setTitle(boolean leftVisibility, String title,
                         String rightText) {

        setTitle(leftVisibility, title);
        setRightText(rightText);
    }

    /**
     * 设置标题
     *
     * @param leftVisibility  左边是否可见（true为可见）
     * @param title           标题名字
     * @param rightDrawableId 右边图片（为0则不显示）
     */
    @Override
    public void setTitle(boolean leftVisibility, String title,
                         int rightDrawableId) {

        setTitle(leftVisibility, title);
        setRightImageResource(rightDrawableId);
    }

    @Override
    public void setTitle(boolean leftVisibility, String title) {
        setLeftLayoutVisibility(leftVisibility ? VISIBLE : INVISIBLE);
        setTitleText(title);
        setRightLayoutVisibility(INVISIBLE);
    }

    @Override
    public void setTitle(String leftText, String title) {
        setTitle(leftText, title, "");
    }

    @Override
    public void setTitle(String leftText, String title, String rightText) {
        setLeftText(leftText);
        setTitleText(title);
        setRightText(rightText);
    }

    @Override
    public void setTitle(String leftText, String title, int rightDrawableId) {
        setLeftText(leftText);
        setTitleText(title);
        setRightImageResource(rightDrawableId);
    }

    @Override
    public void setTitle(int leftDrawableId, String title) {
        setTitle(leftDrawableId, title, "");
    }

    @Override
    public void setTitle(int leftDrawableId, String title, String rightText) {
        setLeftImageResource(leftDrawableId);
        setTitleText(title);
        setRightText(rightText);
    }

    @Override
    public void setTitle(int leftDrawableId, String title, int rightDrawableId) {
        setLeftImageResource(leftDrawableId);
        setTitleText(title);
        setRightImageResource(rightDrawableId);
    }

    @Override
    public void setTitle(String title) {
        setTitleText(title);
        setLeftLayoutVisibility(INVISIBLE);
        setRightLayoutVisibility(INVISIBLE);
    }

    @Override
    public void setUnReadNum(int num){
        UnReadUtil.setUnReadText(tvUnReadNum, num);
    }
    /**
     * 设置背景颜色
     *
     * @param color
     */
    @Override
    public void setTitleBarBackgroundColor(int color) {
        llTitleBar.setBackgroundColor(color);
    }


    @Override
    public void setBackgroundColor(int color) {
        llTitleBar.setBackgroundColor(color);
    }

    @Override
    public LinearLayout getLeftLayout() {
        return leftLayout;
    }

    @Override
    public ImageView getLeftImage() {
        return leftImage;
    }

    @Override
    public TextView getLeftTextView() {
        return leftTextView;
    }

    @Override
    public LinearLayout getRightLayout() {
        return rightLayout;
    }

    @Override
    public ImageView getRightImage() {
        return rightImage;
    }

    @Override
    public TextView getRightTextView() {
        return rightTextView;
    }

    @Override
    public TextView getTitleView() {
        return titleView;
    }

    @Override
    public RelativeLayout getTitleLayout() {
        return titleLayout;
    }
}
