package com.jm.core.common.http.okhttp;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 联网回调
 *
 * @author jinXiong.Xie
 * @date 2018/11/26
 */
public interface ResultListener {
    /**
     * 访问链接状态，开始请求时 isStartOrEnd 为 true，请求结束时， isStartOrEnd 为 false
     *
     * @param id
     * @param isStartOrEnd
     * @param data
     */
    void state(int id, boolean isStartOrEnd, Object[] data);

    /**
     * 访问链接失败
     *
     * @param id
     * @param call
     * @param e
     * @param data
     */
    void fail(int id, Call call, Exception e, Object[] data);

    /**
     * 请求成功，里面为请求的结果
     *
     * @param id
     * @param call
     * @param response
     * @param obj
     * @param data
     */
    void success(int id, Call call, Response response, JSONObject obj, Object[] data);

    /**
     * 帐号验证信息过期
     */
    void onOtherLogin();
}
