package com.jm.xpproject.ui.setting.util;

import android.content.Context;

import com.jm.core.common.tools.tools.GsonUtil;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.bean.UserInfoBean;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.json.JSONObject;

/**
 * 基于小跑的获取用户信息工具类
 *
 * @author jinXiong.Xie
 */

public class XPUserInfoUtil extends XPBaseUtil {

    public XPUserInfoUtil(Context context) {
        super(context);
    }

    /**
     * 获取用户信息
     *
     * @param sessionId
     * @param callBack
     */
    public void requestUserInfo(String sessionId, final RequestUserInfoCallBack callBack) {
        HttpCenter.getInstance(getContext()).getUserHttpTool().httpUserInfo(sessionId, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                UserInfoBean infoBean = GsonUtil.gsonToBean(obj.optJSONObject("data").toString(), UserInfoBean.class);
                if (infoBean != null) {
                    //更新数据
                    UserData userData = UserData.getInstance();
                    userData.setMobile(infoBean.getMobile());
                    userData.setHead(infoBean.getHead());
                    userData.setNick(infoBean.getNick());
                    if (callBack != null) {
                        callBack.success(infoBean);
                    }
                }
            }

            @Override
            public void error(int id, JSONObject obj, Object[] data) {
                super.error(id, obj, data);
            }
        });
    }

    /**
     * 获取用户信息回调
     */
    public interface RequestUserInfoCallBack {
        /**
         * 成功获取用户信息
         *
         * @param userInfoBean
         */
        void success(UserInfoBean userInfoBean);
    }
}
