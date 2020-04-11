package com.jm.api.util;

/**
 * 请求回调
 *
 * @author jinXiong.Xie
 */
public abstract class RequestCallBack {

    /**
     * 成功回调
     *
     * @param obj
     */
    public abstract void success(Object obj);

    /**
     * 失败回调
     *
     * @param obj
     */
    public void error(Object obj) {

    }
}
