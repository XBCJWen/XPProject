package com.jm.xpproject.listener;

import android.content.Context;

import com.jm.core.common.widget.toast.MyToast;

import org.json.JSONObject;

/**
 * 基于LoadingCodeResultListener，将其错误的状态直接提示出来
 *
 * @author jinXiong.Xie
 */

public abstract class LoadingErrorResultListener extends LoadingCodeResultListener {


    public LoadingErrorResultListener(Context context) {
        super(context);
    }

    /**
     * 错误状态（code==0）
     *
     * @param id
     * @param obj
     * @param data
     */
    @Override
    public void error(int id, JSONObject obj, Object[] data) {
        MyToast.showToast(context, obj.optString("desc"));
    }
}

