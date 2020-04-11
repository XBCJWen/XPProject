package com.jm.api.util;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.jm.core.common.tools.base.UriUtil;

import java.io.File;

/**
 * Internet工具类
 *
 * @author jinXiong.Xie
 */

public class IntentUtil {

    private IntentUtil() {
    }

    /**
     * 播放视频
     */
    public static void showVideo(Context context, String cameraPath) {
        if (TextUtils.isEmpty(cameraPath)) {
            return;
        }
        //  0.    定义好视频的路径
        Uri uri = Uri.parse(cameraPath);

        //  1.  先设定好Intent
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //  2.  设置好 Data：播放源，是一个URI
        //      设置好 Data的Type：类型是 “video/mp4"
        intent.setDataAndType(uri, "video/*");

        //  3.  跳转：
        context.startActivity(intent);

    }

    /**
     * 创建打开浏览器Intent
     */
    public static void intentToBrowser(Context ctx, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        ctx.startActivity(intent);
    }

    /**
     * 显示打电话的界面
     *
     * @param context
     * @param mobile
     */
    public static void showCallLayout(Context context, String mobile) {
        Intent intent1 = new Intent(Intent.ACTION_DIAL);
        intent1.setData(Uri.parse("tel:" + mobile));
        context.startActivity(intent1);
    }

    /**
     * 显示短信的界面
     *
     * @param context
     * @param mobile
     */
    public static void showSMSLayout(Context context, String mobile, String content) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        //必须指定type
        smsIntent.setType("vnd.android-dir/mms-sms");
        //address字段不能改
        smsIntent.putExtra("address", mobile);
        //sms_body 不能改
        smsIntent.putExtra("sms_body", content);
        context.startActivity(smsIntent);
    }


    /**
     * 从Intent中获取Bundle
     *
     * @param intent
     * @return
     */
    public static Bundle getBundle(Intent intent) {
        Bundle bundle = new Bundle();
        if (intent == null) {
            return bundle;
        }

        if (intent.getExtras() == null) {
            return bundle;
        }
        return intent.getExtras();
    }

    /**
     * 打电话
     *
     * @param context
     * @param mobile
     */
    public static void callMobile(final Context context, final String mobile) {
        PermissionTools permissionTools = new PermissionTools(context);
        permissionTools.requestPermissionDefault(new PermissionTools.PermissionCallBack() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mobile));
                context.startActivity(intent);
            }

            @Override
            public void onCancel() {

            }
        }, new String[]{
                Manifest.permission.CALL_PHONE});
    }

    /**
     * 跳转页面
     *
     * @param activity
     * @param actClass
     * @param bundle
     */
    public static void intentToActivity(Activity activity, Class actClass, Bundle bundle, int code) {
        Intent intent = new Intent(activity, actClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, code);
    }


    /**
     * 跳转页面
     *
     * @param context
     * @param actClass
     * @param bundle
     */
    public static void intentToActivity(Context context, Class actClass, Bundle bundle) {
        Intent intent = new Intent(context, actClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param context
     * @param actClass
     */
    public static void intentToActivity(Context context, Class actClass) {
        intentToActivity(context, actClass, null);
    }

    /**
     * 跳转回桌面
     *
     * @param context
     */
    public static void intentToDesktop(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    /**
     * 用于打开HTML文件
     *
     * @param context
     * @param file
     * @return
     */
    public static void intentToHtmlFile(Context context, File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        context.startActivity(intent);
    }

    /**
     * 用于打开图片文件
     *
     * @param context
     * @param file
     */
    public static void intentToImageFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "image/*");
        context.startActivity(intent);
    }

    /**
     * 用于打开PDF文件
     *
     * @param context
     * @param file
     * @return
     */
    public static void intentToPdfFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
    }

    /**
     * 用于打开文本文件
     *
     * @param file
     * @return
     */
    public static void intentToTextFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "text/plain");
        context.startActivity(intent);
    }

    /**
     * 用于打开音频文件
     *
     * @param context
     * @param file
     */
    public static void intentToAudioFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "audio/*");
        context.startActivity(intent);
    }

    /**
     * 用于打开视频文件
     *
     * @param context
     * @param file
     */
    public static void intentToVideoFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "video/*");
        context.startActivity(intent);
    }


    /**
     * 用于打开CHM文件
     *
     * @param context
     * @param file
     */
    public static void intentToChmFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "application/x-chm");
        context.startActivity(intent);
    }


    /**
     * 用于打开Word文件
     *
     * @param context
     * @param file
     */
    public static void intentToWordFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "application/msword");
        context.startActivity(intent);
    }

    /**
     * 用于打开Excel文件
     *
     * @param context
     * @param file
     */
    public static void intentToExcelFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        context.startActivity(intent);
    }

    /**
     * 用于打开PPT文件
     *
     * @param context
     * @param file
     */
    public static void intentToPPTFile(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = UriUtil.getUri(context, file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        context.startActivity(intent);
    }

    /**
     * 用于打开apk文件
     *
     * @param context
     * @param file
     */
    public static void intentToApkFile(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


}
