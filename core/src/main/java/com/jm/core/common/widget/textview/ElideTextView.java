package com.jm.core.common.widget.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.jm.core.common.tools.base.PixelsTools;

/**
 * @author cgq
 *         文本对角斜线删除效果
 */

public class ElideTextView extends AppCompatTextView {

    private Context context;

    public ElideTextView(Context context) {
        super(context);
        this.context = context;
    }

    public ElideTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ElideTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(PixelsTools.dip2Px(context, 1));
        canvas.drawLine(0, PixelsTools.dip2Px(context, 5), getMeasuredWidth(),
                getMeasuredHeight() - PixelsTools.dip2Px(context, 5), paint);
    }
}
