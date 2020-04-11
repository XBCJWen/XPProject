package com.jm.core.common.widget.scrollview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/7/16.
 * 没有滚动的GridView
 */

public class NoScrollRecyclerView extends RecyclerView {

    public NoScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public NoScrollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollRecyclerView(Context context) {
        super(context);
    }

    /**
     * 设置不滚动
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}