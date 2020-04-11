package com.jm.xpproject.ui.setting.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.api.util.SharedAccount;
import com.jm.core.common.tools.base.EditUtil;
import com.jm.core.common.tools.base.ReciprocalUtil;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * 基于小跑的绑定手机号的工具类
 *
 * @author jinXiong.Xie
 */

public class XPBindMobileUtil extends XPBaseUtil {

    /**
     * 判断是否点击过获取验证码，false：未点击，true：已点击
     */
    private boolean clickCode = false;

    private ReciprocalUtil util;

    public XPBindMobileUtil(Context context) {
        super(context);
        util = new ReciprocalUtil();
    }

    /**
     * 绑定手机号
     */
    public void bindMobile(String sessionId, EditText editMobile, EditText editCode) {
        String[] strEdit = EditUtil
                .getEditsStringAutoTip(getContext(), editMobile, editCode);
        if (strEdit == null) {
            return;
        }

        if (!clickCode) {
            showToast("请先获取验证码");
            return;
        }

        httpBindMobile(sessionId, strEdit);
    }


    private void httpBindMobile(String sessionId, final String[] strEdit) {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpBindMobile(sessionId, strEdit[0], strEdit[1], new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast(obj.optString("desc"));
                EventBus.getDefault()
                        .post(new MessageEvent(MessageEvent.BIND_MOBILE_SUCCESS, strEdit[0]));
                UserData.getInstance().setMobile(strEdit[0]);
                //记录新的手机号
                SharedAccount.getInstance(context)
                        .alterMobile(strEdit[0]);
                finish();
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param editMobile
     * @param tvCode
     */
    public void requestCode(EditText editMobile, final TextView tvCode) {
        String[] strEdit = EditUtil.getEditsStringAutoTip(getContext(), editMobile);
        if (strEdit == null) {
            return;
        }
        HttpCenter.getInstance(getContext()).getUserHttpTool().httpGetCode(strEdit[0], 1, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast("获取验证码成功");
                clickCode = true;
                getCode(tvCode);
            }
        });
    }

    private void getCode(TextView tvCode) {
        util.getCode(60, tvCode);
    }

    /**
     * 关闭获取验证码
     */
    public void closeRequestCode() {
        if (util != null) {
            util.closeAll();
        }
    }
}
