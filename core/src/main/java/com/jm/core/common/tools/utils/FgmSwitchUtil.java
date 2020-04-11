package com.jm.core.common.tools.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fgm切换工具类
 *
 * @author jinXiong.Xie
 */

public class FgmSwitchUtil {

    private Fragment mContent;
    private FragmentActivity fragmentActivity;
    private Fragment fragment;
    private int layoutId;

    public FgmSwitchUtil(FragmentActivity fragmentActivity, int layoutId) {
        this.fragmentActivity = fragmentActivity;
        this.layoutId = layoutId;
    }

    public FgmSwitchUtil(Fragment fragment, int layoutId) {
        this.fragment = fragment;
        this.layoutId = layoutId;
    }

    /**
     * 切换Fragment
     *
     * @param from
     * @param to
     */
    public void switchContentAllowingStateLoss(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = getFragmentTransaction();
            if (transaction == null) {
                return;
            }
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(layoutId, to).show(to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

    /**
     * 切换Fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = getFragmentTransaction();
            if (transaction == null) {
                return;
            }
            // 先判断是否被add过
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(layoutId, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commit();
            }
        }
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        if (transaction == null) {
            return;
        }
        transaction.replace(layoutId, fragment);
        transaction.commit();
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    public void showFragmentAllowingStateLoss(Fragment fragment) {
        FragmentTransaction transaction = getFragmentTransaction();
        if (transaction == null) {
            return;
        }
        transaction.replace(layoutId, fragment);
        transaction.commitAllowingStateLoss();
    }
    /**
     * 获取FragmentTransaction
     *
     * @return
     */
    private FragmentTransaction getFragmentTransaction() {
        FragmentManager manager = null;
        if (fragmentActivity != null) {
            manager = fragmentActivity.getSupportFragmentManager();
        }
        if (fragment != null) {
            manager = fragment.getChildFragmentManager();
        }
        if (manager == null) {
            return null;
        }
        return manager.beginTransaction();
    }

}
