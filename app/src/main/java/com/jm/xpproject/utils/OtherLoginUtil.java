package com.jm.xpproject.utils;

import android.app.Activity;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.jm.core.common.tools.utils.AndroidShare;
import com.jm.core.common.widget.toast.MyToast;

/**
 * 其它方式登录
 *
 * @author jinXiong.Xie
 */

public class OtherLoginUtil {

    private OtherLoginUtil() {
    }

    /**
     * 基于友盟的第三方登录
     *
     * @param activity
     * @param media
     * @param listener
     */
    public static void login(Activity activity, SHARE_MEDIA media, UMAuthListener listener) {
        switch (media) {
            case SINA:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.SINA, listener);
                break;
            case WEIXIN:
                if (AndroidShare.isAvilible(activity, "com.tencent.mm")) {
                    UMShareAPI.get(activity)
                            .getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, listener);
                } else {
                    MyToast.showToast(activity, "请先安装微信后再试");
                }
                break;
            case QQ:
                if (AndroidShare.isAvilible(activity, "com.tencent.mobileqq")) {
                    UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.QQ, listener);
                } else {
                    MyToast.showToast(activity, "请先安装QQ后再试");
                }
                break;
            default:
                break;
        }
    }


}
