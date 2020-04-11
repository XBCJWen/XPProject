package com.jm.xpproject.bean;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 导航栏的布局所需内容
 *
 * @author jinXiong.Xie
 */

public class GuideIndexBean {

    /**
     * 导航栏的功能名称
     */
    private TextView[] tvGuideName;
    /**
     * 导航栏的功能图标
     */
    private ImageView[] imgGuideIcon;
    /**
     * 导航栏的功能布局
     */
    private LinearLayout[] llGuide;
    /**
     * 导航栏的未读数
     */
    private TextView[] tvGuideUnReadNum;
    /**
     * 导航栏布局
     */
    private LinearLayout llGuideLayout;
    /**
     * 导航
     */
    private ViewPager viewPager;
    private FragmentActivity activity;
    private Fragment[] fgmList;

    public GuideIndexBean() {
    }

    public GuideIndexBean(FragmentActivity activity, TextView[] tvGuideName, ImageView[] imgGuideIcon, LinearLayout[] llGuide, TextView[] tvGuideUnReadNum, LinearLayout llGuideLayout,ViewPager viewPager, Fragment[] fgmList) {
        this.activity = activity;
        this.tvGuideName = tvGuideName;
        this.imgGuideIcon = imgGuideIcon;
        this.llGuide = llGuide;
        this.tvGuideUnReadNum = tvGuideUnReadNum;
        this.llGuideLayout = llGuideLayout;
        this.viewPager = viewPager;
        this.fgmList = fgmList;
    }

    public FragmentActivity getActivity() {
        return activity;
    }

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public TextView[] getTvGuideName() {
        return tvGuideName;
    }

    public void setTvGuideName(TextView[] tvGuideName) {
        this.tvGuideName = tvGuideName;
    }

    public ImageView[] getImgGuideIcon() {
        return imgGuideIcon;
    }

    public void setImgGuideIcon(ImageView[] imgGuideIcon) {
        this.imgGuideIcon = imgGuideIcon;
    }

    public LinearLayout[] getLlGuide() {
        return llGuide;
    }

    public void setLlGuide(LinearLayout[] llGuide) {
        this.llGuide = llGuide;
    }

    public TextView[] getTvGuideUnReadNum() {
        return tvGuideUnReadNum;
    }

    public void setTvGuideUnReadNum(TextView[] tvGuideUnReadNum) {
        this.tvGuideUnReadNum = tvGuideUnReadNum;
    }

    public LinearLayout getLlGuideLayout() {
        return llGuideLayout;
    }

    public void setLlGuideLayout(LinearLayout llGuideLayout) {
        this.llGuideLayout = llGuideLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public Fragment[] getFgmList() {
        return fgmList;
    }

    public void setFgmList(Fragment[] fgmList) {
        this.fgmList = fgmList;
    }
}
