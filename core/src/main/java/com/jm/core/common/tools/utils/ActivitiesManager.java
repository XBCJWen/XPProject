package com.jm.core.common.tools.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * 自定义的activity栈
 *
 * @author jinXiong.Xie
 */
public class ActivitiesManager {

    private static LinkedList<Activity> activityStack;

    /**
     * 单例
     */
    private static ActivitiesManager instance;

    private ActivitiesManager() {
    }

    public static ActivitiesManager getInstance() {
        if (instance == null) {
            instance = new ActivitiesManager();
        }
        return instance;
    }

    /**
     * 压入栈顶
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<Activity>();
        }
        activityStack.push(activity);
    }

    /**
     * 移除栈顶activity
     */
    public void popActivity() {
        Activity activity = activityStack.pop();
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的activity
     */
    public void popOneActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取当前栈顶activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.peek();
        return activity;
    }

    /**
     * 除了某个，其余全部删除
     */
    public void popAllActivityExceptOne(Class clazz) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity == null) {
                activityStack.remove(activity);
                continue;
            }
            if (activity.getClass().equals(clazz)) {
                continue;
            }
            popOneActivity(activity);
        }
    }

    /**
     * 移除全部Activity
     */
    public void popAllActivity() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (activity == null) {
                activityStack.remove(activity);
                continue;
            }
            popOneActivity(activity);
        }
    }

    /**
     * 退出app,清除所有activity
     */
    public void exit() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popOneActivity(activity);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
