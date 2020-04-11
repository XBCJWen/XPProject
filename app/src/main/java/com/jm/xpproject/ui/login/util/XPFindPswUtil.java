package com.jm.xpproject.ui.login.util;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.jm.core.common.tools.base.EditUtil;
import com.jm.core.common.tools.base.ReciprocalUtil;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * 基于小跑的找回密码工具类
 *
 * @author jinXiong.Xie
 */

public class XPFindPswUtil extends XPBaseUtil {

    private ReciprocalUtil util;

    /**
     * 判断是否点击过获取验证码，false：未点击，true：已点击
     */
    private boolean clickCode = false;

    public XPFindPswUtil(Context context) {
        super(context);

        util = new ReciprocalUtil();
    }


    /**
     * 获取验证码功能
     */
    public void httpGetCode(EditText editMobile, final Button btnGetCode) {

        String[] strEdit = EditUtil.getEditsStringAutoTip(getContext(), editMobile);
        if (strEdit == null) {
            return;
        }
        //1：注册  2：找回密码
        HttpCenter.getInstance(getContext()).getUserHttpTool().httpGetCode(strEdit[0], 2, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast("获取验证码成功");
                clickCode = true;
                getCode(btnGetCode);
            }
        });
    }

    /**
     * 验证码倒数
     *
     * @param btnGetCode
     */
    private void getCode(Button btnGetCode) {
        if (util != null) {
            util.getCode(60, btnGetCode);
        }
    }


    public void closeReciprocal() {
        if (util != null) {
            util.closeReciprocal();
        }
    }

    /**
     * 找回密码功能
     */
    public void httpFindPsw(final EditText editMobile, final EditText editPsw, EditText editPsw2, final EditText editCode) {
        String[] strEdit = EditUtil.getEditsStringAutoTip(getContext(), editMobile, editPsw, editPsw2, editCode);
        if (strEdit == null) {
            return;
        }
//        if (!StringUtil.isMobile(strEdit[0])) {
//            showToast("请检查输入的手机号");
//            return;
//        }

        if (!EditUtil.checkSamePsw(getContext(), editPsw, editPsw2, DataConfig.PSW_MIN_LENGTH)) {
            return;
        }

        if (!clickCode) {
            showToast("请先获取验证码");
            return;
        }

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpFindPsw(strEdit[0], strEdit[1], strEdit[3], new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                //找回密码成功后，在登录页显示该手机号
                EventBus.getDefault()
                        .post(new MessageEvent(MessageEvent.FIND_PSW_SUCCESS, editMobile.getText().toString()));
                showToast(obj.optString("desc"));
                finish();
            }
        });

    }
}
