package com.jm.core.common.widget.adapter.viewpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Fragment ViewPager适配器
 *
 * @author jinXiong.Xie
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentsList;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm,
                                    ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    public BaseFragmentPagerAdapter(FragmentManager fm,
                                    Fragment[] fragments) {
        super(fm);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        for (int index = 0; index < fragments.length; index++) {
            fragmentArrayList.add(fragments[index]);
        }
        this.fragmentsList = fragmentArrayList;
    }


    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    //Remove a page for the given position. The adapter is responsible for removing the view from its container
    //防止重新销毁视图
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //如果注释这行，那么不管怎么切换，page都不会被销毁
//    if (position == 0) {
//        super.destroyItem(container, position, object);

//    }
    }

}