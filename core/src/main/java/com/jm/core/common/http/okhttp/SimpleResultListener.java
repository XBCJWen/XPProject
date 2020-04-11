package com.jm.core.common.http.okhttp;

import okhttp3.Call;

/**
 * 简易版网络回调，仅实现成功的方法即可
 *
 * @author jinXiong.Xie
 */

public abstract class SimpleResultListener implements ResultListener {
    @Override
    public void state(int id, boolean isStartOrEnd, Object[] data) {

    }

    @Override
    public void fail(int id, Call call, Exception e, Object[] data) {

    }

    @Override
    public void onOtherLogin() {

    }
}
