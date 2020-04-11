package com.jm.xpproject.utils.xp;

import android.content.Context;
import android.text.TextUtils;

import com.jm.core.common.tools.BaseUtil;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 小跑基础工具类
 *
 * @author jinXiong.Xie
 */

public class XPBaseUtil extends BaseUtil {


    public XPBaseUtil(Context context) {
        super(context);
    }

    public void showOtherLoginDialog() {
        if (this == null) {
            return;
        }
        DialogUtil util = DialogUtil.getInstance(getContext(), DataConfig.LOGIN_CLASS);
        util.showOtherLoginDialog();
    }

    public String getSessionId() {
        UserData userData = UserData.getInstance();
        String strSessionId = userData.getSessionId();
        if (TextUtils.isEmpty(strSessionId)) {
            showOtherLoginDialog();
        }
        return strSessionId;
    }

    public String getSessionIdText() {
        return UserData.getInstance().getSessionId();
    }

    public void postEvent(int eventId, Object... message) {
        EventBus.getDefault().post(new MessageEvent(eventId, message));
    }
}
