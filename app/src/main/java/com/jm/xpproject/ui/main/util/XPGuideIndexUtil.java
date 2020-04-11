package com.jm.xpproject.ui.main.util;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.xpproject.R;
import com.jm.xpproject.bean.GuideIndexBean;
import com.jm.xpproject.config.change.UIConfig;
import com.jm.core.common.tools.viewpager.ViewPagerFgmUtil;

/**
 * 导航栏工具类
 *
 * @author jinXiong.Xie
 */

public class XPGuideIndexUtil {

    /**
     * 导航栏功能个数
     */
    private final int guideLength = UIConfig.GUIDE_FGM.length;
    /**
     * 导航栏的所需数据
     */
    private GuideIndexBean bean;
    private ViewPagerFgmUtil viewPagerFgmUtil;

    public XPGuideIndexUtil(GuideIndexBean bean) {
        this.bean = bean;
        viewPagerFgmUtil = new ViewPagerFgmUtil(bean.getActivity());
    }


    /**
     * 初始化导航栏布局
     */
    public void initGuide() {
        if (!checkGuideConfig()) {
            return;
        }

        initGuideLayout();

        initViewPager();

    }


    private void initViewPager() {

        initFragment();
        viewPagerFgmUtil.init(bean.getViewPager(), bean.getFgmList(), bean.getLlGuide(), new ViewPagerFgmUtil.TabBarCallBack() {
            @Override
            public void changeTabBar(int position) {
                alterGuideLayoutStyle(position);
            }
        });
    }

    private void initFragment() {
        Fragment[] fgmList = bean.getFgmList();
        if (fgmList == null || fgmList.length != guideLength) {
            fgmList = new Fragment[guideLength];
        }
        for (int index = 0; index < guideLength; index++) {
            if (fgmList[index] == null) {
                try {
                    fgmList[index] = (Fragment) UIConfig.GUIDE_FGM[index].newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initGuideLayout() {
        //初始化UI
        for (int index = 0; index < guideLength; index++) {
            View view = createGuideIndexLayout();
            bindGuideLayoutData(view, index);
            bean.getLlGuideLayout().addView(view);
        }
    }

    /**
     * 检测导航栏的配置
     *
     * @return
     */
    private boolean checkGuideConfig() {
        if (guideLength <= 0) {
            return false;
        }
        if (guideLength == UIConfig.GUIDE_NAME.length &&
                guideLength == UIConfig.GUIDE_NORMAL_ICON.length &&
                guideLength == UIConfig.GUIDE_SELECT_ICON.length) {
            return true;
        }
        return false;
    }

    /**
     * 创建导航栏的功能布局
     *
     * @return
     */
    private View createGuideIndexLayout() {
        View view = LayoutInflater.from(bean.getActivity()).inflate(R.layout.item_main_guide_index, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        view.setLayoutParams(layoutParams);
        return view;
    }


    /**
     * 绑定导航栏的功能布局
     *
     * @param view
     */
    private void bindGuideLayoutData(View view, int index) {
        bean.getLlGuide()[index] = (LinearLayout) view.findViewById(R.id.ll_guide);
        bean.getTvGuideName()[index] = (TextView) view.findViewById(R.id.tv_name);
        bean.getImgGuideIcon()[index] = (ImageView) view.findViewById(R.id.img_guide);
        bean.getTvGuideUnReadNum()[index] = (TextView) view.findViewById(R.id.tv_unread_num);

        bean.getTvGuideName()[index].setText(UIConfig.GUIDE_NAME[index]);
    }


    /**
     * 更改导航栏布局效果
     *
     * @param index
     */
    private void alterGuideLayoutStyle(int index) {
        if (index >= guideLength) {
            return;
        }

        for (int i = 0; i < guideLength; i++) {
            bean.getImgGuideIcon()[i].setImageResource(UIConfig.GUIDE_NORMAL_ICON[i]);
            bean.getTvGuideName()[i].setTextColor(bean.getActivity().getResources().getColor(UIConfig.GUIDE_NORMAL_TEXT_COLOR));
        }

        bean.getImgGuideIcon()[index].setImageResource(UIConfig.GUIDE_SELECT_ICON[index]);
        bean.getTvGuideName()[index].setTextColor(bean.getActivity().getResources().getColor(UIConfig.GUIDE_SELECT_TEXT_COLOR));
    }

}
