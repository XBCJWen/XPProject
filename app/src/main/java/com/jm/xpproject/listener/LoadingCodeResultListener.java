package com.jm.xpproject.listener;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 基于具有加载滚动条和帐号在别处登录处理的网络回调，将成功的方法分为code为0和1的情况
 *
 * @author jinXiong.Xie
 */

public abstract class LoadingCodeResultListener extends LoadingResultListener {

    public LoadingCodeResultListener(Context context) {
        super(context);
    }

    @Override
    public void success(int id, Call call, Response response, JSONObject obj, Object[] data) {
        Activity activity = (Activity) context;
        if (activity == null || activity.isFinishing()) {
            return;
        }
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
