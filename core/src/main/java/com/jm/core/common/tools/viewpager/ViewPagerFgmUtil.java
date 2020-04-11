package com.jm.core.common.tools.viewpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jm.core.common.tools.log.LogUtil;
import com.jm.core.common.widget.adapter.viewpageradapter.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager、Fragment和View连接的工具类
 *
 * @author jinXiong.Xie
 */

public class ViewPagerFgmUtil {

    private ViewPager viewPager;
    private Fragment[] fragments;
    private View[] tabBarViews;
    private Context context;
    /**
     * 储存Fragment
     */
    private ArrayList<Fragment> fgmList = new ArrayList<>();

    private Fragment fragment;

    public ViewPagerFgmUtil(Fragment fragment) {
        this.context = fragment.getContext();
        this.fragment = fragment;
    }

    /**
     * 父页面是否为Fragment
     */
    public boolean isParentIsFragment() {
        return fragment != null;
    }


    /**
     * ViewPager标识
     */
    private int clickIndex = 0;

    private TabBarCallBack tabBarCallBack;

    public ViewPagerFgmUtil(Context context) {
        this.context = context;
    }

    public void setClickIndex(int clickIndex) {
        this.clickIndex = clickIndex;
    }

    /**
     * 初始化数据
     *
     * @param viewPager
     * @param fragments
     * @param tabBarViews
     */
    private void initData(ViewPager viewPager, Fragment[] fragments, View[] tabBarViews, TabBarCallBack tabBarCallBack) {
        this.viewPager = viewPager;
        this.fragments = fragments;
        this.tabBarViews = tabBarViews;
        this.tabBarCallBack = tabBarCallBack;
    }

    /**
     * 检测参数
     */
    private boolean checkParameter() {
        if (viewPager == null || fragments == null) {
            LogUtil.e("参数有空");
            return false;
        }
//        if (fragments.length != tabBarViews.length) {
//            LogUtil.e("Fragment数与View数不对应");
//            return false;
//        }
        return true;
    }

    public void init(ViewPager viewPager, List<Class<? extends Fragment>> fragments, TabBarCallBack tabBarCallBack) {
        init(viewPager, fragments, null, tabBarCallBack);
    }

    public void init(ViewPager viewPager, Class<? extends Fragment>[] fragments, TabBarCallBack tabBarCallBack) {
        init(viewPager, fragments, null, tabBarCallBack);
    }

    /**
     * 初始化
     *
     * @param viewPager
     * @param fragments      ViewPager中的Fragment
     * @param tabBarViews    点击切换ViewPager的View
     * @param tabBarCallBack 提示更改TabBarView的样式
     */
    public void init(ViewPager viewPager, List<Class<? extends Fragment>> fragments, List<View> tabBarViews, TabBarCallBack tabBarCallBack) {
        Class<Fragment>[] classes = null;
        if (fragments != null) {
            classes = new Class[fragments.size()];
            fragments.toArray(classes);
        }
        View[] views = null;
        if (tabBarViews != null) {
            views = new View[tabBarViews.size()];
            tabBarViews.toArray(views);
        }
        init(viewPager, classes, views, tabBarCallBack);

    }

    /**
     * 初始化
     *
     * @param viewPager
     * @param fragments      ViewPager中的Fragment
     * @param tabBarViews    点击切换ViewPager的View
     * @param tabBarCallBack 提示更改TabBarView的样式
     */
    public void init(ViewPager viewPager, Class<? extends Fragment>[] fragments, View[] tabBarViews, TabBarCallBack tabBarCallBack) {
        //生成Fragment
        Fragment[] fragment = new Fragment[fragments.length];
        for (int i = fragment.length - 1; i >= 0; i--) {
            try {
                fragment[i] = fragments[i].newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        init(viewPager, fragment, tabBarViews, tabBarCallBack);

    }

    public void init(ViewPager viewPager, Fragment[] fragments, View[] tabBarViews, TabBarCallBack tabBarCallBack) {
        initData(viewPager, fragments, tabBarViews, tabBarCallBack);

        if (!checkParameter()) {
            return;
        }

        for (Fragment fragment : fragments) {
            fgmList.add(fragment);
        }

        //配置ViewPager
        FragmentManager fragmentManager = null;
        if (isParentIsFragment()) {
            fragmentManager = fragment.getChildFragmentManager();
        } else {
            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        }
        viewPager.setAdapter(new BaseFragmentPagerAdapter(
                fragmentManager, fgmList));

        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        //view监听
        if (tabBarViews != null) {
            for (int i = 0; i < tabBarViews.length; i++) {
                if (tabBarViews[i] != null) {
                    tabBarViews[i].setOnClickListener(new ViewClickListener(i));
                }
            }
        }

        if (tabBarCallBack != null) {
            tabBarCallBack.changeTabBar(clickIndex);
        }

        if (clickIndex != 0) {
            viewPager.setCurrentItem(clickIndex);
        }

    }

    public void init2(ViewPager viewPager, List<Fragment> fragments, List<View> tabBarViews, TabBarCallBack tabBarCallBack) {
        Class<Fragment>[] classes = null;
        if (fragments != null) {
            classes = new Class[fragments.size()];
            fragments.toArray(classes);
        }
        View[] views = null;
        if (tabBarViews != null) {
            views = new View[tabBarViews.size()];
            tabBarViews.toArray(views);
        }
        init(viewPager, classes, views, tabBarCallBack);

    }

    /**
     * ViewPager相应的点击View监听
     */
    private class ViewClickListener implements View.OnClickListener {

        private int position;

        public ViewClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (clickIndex == position) {
                return;
            }
            clickIndex = position;

            changeViewPagerIndex(position);

        }
    }


    /**
     * ViewPager监听
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            //arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
        }

        @Override
        public void onPageSelected(int position) {
            clickIndex = position;

            if (tabBarCallBack != null) {
                tabBarCallBack.changeTabBar(position);
            }
        }
    }

    /**
     * TabBar样式改变
     */
    public interface TabBarCallBack {
        /**
         * 更改TabBar样式
         *
         * @param position
         */
        void changeTabBar(int position);
    }

    /**
     * 更改viewpager下标
     *
     * @param position
     */
    private void changeViewPagerIndex(int position) {
        if (viewPager != null) {
            viewPager.setCurrentItem(position, false);
        }
    }

}
