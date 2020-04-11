package com.jm.core.common.tools.net;

import android.content.Context;
import android.text.TextUtils;

import com.jm.core.common.tools.log.LogUtil;
import com.jm.core.common.tools.base.SharedPreferencesTool;

import java.io.File;

/**
 * 图片缓存(缓存到file目录中)
 *
 * @author jinXiong.Xie
 */

public class ImageCache {

    private ImageCache() {
    }

    /**
     * 图片缓存的地址
     */
    private static String filePath = "FILE_PATH";
    /**
     * 图片点击超链接
     */
    private static String clickLink = "CLICK_LINK";
    /**
     * 图片的链接
     */
    private static String imageLink = "IMAGE_LINK";

    /**
     * 获取图片缓存
     *
     * @param context
     * @param mark     标识
     * @param callBack
     */
    public static void requestImageCache(Context context, String mark, RequestImageCacheCallBack callBack) {
        //获取
        SharedPreferencesTool tool = new SharedPreferencesTool(context, mark);
        String path = (String) tool.getParam(filePath, "");
        String link = (String) tool.getParam(clickLink, "");
        File file = new File(path);
        if (!file.exists()) {
            if (callBack != null) {
                callBack.noCache();
            }
        } else {
            if (callBack != null) {
                callBack.loadCacheSuccess(file, link);
            }
        }
    }


    /**
     * 保存图片缓存
     *
     * @param context
     * @param mark
     * @param imageNetLink
     * @param imageClickLink
     */
    public static void saveImageCache(Context context, String mark, String imageNetLink, String imageClickLink) {
        saveImageCache(context, mark, imageNetLink, imageClickLink, null);
    }

    /**
     * 保存图片缓存
     *
     * @param context
     * @param mark
     * @param imageNetLink
     * @param imageClickLink
     * @param callBack
     */
    public static void saveImageCache(Context context, String mark, String imageNetLink, String imageClickLink, SaveImageCacheCallBack callBack) {
        //判断图片链接是否有误
        if (TextUtils.isEmpty(imageNetLink)) {
            if (callBack != null) {
                callBack.onFailure(new Exception("图片链接为null"));
            }
        }
        //判断imageUrl是否相同，预防重复下载
        SharedPreferencesTool tool = new SharedPreferencesTool(context, mark);
        String path = (String) tool.getParam(filePath, "");
        String link = (String) tool.getParam(clickLink, "");
        String strLocalUrl = (String) tool.getParam(imageLink, "");
        if (!TextUtils.isEmpty(strLocalUrl) && imageNetLink.equals(strLocalUrl)) {
            //图片链接相同
            //判断图片缓存File存在
            File file = new File(path);
            if (!file.exists()) {
                downLoadImage(context, imageNetLink, mark, imageClickLink, callBack);
            } else {
                if (callBack != null) {
                    callBack.loadCacheSuccess(file, link);
                }
            }

            return;
        } else {
            downLoadImage(context, imageNetLink, mark, imageClickLink, callBack);
        }
    }

    /**
     * 下载图片
     *
     * @param context
     * @param imageUrl
     * @param mark
     * @param imageClickLink
     * @param callBack
     */
    private static void downLoadImage(final Context context, final String imageUrl, final String mark, final String imageClickLink, final SaveImageCacheCallBack callBack) {
        //开始下载图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                DownLoadUtil.downLoadImage(context, imageUrl, new DownLoadUtil.OnDownLoadImageCallBack() {
                    @Override
                    public void onSuccess(File file) {
                        //保存本地
                        SharedPreferencesTool tool = new SharedPreferencesTool(context, mark);
                        tool.setParams(filePath, file.getPath(), clickLink, imageClickLink,
                                imageLink, imageUrl);

                        LogUtil.e("onError", "下载" + mark + "图成功");

                        if (callBack != null) {
                            callBack.loadCacheSuccess(file, imageClickLink);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        LogUtil.e("onError", "下载" + mark + "图失败");
                        if (callBack != null) {
                            callBack.onFailure(e);
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 获取图片缓存回调
     */
    public interface RequestImageCacheCallBack {
        /**
         * 没有图片缓存
         */
        void noCache();

        /**
         * 获取图片缓存成功
         *
         * @param file
         * @param link
         */
        void loadCacheSuccess(File file, String link);
    }

    /**
     * 保存图片缓存回调
     */
    public interface SaveImageCacheCallBack {

        /**
         * 保存图片缓存失败
         */
        void onFailure(Exception e);

        /**
         * 获取图片缓存成功
         *
         * @param file
         * @param link
         */
        void loadCacheSuccess(File file, String link);
    }


}
