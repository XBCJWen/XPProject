package com.jm.xpproject.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jm.core.common.tools.BaseUtil;
import com.jm.xpproject.R;


/**
 * 监听网络工具类（可监听连接网络（wifi,数据连接），断开网络）
 *
 * @author 张晓超
 */
public class MonitorNetworkUtils extends BaseUtil {
    /**
     * 无网络时显示的view
     */
    private View notNetView;
    /**
     * 被替换的view
     */
    private View mTargetView;
    /**
     * 要替换进去的view
     */
    private View mReplaceView = null;


    public void setNotNetView(View notNetView) {
        this.notNetView = notNetView;
    }

    /**
     * 默认的无网络的显示的view
     *
     * @param context
     */
    private static final int DEFAULT_NONETWORK = R.layout.network_missing;


    public MonitorNetworkUtils(Context context) {
        super(context);

        notNetView = LayoutInflater.from(context).inflate(DEFAULT_NONETWORK, null);
    }


    /**
     * 显示view
     *
     * @param views
     */
    public void showView(final View views) {
        toReplaceView(views, notNetView);
    }


    /**
     * 移除view
     */
    public void dismissView() {
        removeView();
    }


    /**
     * 用来替换某个View，比如你可以用一个空页面去替换某个View
     *
     * @param targetView  被替换的那个View
     * @param replaceView 要替换进去的那个View
     * @return
     */
    private void toReplaceView(View targetView, final View replaceView) {
        mTargetView = targetView;
        if (mTargetView == null) {
            return;
        } else if (!(mTargetView.getParent() instanceof ViewGroup)) {
            return;
        }

        ViewGroup parentViewGroup = (ViewGroup) mTargetView.getParent();
        int index = parentViewGroup.indexOfChild(mTargetView);
        if (mReplaceView != null) {
            parentViewGroup.removeView(mReplaceView);
        }
        mReplaceView = replaceView;
        mReplaceView.setLayoutParams(mTargetView.getLayoutParams());

        parentViewGroup.addView(mReplaceView, index);

        //RelativeLayout时别的View可能会依赖这个View的位置，所以不能GONE
        if (parentViewGroup instanceof RelativeLayout) {
            mTargetView.setVisibility(View.INVISIBLE);
        } else {
            mTargetView.setVisibility(View.GONE);
        }
        return;
    }

    /**
     * 移除你替换进来的View
     */
    private final void removeView() {
        if (mReplaceView != null && mTargetView != null) {
            if (mTargetView.getParent() instanceof ViewGroup) {
                ViewGroup parentViewGroup = (ViewGroup) mTargetView.getParent();
                parentViewGroup.removeView(mReplaceView);
                mReplaceView = null;
                mTargetView.setVisibility(View.VISIBLE);
            }
        }
        return;
    }

    /**
     * @return 返回你替换进来的View
     */
    private final View getView() {
        return mReplaceView;
    }


}
