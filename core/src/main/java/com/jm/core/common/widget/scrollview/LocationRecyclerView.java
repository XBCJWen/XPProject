package com.jm.core.common.widget.scrollview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 获取点击位置的RecyclerView
 *
 * @author jinXiong.Xie
 * @date 2018/12/11
 */
public class LocationRecyclerView extends RecyclerView {

    private double yLocation;
    private double xLocation;

    public double getYLocation() {
        return yLocation;
    }

    public double getXLocation() {
        return xLocation;
    }


    public LocationRecyclerView(Context context) {
        super(context);
    }

    public LocationRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            yLocation = e.getY();
            xLocation = e.getX();

        }
        return super.onTouchEvent(e);
    }
}
