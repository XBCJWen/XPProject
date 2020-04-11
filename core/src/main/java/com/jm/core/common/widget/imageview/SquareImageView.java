package com.jm.core.common.widget.imageview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 宽高相同的ImageView
 */

public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context) {

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