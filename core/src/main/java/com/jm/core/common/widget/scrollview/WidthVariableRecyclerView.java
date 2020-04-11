package com.jm.core.common.widget.scrollview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * 宽度可变的水平滚动WidthVariableRecyclerView，这里最大宽度设置为屏幕的4/5
 */

public class WidthVariableRecyclerView extends RecyclerView {


    private Context context;
    private int maxWidth;

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public WidthVariableRecyclerView(Context context) {
        super(context);
        this.context = context;
    }

    public WidthVariableRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }

    public WidthVariableRecyclerView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        this.context = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //设置默认最大宽度
        if (maxWidth == 0) {
            //获取屏幕的最大宽度
            DisplayMetrics metric = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;
            maxWidth = 4 * width / 5;
        }

        if (widthMode == MeasureSpec.AT_MOST && widthSize > maxWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, widthMode);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}