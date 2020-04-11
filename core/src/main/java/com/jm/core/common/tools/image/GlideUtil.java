package com.jm.core.common.tools.image;


import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

/**
 * 使用Glide加载图片
 */

public class GlideUtil {

    private GlideUtil() {
    }

    /**
     * @param context 上下文
     * @param imgUrl  图片链接
     */
    public static void loadImage(Context context, Object imgUrl) {
//        if (!((Activity) context).isFinishing()) {
        Glide.with(context).load(imgUrl);
//        }
    }

    /**
     * @param context   上下文
     * @param imgUrl    图片链接
     * @param imageView 显示图片的ImageView
     */
    public static void loadImage(Context context, Object imgUrl, ImageView imageView) {
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(imgUrl).dontAnimate().into(imageView);
        }
    }

    /**
     * @param context       上下文
     * @param imgUrl        图片链接
     * @param placeholderId 占位图的图片Id
     * @param imageView     显示图片的ImageView
     */
    public static void loadImage(Context context, Object imgUrl, int placeholderId,
                                 ImageView imageView) {
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(imgUrl).dontAnimate().placeholder(placeholderId).into(imageView);
        }
    }

    /**
     * @param context       上下文
     * @param imgUrl        图片链接
     * @param placeholderId 占位图的图片Id
     * @param errorId       错误的图片Id
     * @param imageView     显示图片的ImageView
     */
    public static void loadImage(Context context, Object imgUrl, int placeholderId, int errorId,
                                 ImageView imageView) {
        // Glide 加载图片
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(imgUrl).dontAnimate().placeholder(placeholderId).error(errorId)
                    .into(imageView);
        }

    }


    /**
     * @param context   上下文
     * @param imgUrl    图片链接
     * @param transform 对显示的图片进行处理（圆角之类处理）
     * @param imageView 显示图片的ImageView
     */
    public static void loadImage(Context context, Object imgUrl, CornersTransform transform,
                                 ImageView imageView) {
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(imgUrl).dontAnimate()
                    .transform(new CenterCrop(context), transform).into(imageView);

        }
    }

    /**
     * @param context       上下文
     * @param imgUrl        图片链接
     * @param placeholderId 占位图的图片Id
     * @param transform     对显示的图片进行处理（圆角之类处理）
     * @param imageView     显示图片的ImageView
     */
    public static void loadImage(Context context, Object imgUrl, int placeholderId,
                                 CornersTransform transform,
                                 ImageView imageView) {
        if (!((Activity) context).isFinishing()) {

            Glide.with(context).load(imgUrl).dontAnimate().placeholder(placeholderId)
                    .transform(new CenterCrop(context), transform).into(imageView);
        }
    }

    /**
     * @param context       上下文
     * @param imgUrl        图片链接
     * @param placeholderId 占位图的图片Id
     * @param errorId       错误的图片Id
     * @param transform     对显示的图片进行处理（圆角之类处理）
     * @param imageView     显示图片的ImageView
     */
    public static void loadImage(Context context, Object imgUrl,
                                 int placeholderId, int errorId, CornersTransform transform, ImageView imageView) {
        // Glide 加载图片
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(imgUrl).dontAnimate().placeholder(placeholderId).error(errorId)
                    .transform(new CenterCrop(context), transform).into(imageView);

        }

    }
}
