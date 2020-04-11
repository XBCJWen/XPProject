package com.jm.xpproject.utils.xp;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.jm.api.appupdater.updater.Updater;
import com.jm.api.appupdater.updater.UpdaterConfig;
import com.jm.api.util.IntentUtil;
import com.jm.api.widget.MySpecificDialog;
import com.jm.api.widget.SingleLineDialog;
import com.jm.core.common.tools.utils.VersionUtil;
import com.jm.core.common.widget.toast.MyToast;
import com.jm.xpproject.bean.VersionBean;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.http.api.BaseCloudApi;

import org.greenrobot.eventbus.EventBus;

/**
 * 检测更新工具类
 *
 * @author jinXiong.Xie
 */

public class XPCheckVersionUtil {

    private Context context;
    private String appName;

    /**
     * 下载url
     */
    private String url;

    public XPCheckVersionUtil(Context context, String appName) {
        this.context = context;
        this.appName = appName;
    }

    /**
     * 检测更新
     *
     * @param bean
     */
    public void checkUpdate(VersionBean bean) {

        checkUpdate(bean, true);
    }

    /**
     * 检测更新
     *
     * @param bean
     */
    public void checkUpdate(VersionBean bean, boolean showUpdateTip) {

        if (bean != null) {
            int versionCode = VersionUtil.getVersionCode(context);
            if (bean.getVersion() == versionCode || bean == null) {
                if (showUpdateTip) {
                    MyToast.showToast(context, "当前为最新版本，不需要更新");
                }
                return;
            } else {
                if (versionCode > bean.getVersion()) {
                    if (showUpdateTip) {
                        MyToast.showToast(context, "当前为最新版本，不需要更新");
                    }
                    return;
                }

//                if (TextUtils.isEmpty(bean.getUrl())) {
//                    url = "https://fir.im/bitmessage";
//                }
//                url = "http://releases.b0.upaiyun.com/hoolay.apk";
//                url = BaseCloudApi.getFileUrl(bean.getUrl());

                url = bean.getUrl();

                if (bean.getState() == 1) {
                    showMustVersionDialog();
                } else {
                    if (showUpdateTip) {
                        showSuggestVersionDialog();
                    }

                }
            }

        }
    }

    private MySpecificDialog suggestUpdateDialog;

    /**
     * 建议更新
     */
    private void showSuggestVersionDialog() {
        if (suggestUpdateDialog == null) {
            suggestUpdateDialog = new MySpecificDialog.Builder(context).strMessage("检测到新的版本，请尽快更新").strLeft("取消").strRight("确定").myDialogCallBack(new MySpecificDialog.MyDialogCallBack() {
                @Override
                public void onLeftBtnFun(Dialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onRightBtnFun(Dialog dialog) {
                    dialog.dismiss();
                    updateApk();
                }
            }).buildDialog();
//            suggestUpdateDialog = new MySpecificDialog(context);
//            suggestUpdateDialog.initDialog("检测到新的版本，请尽快更新", "取消", "确定", new MySpecificDialog.MyDialogCallBack() {
//                @Override
//                public void onLeftBtnFun() {
//
//                }
//
//                @Override
//                public void onRightBtnFun() {
//                    updateApk(url);
//                }
//            });
        }
        if (!suggestUpdateDialog.isShowing()) {
            suggestUpdateDialog.showDialog();
        }

    }

    private MySpecificDialog mustUpdateDialog;

    /**
     * 强制更新
     */
    private void showMustVersionDialog() {
        if (mustUpdateDialog == null) {
            mustUpdateDialog = new MySpecificDialog.Builder(context).strMessage("当前版本过低，请更新").strCenter("确定").myDialogCenterCallBack(new MySpecificDialog.MyDialogCenterCallBack() {
                @Override
                public void onCenterBtnFun(Dialog dialog) {
                    updateApk();
                    //是否为内置更新
                    if (DataConfig.BUILT_IN_UPDATE) {
                        dialog.dismiss();
                        showMustUpdateDialog();
                    }
                }
            }).buildDialog();
//            mustUpdateDialog = new MySpecificDialog(context);
//            mustUpdateDialog.initCenterDialog("当前版本过低，请更新", "确定", new MySpecificDialog.MyDialogCenterCallBack() {
//                @Override
//                public void onCenterBtnFun() {
//                    updateApk(url);
//                    showMustUpdateDialog();
//                }
//            });
        }
        if (!mustUpdateDialog.isShowing()) {
            mustUpdateDialog.showDialogOutSlideNoClose();
        }
    }

    private SingleLineDialog singleLineDialog;

    /**
     * 正在更新
     */
    private void showMustUpdateDialog() {
        singleLineDialog = new SingleLineDialog();
        singleLineDialog.initDialog(context, "正在更新中，请耐心等候").showNoClose();


    }

    /**
     * 更新App
     */
    private void updateApk() {
        if (TextUtils.isEmpty(url)) {
            MyToast.showToast(context, "获取数据失败，请稍后再试");
            return;
        }
        //是否为内置更新
        if (DataConfig.BUILT_IN_UPDATE) {
            url = BaseCloudApi.getFileUrl(url);
            UpdaterConfig config = new UpdaterConfig.Builder(context)
                    .setTitle(appName)
                    .setDescription("版本更新")
                    .setFileUrl(url)
                    .setCanMediaScanner(true)
                    .build();
            Updater.get().showLog(true).setDownLoadListener(new Updater.DownLoadListener() {
                @Override
                public void error() {
                    EventBus.getDefault()
                            .post(new MessageEvent(MessageEvent.DOWNLOAD_FAILED));
                }
            }).download(config);
        } else {
            IntentUtil.intentToBrowser(context, url);
        }

    }

    /**
     * 关闭正在更新的对话框
     */
    public void closeMustUpdateDialog() {

        MyToast.showToast(context, "下载失败，请稍后再试");

        if (singleLineDialog != null) {
            singleLineDialog.dismiss();
        }

    }

}
