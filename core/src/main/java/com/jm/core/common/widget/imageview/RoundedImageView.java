package com.jm.core.common.widget.imageview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 圆角图片
 */

public class RoundedImageView extends AppCompatImageView {

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {8.0f, 8.0f, 8.0f, 8.0f, 0.0f, 0.0f, 0.0f, 0.0f,};

    public RoundedImageView(Context context) {
        super(context);
        initRids();
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRids();
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRids();
    }

    public void setRids(float topLeftXRadius, float topLeftYRadius,
                        float topRightXRadius, float topRightYRadius,
                        float bottomLeftXRadius, float bottomLeftYRadius,
                        float bottomRightXRadius, float bottomRightYRadius) {

        rids[0] = topLeftXRadius;
        rids[1] = topLeftYRadius;
        rids[2] = topRightXRadius;
        rids[3] = topRightYRadius;
        rids[4] = bottomLeftXRadius;
        rids[5] = bottomLeftYRadius;
        rids[6] = bottomRightXRadius;
        rids[7] = bottomRightYRadius;
    }

    /**
     * 依次从左到右,从上到下，从x到y
     *
     * @param xyRadius
     */
    public void setArrayRids(float... xyRadius) {

        int size = (xyRadius != null) ? xyRadius.length : 0;
        for (int i = 0; i < size; i++) {
            rids[i] = xyRadius[i];
        }

    }

    private void initRids() {
        for (int index = 0; index < rids.length; index++) {
            rids[index] = Resources.getSystem().getDisplayMetrics().density * rids[index];
        }
    }


    /**
     * 画图
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}