package com.jm.xpproject.ui.login.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.jm.api.util.SharedAccount;
import com.jm.api.util.UMengAnalyticsUtil;
import com.jm.core.common.tools.base.EditUtil;
import com.jm.core.common.tools.tools.GsonUtil;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.OtherLoginUtil;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

/**
 * 基于小跑的登录工具类
 *
 * @author jinXiong.Xie
 */

public class XPLoginUtil extends XPBaseUtil {


    /**
     * 微信
     */
    private final int WE_CHAT = 0;
    /**
     * 新浪
     */
    private final int SINA = 1;
    /**
     * QQ
     */
    private final int QQ = 2;

    public XPLoginUtil(Context context) {
        super(context);
    }

    /**
     * 自动填充手机号
     */
    public void autoFillMobile(final EditText editMobile) {

        EditUtil.setText(editMobile, SharedAccount.getInstance(getActivity()).getMobile());

    }


    /**
     * 第三方登录
     *
     * @param unionId
     * @param head
     * @param nick
     * @param otherLoginType
     */
    private void httpAuthorize(final String unionId, final String head, final String nick, final int otherLoginType) {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpAuthorize(unionId, head, nick, otherLoginType, new LoadingErrorResultListener(getContext()) {
            @Override
            public void fail(int id, Call call, Exception e, Object[] data) {
//                super.fail(id, call, e, data);
            }

            @Override
            public void normal(int id, JSONObject obj, Object[] data) {

                UserData.getInstance().save(GsonUtil.gsonToBean(obj.optJSONObject("data").toString(), UserData.class));
                SharedAccount.getInstance(getContext()).saveAvatarAndName(UserData.getInstance().getHead(), UserData.getInstance().getNick());
                //统计平台登录
                String strUserId = String.valueOf(UserData.getInstance().getId());
                switch (otherLoginType) {
                    case QQ:
                        UMengAnalyticsUtil.onProfileSignIn("QQ", strUserId);
                        break;
                    case WE_CHAT:
                        UMengAnalyticsUtil.onProfileSignIn("微信", strUserId);
                        break;
                    case SINA:
                        UMengAnalyticsUtil.onProfileSignIn("新浪", strUserId);
                        break;
                    default:
                }

//                MainAct.actionStart(context);
                DataConfig.turnToMain(context);
                finish();
            }

        });
    }

    /**
     * 帐号密码式登录
     *
     * @param strPhone
     * @param strPsw
     */
    private void httpLogin(final String strPhone, final String strPsw) {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpLogin(strPhone, strPsw, new LoadingErrorResultListener(getContext()) {
            @Override
            public void fail(int id, Call call, Exception e, Object[] data) {
                super.fail(id, call, e, data);
            }

            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                SharedAccount.getInstance(context).saveMobileLogin(strPhone);

                String userInfo = obj.optJSONObject("data").toString();
                UserData.getInstance().save(GsonUtil.gsonToBean(userInfo, UserData.class));
                SharedAccount.getInstance(getContext()).saveAvatarAndName(UserData.getInstance().getHead(), UserData.getInstance().getNick());
                if (DataConfig.SAVE_USER_INFO) {
                    SharedAccount.getInstance(context).saveUserInfo(userInfo);
                }
                //统计平台登录
                UMengAnalyticsUtil.onProfileSignIn("帐号密码", String.valueOf(UserData.getInstance().getId()));
//                MainAct.actionStart(context);
                DataConfig.turnToMain(context);
                finish();
            }
        });
    }

    /**
     * 第三方登录
     *
     * @param media
     */
    public void otherTypeLogin(SHARE_MEDIA media) {
        OtherLoginUtil.login((Activity) getContext(), media, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            //0微信1新浪 2qq
            if (platform.equals(SHARE_MEDIA.QQ)) {
                String strOpenId = data.get("openid");
                String head = data.get("profile_image_url");
                String nick = data.get("screen_name");
                if (TextUtils.isEmpty(strOpenId)) {
                    showToast("授权失败，请稍后再试");
                } else {
                    httpAuthorize(strOpenId, head, nick, QQ);
                }
            }
            if (platform.equals(SHARE_MEDIA.WEIXIN)) {
                String strUnionId = data.get("uid");
                String head = data.get("profile_image_url");
                String nick = data.get("screen_name");
                if (TextUtils.isEmpty(strUnionId)) {
                    showToast("授权失败，请稍后再试");
                } else {
                    httpAuthorize(strUnionId, head, nick, WE_CHAT);
                }
            }
            if (platform.equals(SHARE_MEDIA.SINA)) {
                String strUnionId = data.get("uid");
                String head = data.get("avatar_large");
                String nick = data.get("screen_name");
                if (TextUtils.isEmpty(strUnionId)) {
                    showToast("授权失败，请稍后再试");
                } else {
                    httpAuthorize(strUnionId, head, nick, SINA);
                }
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("取消授权");
        }
    };

    /**
     * 判断登录条件
     */
    public void checkLogin(EditText editMobile, EditText editPsw) {
        String[] strEdit = EditUtil.getEditsStringAutoTip(getContext(), editMobile, editPsw);
        if (strEdit == null) {
            return;
        }

        String strPsw = editPsw.getText().toString();
        if (strPsw.length() < 6) {
            showToast("密码位数不能少于6位");
            return;
        }

//        if (!StringUtil.isMobile(strEdit[0])) {
//            showToast("请检查输入的手机号");
//            return;
//        }

        httpLogin(strEdit[0], strEdit[1]);
    }
}
