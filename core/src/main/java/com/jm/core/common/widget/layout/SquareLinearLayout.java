package com.jm.core.common.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 宽高相同的LinearLayout
 */

public class SquareLinearLayout extends LinearLayout {

    public SquareLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLinearLayout(Context context) {

        super(context);

    }

    @Override

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),

                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();

        int childHeightSize = getMeasuredHeight();

// 高度和宽度一样

        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(

                childWidthSize, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}