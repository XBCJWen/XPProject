package com.jm.xpproject.listener;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.jm.core.common.http.okhttp.ResultListener;
import com.jm.core.common.widget.dialog.LoadingDialog;
import com.jm.core.common.widget.toast.MyToast;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.utils.DialogUtil;

import org.json.JSONObject;

import okhttp3.Call;

/**
 * 具有加载滚动条和帐号在别处登录处理的网络回调，仅实现成功的方法即可
 *
 * @author jinXiong.Xie
 */

public abstract class LoadingResultListener implements ResultListener {

    private LoadingDialog myDialog;
    protected Context context;

    public LoadingResultListener(Context context) {
        this.context = context;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (myDialog == null) {
                        myDialog = new LoadingDialog(context, "", "loading");
                    }
                    if (!((Activity) context).isFinishing()) {
                        myDialog.show();
                    }
                    break;
                case 1:
                    if (myDialog != null&&myDialog!=null) {
                        myDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void state(int id, boolean isStartOrEnd, Object[] data) {
        if (isStartOrEnd) {
            handler.sendEmptyMessage(0);
        } else {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void fail(int id, Call call, final Exception e, Object[] data) {
        e.printStackTrace();
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyToast.showToast(context, e.getMessage());
            }
        });
    }

    @Override
    public void onOtherLogin() {
        DialogUtil.getInstance(context, DataConfig.LOGIN_CLASS).showOtherLoginDialog();
    }

    /**
     * 显示Desc
     *
     * @param jsonObject
     */
    protected void showDesc(JSONObject jsonObject) {
        MyToast.showToast(context, getDesc(jsonObject));
    }

    /**
     * 获取Desc
     *
     * @param jsonObject
     */
    protected String getDesc(JSONObject jsonObject) {
        return jsonObject.optString("desc", "");
    }
}
