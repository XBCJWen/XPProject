package com.jm.api.util;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


/**
 * 分享到第三方
 *
 * @author jinXiong.Xie
 */
public class ShareUtil {
    /*
    在分享所在的Activity里复写onActivityResult方法,注意不可在fragment中实现，如果在fragment中调用分享，
    在fragment依赖的Activity中实现，如果不实现onActivityResult方法，会导致分享或回调无法正常进行
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
    */

    private Activity activity;

    public ShareUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 分享（带面板）
     *
     * @param text
     */
    public void shareTextPanel(String text) {
        new ShareAction(activity).withText(text).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).open();
    }

    /**
     * 分享（不带面板）
     *
     * @param text
     */
    public void shareText(SHARE_MEDIA shareType, String text) {
        new ShareAction(activity)
                //传入平台
                .setPlatform(shareType)
                //分享内容
                .withText(text)
                //回调监听器
                .setCallback(umShareListener)
                .share();
    }

    /**
     * 分享
     *
     * @param shareType   分享平台
     * @param url         分享的链接
     * @param title       标题
     * @param drawableId  图片
     * @param description 描述
     */
    public void shareUrl(SHARE_MEDIA shareType, String url, String title, int drawableId, String description) {
        shareUrl(shareType, url, title, getUMImage(drawableId), description);
    }

    /**
     * 分享
     *
     * @param shareType   分享平台
     * @param url         分享的链接
     * @param title       标题
     * @param imageUrl    图片
     * @param description 描述
     */
    public void shareUrl(SHARE_MEDIA shareType, String url, String title, String imageUrl, String description) {
        shareUrl(shareType, url, title, getUMImage(imageUrl), description);
    }

    /**
     * 分享
     *
     * @param shareType   分享平台
     * @param url         分享的链接
     * @param title       标题
     * @param image       图片
     * @param description 描述
     */
    public void shareUrl(SHARE_MEDIA shareType, String url, String title, UMImage image, String description) {
        //链接
        UMWeb web = new UMWeb(url);
        //标题
        web.setTitle(title);
        //图片
        web.setThumb(image);
        //描述
        web.setDescription(description);

        //传入平台
        new ShareAction(activity)
                .setPlatform(shareType)
//        .withText("hello")//分享内容
                .withMedia(web)
                //回调监听器
                .setCallback(umShareListener)
                .share();
    }

    /**
     * 获取图片类
     *
     * @param imageUrl
     * @return
     */
    public UMImage getUMImage(String imageUrl) {
        return new UMImage(activity, imageUrl);
    }

    /**
     * 获取图片类
     *
     * @param drawableId
     * @return
     */
    public UMImage getUMImage(int drawableId) {
        return new UMImage(activity, drawableId);
    }


    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(activity, "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(activity, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//      Toast.makeText(InviteFriendAct.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };


}
