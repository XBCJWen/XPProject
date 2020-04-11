package com.jm.core.common.http.okhttp;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 基于简易版网络回调，并将成功的方法改为code为0和1两种状态
 *
 * @author jinXiong.Xie
 */

public abstract class SimpleCodeResultListener extends SimpleResultListener {

    @Override
    public void success(int id, Call call, Response response, JSONObject obj, Object[] data) {
        int code = obj.optInt("code");
        if (code == 0) {
            error(id, obj, data);
        } else if (code == 1) {
            normal(id, obj, data);
        }
    }

    /**
     * 正常状态（code==1）
     *
     * @param id
     * @param obj
     * @param data
     */
    public abstract void normal(int id, JSONObject obj, Object[] data);

    /**
     * 错误状态（code==0）
     *
     * @param id
     * @param obj
     * @param data
     */
    public abstract void error(int id, JSONObject obj, Object[] data);
}
