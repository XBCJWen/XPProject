package com.jm.xpproject.base.impl;

import android.view.View;

/**
 * 网络监听接口
 *
 * @author jinXiong.Xie
 */
public interface NetworkInterface {
    /**
     * 设置无网络的布局
     *
     * @param notNetViewLayoutId
     */
    void setNotNetView(int notNetViewLayoutId);

    /**
     * 设置无网络的布局
     *
     * @param notNetView
     */
    void setNotNetView(View notNetView);

    /**
     * 设置无网络时替换的View
     *
     * @param replaceView
     */
    void setReplaceView(View replaceView);

    /**
     * 设置无网络时替换的View
     *
     * @param replaceViewLayoutId
     */
    void setReplaceView(int replaceViewLayoutId);

    /**
     * 将ReplaceView替换为整个窗口的View
     */
    void setContentReplaceView();

    /**
     * 将ReplaceView替换为页面布局文件的View
     */
    void setLayoutReplaceView();

    /**
     * 移除replaceView
     */
    void removeReplaceView() ;


}
