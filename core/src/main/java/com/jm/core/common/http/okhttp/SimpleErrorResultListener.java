package com.jm.core.common.http.okhttp;

import org.json.JSONObject;

/**
 * 基于SimpleCodeResultListener，将code为0的情况直接无视掉
 *
 * @author jinXiong.Xie
 */

public abstract class SimpleErrorResultListener extends SimpleCodeResultListener {

    /**
     * 错误状态（code==0）
     *
     * @param id
     * @param obj
     * @param data
     */
    @Override
    public void error(int id, JSONObject obj, Object[] data) {

    }
}
