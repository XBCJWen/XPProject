package com.jm.core.common.widget.scrollview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.jm.core.common.tools.base.PixelsTools;

/**
 * 透明的标题到不透明
 *
 * @author jinXiong.Xie
 */

public class AlphaTitleScrollView extends ScrollView {
    public static final String TAG = "AlphaTitleScrollView";
    /**
     * 滑动最短距离，避免一滑动就更改背景
     */
    private int mSlop;
    /**
     * 作为标题的View
     */
    private View titleView;
    /**
     * 作为相对滑动的View（可滑动距离为heightView高度-titleView高度）
     */
    private View heightView;

    //滑动后显示的背景颜色
    /**
     * 颜色值R
     */
    private int r;
    /**
     * 颜色值G
     */
    private int g;
    /**
     * 颜色值B
     */
    private int b;
    /**
     * 可滑动高度
     */
    private int scrollHeight = Integer.MIN_VALUE;

    public AlphaTitleScrollView(Context context) {
        this(context, null);
    }

    public AlphaTitleScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public AlphaTitleScrollView(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//        mSlop = 10;
        Log.i(TAG, mSlop + "");
    }

    /**
     * 设置基于相对滑动的View进行滑动显示
     *
     * @param titleView  头部布局
     * @param heightView 相对滑动的View
     */
    public void setTitleAndView(View titleView, View heightView, int r, int g, int b) {
        this.heightView = heightView;

        initView(titleView, r, g, b);
    }

    /**
     * 设置基于可滑动高度进行滑动显示
     *
     * @param titleView    头部布局
     * @param scrollHeight 可滑动高度
     */
    public void setTitleAndScrollHeight(View titleView, int scrollHeight, int r, int g, int b) {

        this.scrollHeight = PixelsTools.dip2Px(titleView.getContext(), scrollHeight);

        initView(titleView, r, g, b);

    }

    private void initView(View titleView, int r, int g, int b) {
        this.titleView = titleView;

        this.r = r;
        this.g = g;
        this.b = b;

        //将背景设置为透明
        titleView.setBackgroundColor(Color.argb(0, r, g, b));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        float headHeight = scrollHeight == Integer.MIN_VALUE ? heightView.getMeasuredHeight()
                - titleView.getMeasuredHeight() : scrollHeight;
        int alpha = (int) (((float) t / headHeight) * 255);
        if (alpha >= 255) {
            alpha = 255;

        }
        if (alpha <= mSlop) {
            alpha = 0;
        }
        titleView.setBackgroundColor(Color.argb(alpha, r, g, b));

        super.onScrollChanged(l, t, oldl, oldt);
    }
}