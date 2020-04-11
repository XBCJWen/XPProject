package com.jm.xpproject.ui.setting.util;

import android.app.Dialog;
import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.jm.api.util.IntentUtil;
import com.jm.api.util.SharedAccount;
import com.jm.api.util.UMengAnalyticsUtil;
import com.jm.api.util.UMengUtil;
import com.jm.api.widget.MySpecificDialog;
import com.jm.core.common.tools.utils.ActivitiesManager;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.json.JSONObject;

/**
 * 基于小跑的设置页面的工具类
 *
 * @author jinXiong.Xie
 */

public class XPSettingUtil extends XPBaseUtil {

    public XPSettingUtil(Context context) {
        super(context);
    }

    /**
     * 退出登录
     */
    public void logout(String sessionId) {
        showLogoutDialog(sessionId);
    }

    private MySpecificDialog dialog;

    /**
     * 显示确认退出的dialog
     */
    private void showLogoutDialog(final String sessionId) {
        if (dialog==null){
            dialog = new MySpecificDialog.Builder(getContext()).strMessage("是否确认退出登录？").strLeft("取消").strRight("确定").myDialogCallBack(new MySpecificDialog.MyDialogCallBack() {
                @Override
                public void onLeftBtnFun(Dialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onRightBtnFun(Dialog dialog) {
                    dialog.dismiss();
                    httpLogout(sessionId);
                }
            }).buildDialog();
        }
        dialog.showDialog();
//        MySpecificDialog dialog = new MySpecificDialog(getContext());
//        dialog.initDialog("是否确认退出登录？", "取消", "确定", new MySpecificDialog.MyDialogCallBack() {
//            @Override
//            public void onLeftBtnFun() {
//
//            }
//
//            @Override
//            public void onRightBtnFun() {
//                HttpLogout(sessionId);
//            }
//        }).showDialog();
    }

    /**
     * 用户退出功能
     */
    public void httpLogout(String sessionId) {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpLogout(sessionId, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                UserData userData = UserData.getInstance();
                userData.clear();
                SharedAccount.getInstance(context).delete();

                //移除帐号信息
                UMengAnalyticsUtil.onProfileSignOff();
                //移除平台授权
                UMengUtil.deleteOauth(getActivity(), SHARE_MEDIA.QQ);
                UMengUtil.deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN);
                UMengUtil.deleteOauth(getActivity(), SHARE_MEDIA.SINA);

//                RongIM.getInstance().disconnect();
//                RongIM.getInstance().logout();

//                ActivitiesManager.getInstance().popAllActivity();
                IntentUtil.intentToActivity(context, DataConfig.LOGIN_CLASS);
                ActivitiesManager.getInstance().popAllActivityExceptOne(DataConfig.LOGIN_CLASS);

            }
        });
    }
}
