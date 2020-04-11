package com.jm.xpproject.ui.login.util;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
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
 * 基于小跑的注册工具类
 *
 * @author jinXiong.Xie
 */

public class XPRegisterUtil extends XPBaseUtil {

    /**
     * 判断是否点击过获取验证码(false：未点击，true：已点击)
     */
    private boolean clickCode = false;

    private ReciprocalUtil util;

    public XPRegisterUtil(Context context) {
        super(context);
        util = new ReciprocalUtil();
    }


    /**
     * 注册
     *
     * @param editMobile
     * @param editPsw
     * @param editPsw2
     * @param editCode
     * @param cbProtocol
     */
    public void httpRegister(final EditText editMobile, EditText editPsw, EditText editPsw2, EditText editCode, CheckBox cbProtocol) {

        String[] strEdit = EditUtil.getEditsStringAutoTip(getContext(), editMobile, editPsw, editPsw2, editCode);
        if (strEdit == null) {
            return;
        }

        if (!EditUtil.checkSamePsw(getContext(), editPsw, editPsw2, DataConfig.PSW_MIN_LENGTH)) {
            return;
        }

//        if (!StringUtil.isMobile(strEdit[0])) {
//            showToast("请检查输入的手机号");
//            return;
//        }

        if (!clickCode) {
            showToast("请先获取验证码");
            return;
        }
        if (!cbProtocol.isChecked()) {
            showToast("请阅读注册协议后同意方可注册");
            return;
        }

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpRegister(strEdit[0], strEdit[1], strEdit[3], new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast(obj.optString("desc"));
                //注册成功后，在登录页显示该手机号
                EventBus.getDefault().post(new MessageEvent(MessageEvent.REGISTER_SUCCESS,
                        editMobile.getText().toString()));
                finish();
            }
        });

    }

    /**
     * 获取验证码
     *
     * @param editMobile
     */
    public void httpGetCode(EditText editMobile, final Button btnGetCode) {
        String[] strEdit = EditUtil.getEditsStringAutoTip(getContext(), editMobile);
        if (strEdit == null) {
            return;
        }
//        if (!StringUtil.isMobile(strEdit[0])) {
//            showToast("请检查输入的手机号");
//            return;
//        }
        //type:1：注册  2：找回密码
        HttpCenter.getInstance(getContext()).getUserHttpTool().httpGetCode(strEdit[0], 1, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast("获取验证码成功");
                clickCode = true;
                getCode(btnGetCode);
            }
        });

    }

    /**
     * 倒数
     *
     * @param btnGetCode
     */
    private void getCode(Button btnGetCode) {
        util.getCode(60, btnGetCode);
    }

    /**
     * 关闭倒数
     */
    public void closeReciprocal() {
        if (util != null) {
            util.closeReciprocal();
        }
    }

}
