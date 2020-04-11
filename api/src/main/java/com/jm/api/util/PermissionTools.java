package com.jm.api.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.jm.api.widget.MySpecificDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * 获取权限的工具类
 *
 * @author jinXiong.Xie
 */

public class PermissionTools {

    private static final String PACKAGE_URL_SCHEME = "package:";

    private MySpecificDialog dialog;
    private Context context;

    /**
     * 标记获取全部权限
     */
    private final int REQUEST_CODE = 100;

    public PermissionTools(Context context) {
        this.context = context;

    }

    /**
     * 获取权限
     *
     * @param listener
     * @param permission
     */
    public void requestPermission(PermissionListener listener, String... permission) {
        AndPermission.with(context)
                .requestCode(REQUEST_CODE)
                .permission(permission)
                .callback(listener)
                .start();
    }

    /**
     * 获取权限
     *
     * @param callBack
     * @param permission
     */
    public void requestPermissionDefault(final PermissionCallBack callBack, String... permission) {
        requestPermission(new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                callBack.onSuccess();
            }

            @Override
            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
                if (AndPermission.hasAlwaysDeniedPermission(context, deniedPermissions)) {
                    showPermissionDialog(callBack);
                } else {
                    showPermissionDialog(callBack);
                }
            }
        }, permission);
    }


    /**
     * 显示
     */
    private void showPermissionDialog(final PermissionCallBack callBack) {
        if (dialog == null) {
            dialog = new MySpecificDialog.Builder(context).strMessage("未获得应用运行所需的基本权限，请在设置中开启权限后再使用").strLeft("去设置").strRight("取消").myDialogCallBack(new MySpecificDialog.MyDialogCallBack() {
                @Override
                public void onLeftBtnFun(Dialog dialog) {
                    dialog.dismiss();
                    startAppSettings();
                }

                @Override
                public void onRightBtnFun(Dialog dialog) {
                    dialog.dismiss();
                    if (callBack != null) {
                        callBack.onCancel();
                    }
                }
            }).buildDialog();
        }
        dialog.showDialog();
//        dialog.initDialog("未获得应用运行所需的基本权限，请在设置中开启权限后再使用", "去设置", "取消", new MySpecificDialog.MyDialogCallBack() {
//            @Override
//            public void onLeftBtnFun() {
//                startAppSettings();
//            }
//
//            @Override
//            public void onRightBtnFun() {
//                dialog.dismiss();
//                if (callBack != null) {
//                    callBack.onCancel();
//                }
////                finish();
//            }
//        }).showDialog();
    }

    /**
     * 关闭
     */
    private void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + context.getPackageName()));
        context.startActivity(intent);
    }

    public interface PermissionCallBack {
        /**
         * 获取权限成功
         */
        void onSuccess();

        /**
         * 弹出“去设置权限提示”后，用户点击取消
         */
        void onCancel();
    }

}
