package com.jm.xpproject.ui.main.util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.xpproject.config.change.UIConfig;
import com.jm.core.common.tools.viewpager.ViewPagerFgmUtil;

import java.util.ArrayList;

/**
 * 基于小跑的第二种主页的工具类
 *
 * @author jinXiong.Xie
 */

public class XPMain2Util extends XPMainUtil {

    private ArrayList<Fragment> fgmList = new ArrayList<>();
    private int initIndex = 0;

    private int[] tabBar_select = {UIConfig.GUIDE_SELECT_ICON[0], UIConfig.GUIDE_SELECT_ICON[1], UIConfig.GUIDE_SELECT_ICON[2], UIConfig.GUIDE_SELECT_ICON[3]};
    private String[] tabBar_name = {UIConfig.GUIDE_NAME[0], UIConfig.GUIDE_NAME[1], UIConfig.GUIDE_NAME[2], UIConfig.GUIDE_NAME[3]};
    private int[] tabBar_normal = {UIConfig.GUIDE_NORMAL_ICON[0], UIConfig.GUIDE_NORMAL_ICON[1], UIConfig.GUIDE_NORMAL_ICON[2], UIConfig.GUIDE_NORMAL_ICON[3]};
    private int[] tabBar_textColor = {UIConfig.GUIDE_NORMAL_TEXT_COLOR, UIConfig.GUIDE_SELECT_TEXT_COLOR};

    private ViewPager viewPager;

    ImageView ivOne;
    TextView tvOne;
    ImageView ivTwo;
    TextView tvTwo;
    ImageView ivThree;
    TextView tvThree;
    ImageView ivFour;
    TextView tvFour;

    private ViewPagerFgmUtil viewPagerFgmUtil;

    public XPMain2Util(Context context, ViewPager viewPager, ImageView ivOne, TextView tvOne, ImageView ivTwo, TextView tvTwo, ImageView ivThree, TextView tvThree, ImageView ivFour, TextView tvFour) {
        super(context);
        this.viewPager = viewPager;
        this.ivOne = ivOne;
        this.tvOne = tvOne;
        this.ivTwo = ivTwo;
        this.tvTwo = tvTwo;
        this.ivThree = ivThree;
        this.tvThree = tvThree;
        this.ivFour = ivFour;
        this.tvFour = tvFour;

        tvOne.setText(tabBar_name[0]);
        tvTwo.setText(tabBar_name[1]);
        tvThree.setText(tabBar_name[2]);
        tvFour.setText(tabBar_name[3]);

        viewPagerFgmUtil = new ViewPagerFgmUtil(context);
    }

    /**
     * 初始化ViewPager
     */
    public void initViewPager() {

        Class<Fragment>[] classes = new Class[4];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = UIConfig.GUIDE_FGM[i];
        }
        viewPagerFgmUtil.init(viewPager, classes, null, new ViewPagerFgmUtil.TabBarCallBack() {
            @Override
            public void changeTabBar(int position) {

                initIndex = position;

                XPMain2Util.this.changeTabBar(position);
            }
        });
    }

    /**
     * 选择导航栏
     *
     * @param index
     */
    public void changeTabBar(int index) {
        int normal = getContext().getResources().getColor(tabBar_textColor[0]);
        int select = getContext().getResources().getColor(tabBar_textColor[1]);
        ivOne.setImageResource(tabBar_normal[0]);
        ivTwo.setImageResource(tabBar_normal[1]);
        ivThree.setImageResource(tabBar_normal[2]);
        ivFour.setImageResource(tabBar_normal[3]);

        tvOne.setTextColor(normal);
        tvTwo.setTextColor(normal);
        tvThree.setTextColor(normal);
        tvFour.setTextColor(normal);
        switch (index) {
            case 0:
                ivOne.setImageResource(tabBar_select[0]);
                tvOne.setTextColor(select);
                break;
            case 1:
                ivTwo.setImageResource(tabBar_select[1]);
                tvTwo.setTextColor(select);
                break;
            case 2:
                ivThree.setImageResource(tabBar_select[2]);
                tvThree.setTextColor(select);
                break;
            case 3:
                ivFour.setImageResource(tabBar_select[3]);
                tvFour.setTextColor(select);
                break;
            default:
                break;
        }
    }


    /**
     * 更改viewpager下标
     *
     * @param position
     */
    public void changeViewPagerIndex(int position) {
        if (initIndex == position) {
            return;
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(position, false);
        }
    }
}
