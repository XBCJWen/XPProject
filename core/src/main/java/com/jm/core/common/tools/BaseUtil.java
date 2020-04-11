package com.jm.core.common.tools;

import android.app.Activity;
import android.content.Context;

import com.jm.core.common.widget.toast.MyToast;

/**
 * 基础工具类
 *
 * @author jinXiong.Xie
 */
public class BaseUtil {

    private Context context;

    public BaseUtil(Context context) {
        this.context = context;
    }

    protected void showToast(String strMsg) {
        MyToast.showToast(context, strMsg);
    }

    protected void finish() {
        ((Activity) context).finish();
    }

    public void showDataErrorToast() {
        showToast("数据异常");
    }

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return (Activity) context;
    }
}
