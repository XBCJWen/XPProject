package com.jm.api.appupdater.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jm.api.appupdater.ApkInstallUtil;
import com.jm.api.appupdater.updater.UpdaterUtils;
import com.jm.core.common.tools.log.LogUtil;
import com.jm.core.common.tools.base.FileUtil;

/**
 * 安装App
 */
public class ApkInstallReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long localDownloadId = UpdaterUtils.getLocalDownloadId(context);
            if (downloadApkId == localDownloadId) {
                LogUtil.e("download complete. downloadId is " + downloadApkId);
                installApk(context, downloadApkId);
            }
        } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            //处理 如果还未完成下载，用户点击Notification
            Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }
    }

    private static void installApk(Context context, long downloadApkId) {

//        Intent install = new Intent(Intent.ACTION_VIEW);
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);

        ApkInstallUtil.openAPKFile(context, FileUtil.getRealFilePathFromUri(context, downloadFileUri));
//        if (downloadFileUri != null) {
//            LogUtil.e("file location " + downloadFileUri.toString());
//            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(install);
//        } else {
//            LogUtil.e("download failed");
//        }
    }


}
