package com.jm.core.common.tools.viewpager;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jm.core.common.tools.log.LogUtil;
import com.jm.core.common.widget.adapter.viewpageradapter.GuideViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * ViewPager、View的工具类（不可循环滚动）
 *
 * @author jinXiong.Xie
 */

public class ViewPagerViewUtil {

    private ViewPager viewPager;
    private List<View> views;
    /**
     * ViewPager适配器
     */
    private GuideViewPagerAdapter guideViewPagerAdapter;
    /**
     * 滚动时间
     */
    private int scrollTime = 3000;

    private PagerClickListener pagerClickListener;

    private PageSelectedListener pageSelectedListener;

    private LastPageLeftSlideListener lastPageLeftSlideListener;

    private boolean misScrolled;

    public ViewPagerViewUtil(ViewPager viewPager, List<View> views) {
        this.viewPager = viewPager;
        this.views = views;
    }

    public void setPagerClickListener(PagerClickListener pagerClickListener) {
        this.pagerClickListener = pagerClickListener;
    }

    public void setPageSelectedListener(PageSelectedListener pageSelectedListener) {
        this.pageSelectedListener = pageSelectedListener;
    }

    public void setLastPageLeftSlideListener(LastPageLeftSlideListener lastPageLeftSlideListener) {
        this.lastPageLeftSlideListener = lastPageLeftSlideListener;
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
                        pagerClickListener.onClick(finalI);
                    }
                }
            });
        }

        handlerCarouselException();

        //初始化
        guideViewPagerAdapter = new GuideViewPagerAdapter(views);

        viewPager.setAdapter(guideViewPagerAdapter);

        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
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
                pageSelectedListener.onPageSelected(position % views.size());
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
            if (lastPageLeftSlideListener == null) {
                return;
            }

            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    misScrolled = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    misScrolled = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !misScrolled) {
                        lastPageLeftSlideListener.leftSlide();
                    }
                    misScrolled = true;
                    break;
                default:
            }

        }
    }

    /**
     * 最后一页左滑监听
     */
    public interface LastPageLeftSlideListener {

        /**
         * 左滑
         */
        void leftSlide();

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
     * 选择图片页
     *
     * @param index
     */
    public void setCurrentItem(int index) {
        if (viewPager!=null){
            viewPager.setCurrentItem(index);
        }
    }

}
