package com.jm.xpproject.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.view.KeyEvent;

import com.jm.api.util.SharedAccount;
import com.jm.api.widget.MySpecificDialog;
import com.jm.core.common.tools.utils.ActivitiesManager;
import com.jm.xpproject.bean.UserData;

import java.lang.ref.WeakReference;


/**
 * Dialog工具类
 *
 * @author jinXiong.Xie
 */

public class DialogUtil {

    private static DialogUtil dialogUtil;
    private static WeakReference<Context> wr;
    private static MySpecificDialog dialog;
    private static Class loginClass;

    private DialogUtil(Context context, Class loginClass) {
        wr = new WeakReference<>(context);
        setLoginClass(loginClass);
//        //加载对话框
//        dialog = new MySpecificDialog(wr.get());
    }

    //    public static DialogUtil getInstance(Context context, Class loginClass) {
//        if (dialogUtil == null || wr == null || wr.get() == null) {
//            dialogUtil = new DialogUtil(context, loginClass);
//        } else if (((Activity) wr.get()).isFinishing()) {
//            dialogUtil = new DialogUtil(context, loginClass);
//        }
//        return dialogUtil;
//    }
    public static DialogUtil getInstance(Context context, Class loginClass) {
        if (dialogUtil == null || wr == null || wr.get() == null) {
            dialogUtil = new DialogUtil(context, loginClass);
            dialog = null;
        } else if (((Activity) wr.get()).isFinishing()) {
            dialogUtil = new DialogUtil(context, loginClass);
            dialog = null;
        }
        //添加上下文判断，改正弹框所在的Activity
        if (wr.get() != context) {
            dialogUtil = new DialogUtil(context, loginClass);
            dialog = null;
        }
        return dialogUtil;
    }

    public static void setLoginClass(Class loginClass) {
        DialogUtil.loginClass = loginClass;
    }

    /**
     * 显示重新登录对话框
     */
    public void showOtherLoginDialog() {
        if (wr == null || wr.get() == null) {
            return;
        }
        //监听系统的按键
        OnKeyListener keyListener = new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        //消除用户信息
        UserData userData = UserData.getInstance();
        userData.clear();
        SharedAccount.getInstance(wr.get()).delete();
        //加载对话框
        if (dialog == null) {
//            dialog = new MySpecificDialog(wr.get());
            dialog = new MySpecificDialog.Builder(wr.get()).strMessage("您的账号验证信息已过期，请重新登录").strCenter("确定").myDialogCenterCallBack(new MySpecificDialog.MyDialogCenterCallBack() {
                @Override
                public void onCenterBtnFun(Dialog dialog) {
                    dialog.dismiss();

                    Intent intent = new Intent(wr.get(), loginClass);
                    wr.get().startActivity(intent);
                    ActivitiesManager.getInstance().popAllActivityExceptOne(loginClass);
                }
            }).buildDialog();
            dialog.setOnKeyListener(keyListener);
            dialog.setCancelable(false);
        }
        if (!dialog.isShowing()) {
//            dialog.initCenterDialog("您的账号验证信息已过期，请重新登录", "确定", new MySpecificDialog.MyDialogCenterCallBack() {
//                @Override
//                public void onCenterBtnFun() {
//                    dialog.dismiss();
//
//                    Intent intent = new Intent(wr.get(), loginClass);
//                    wr.get().startActivity(intent);
//                    ActivitiesManager.getInstance().popAllActivityExceptOne(loginClass);
////        ActivitiesManager.getInstance().exit();
//                }
//            })
            dialog.showDialog();
        }
    }
}
