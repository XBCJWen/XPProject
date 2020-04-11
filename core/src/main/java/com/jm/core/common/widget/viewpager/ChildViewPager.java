package com.jm.core.common.widget.viewpager;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 用于ViewPager嵌套
 */

public class ChildViewPager extends ViewPager {
  PointF downP = new PointF();
  PointF curP = new PointF();
//  OnSingleTouchListener onSingleTouchListener;

  public ChildViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ChildViewPager(Context context) {
    super(context);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent arg0) {
    // 当拦截触摸事件到达此位置的时候，返回true，
    // 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
    return true;
  }

  @Override
  public boolean onTouchEvent(MotionEvent arg0) {
    // 每次进行onTouch事件都记录当前的按下的坐标
    curP.x = arg0.getX();
    curP.y = arg0.getY();

    if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
      downP.x = arg0.getX();
      downP.y = arg0.getY();
      // 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
      getParent().requestDisallowInterceptTouchEvent(true);
    }

    if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
      getParent().requestDisallowInterceptTouchEvent(true);
    }
    return super.onTouchEvent(arg0);
  }

}