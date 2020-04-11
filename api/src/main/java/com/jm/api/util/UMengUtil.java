package com.jm.api.util;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 友盟工具类
 *
 * @author jinXiong.Xie
 */
public class UMengUtil {


    /**
     * 内存泄漏解决方案
     *
     * @param activity
     */
    public static void release(Activity activity) {
        UMShareAPI.get(activity).release();
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        UMShareAPI.get(this).release();
//    }


    /**
     * 判断是否安装
     *
     * @param activity
     * @param share_media
     * @return
     */
    public boolean isInstall(Activity activity, SHARE_MEDIA share_media) {
        return UMShareAPI.get(activity).isInstall(activity, share_media);
    }

    /**
     * 删除授权
     *
     * @param activity
     */
    public static void deleteOauth(Activity activity, SHARE_MEDIA media) {
        UMShareAPI.get(activity).deleteOauth(activity, media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    /**
     * 设置是否每次登录都拉取确认界面
     *
     * @param context
     * @param bool
     */
    public static void needAuth(Context context, boolean bool) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(bool);
        UMShareAPI.get(context).setShareConfig(config);
    }
}
