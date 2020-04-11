package com.jm.core.common.tools.viewpager;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jm.core.common.tools.log.LogUtil;
import com.jm.core.common.widget.adapter.viewpageradapter.InfinitePagerAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ViewPager、View的工具类
 *
 * @author jinXiong.Xie
 * @deprecated 使用banner   https://github.com/youth5201314/banner
 */

public class ViewPagerViewUtil2 {

    /**
     * 标识滚动
     */
    private final int SCROLL_VIEW = 0;

    private ViewPager viewPager;
    private List<View> views;
    /**
     * ViewPager适配器
     */
    private InfinitePagerAdapter myViewPagerAdapter;
    /**
     * 滚动时间
     */
    private int scrollTime = 3000;

    private PagerClickListener pagerClickListener;

    private PageSelectedListener pageSelectedListener;

    /**
     * views的原长度
     */
    private int viewsLength;

    /**
     * views的原长度必须大于等于3，若原长度为1，则重新生成三个一样的，若原长度为2，则重新生成两对一样的，
     * 并使用ViewPagerViewUtil(ViewPager viewPager, List<View> views, int length)
     *
     * @param viewPager
     * @param views
     */
    public ViewPagerViewUtil2(ViewPager viewPager, List<View> views) {
        this(viewPager, views, views.size());
    }

    /**
     * views的原长度小于3的时候使用，length为原长度的值，若原长度为1，则重新生成三个一样的，若原长度为2，则重新生成两对一样的
     *
     * @param viewPager
     * @param views
     * @param length    views的原长度
     */
    public ViewPagerViewUtil2(ViewPager viewPager, List<View> views, int length) {
        this.viewPager = viewPager;
        this.views = views;
        viewsLength = length;
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SCROLL_VIEW:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    handler.sendEmptyMessageDelayed(SCROLL_VIEW, scrollTime);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置滚动时间
     *
     * @param scrollTime
     */
    public void setScrollTime(int scrollTime) {
        this.scrollTime = scrollTime;
    }

    public void setPagerClickListener(PagerClickListener pagerClickListener) {
        this.pagerClickListener = pagerClickListener;
    }

    public void setPageSelectedListener(PageSelectedListener pageSelectedListener) {
        this.pageSelectedListener = pageSelectedListener;
    }

    public void initViewPager() {

        //判空
        if (viewPager == null || views == null) {
            LogUtil.e("viewPager == null || views == null");
            return;
        }


        //图片监听
        for (int i = 0; i < views.size(); i++) {
            final int finalI = i;
            views.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pagerClickListener != null) {
                        if (viewsLength == 1) {
                            pagerClickListener.onClick(0);
                            return;
                        }
                        if (viewsLength == 2 && finalI > 1) {
                            pagerClickListener.onClick(finalI == 2 ? 0 : 1);
                            return;
                        }
                        pagerClickListener.onClick(finalI);
                    }
                }
            });
        }

        handlerCarouselException();

        //初始化
        myViewPagerAdapter = new InfinitePagerAdapter(views);

        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.setCurrentItem((Integer.MAX_VALUE >> 1) - (Integer.MAX_VALUE >> 1) % views.size());

        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //开启延迟滚动
        startScrollDelayed(scrollTime);
        //开启第一页的选择回调
        if (pageSelectedListener != null) {
            pageSelectedListener.onPageSelected(0);
        }

    }


    /**
     * 处理无限轮播图出现页面卡顿和崩溃的问题
     */
    private void handlerCarouselException() {
        Field mFirstLayout = null;
        try {
            mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            try {
                mFirstLayout.set(viewPager, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * viewPager滚动监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (pageSelectedListener != null) {
                pageSelectedListener.onPageSelected(position % viewsLength);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    //用户正在滑动，暂停轮播
                    stopScroll();
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    //滑动结束，继续轮播
                    startScrollDelayed(scrollTime);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        stopScroll();
        handler.sendEmptyMessage(SCROLL_VIEW);
    }

    /**
     * 延迟开始滚动
     */
    public void startScrollDelayed(int scrollTime) {
        stopScroll();
        handler.sendEmptyMessageDelayed(SCROLL_VIEW, scrollTime);
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        if (handler.hasMessages(SCROLL_VIEW)) {
            handler.removeMessages(SCROLL_VIEW);
        }
    }

    /**
     * ViewPager点击监听
     */
    public interface PagerClickListener {
        /**
         * 点击
         *
         * @param position
         */
        void onClick(int position);
    }

    /**
     * ViewPager选择监听
     */
    public interface PageSelectedListener {
        /**
         * 页面选择
         *
         * @param position
         */
        void onPageSelected(int position);
    }

    /**
     * 设置ViewPager默认显示页
     *
     * @param position
     */
    public void setCurrentItem(int position) {
        if (viewPager != null) {
            viewPager.setCurrentItem((Integer.MAX_VALUE >> 1) - (Integer.MAX_VALUE >> 1) % views.size() + position);
        }
    }

}
