package com.jm.api.appupdater;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.jm.api.widget.MySpecificDialog;
import com.jm.core.common.tools.utils.ActivitiesManager;

import java.io.File;

/**
 * Apk安装工具类
 *
 * @author jinXiong.Xie
 */
public class ApkInstallUtil {

    /**
     * 下载的Apk地址
     */
    private static String fileUri = null;


    public static void openAPKFile(Context context) {

        if (!TextUtils.isEmpty(fileUri)) {
            openAPKFile(context, fileUri);
        }

    }

    /**
     * 打开安装包
     *
     * @param mContext
     * @param fileUri
     */
    public static void openAPKFile(final Context mContext, String fileUri) {
        // 核心是下面几句代码
        if (null != fileUri) {
            try {
                ApkInstallUtil.fileUri = fileUri;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(fileUri);
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".FileProvider", apkFile);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                        if (!hasInstallPermission) {
                            Activity activity = ActivitiesManager.getInstance().currentActivity();
                            if (activity == null) {
                                return;
                            }
                            new MySpecificDialog.Builder(activity).strMessage("安装应用需要打开未知来源权限，请去设置中开启权限").strCenter("去设置").myDialogCenterCallBack(new MySpecificDialog.MyDialogCenterCallBack() {
                                @Override
                                public void onCenterBtnFun(Dialog dialog) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        startInstallPermissionSettingActivity(mContext);
                                    }
                                    dialog.dismiss();
                                }
                            }).buildDialog().showDialog();
//                            MyToast.showToast(mContext, "请允许" + mContext.getResources().getString(R.string.app_name) + "安装应用");

                            return;
                        }
                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    ApkInstallUtil.fileUri = null;
                    mContext.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context mContext) {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}
