package com.jm.xpproject.ui.setting.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.core.common.tools.base.EditUtil;
import com.jm.core.common.tools.base.ReciprocalUtil;
import com.jm.core.common.tools.base.StringUtil;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.json.JSONObject;

/**
 * 基于小跑的更改密码的工具类
 *
 * @author jinXiong.Xie
 */

public class XPAlterPswUtil extends XPBaseUtil {

    /**
     * 判断是否点击过获取验证码，false：未点击，true：已点击
     */
    private boolean clickCode = false;

    private ReciprocalUtil reciprocalUtil;

    public XPAlterPswUtil(Context context) {
        super(context);

        reciprocalUtil = new ReciprocalUtil();
    }

    /**
     * 显示手机号码
     *
     * @param tvMobile
     * @param strMobile
     */
    public void showMobile(TextView tvMobile, String strMobile) {
        if (TextUtils.isEmpty(strMobile)) {
            showDataErrorToast();
            finish();
            return;
        }

        tvMobile.setText("手机号码：" + StringUtil.hideMobile(strMobile));
    }


    /**
     * 获取验证码功能
     */
    public void httpGetCode(final TextView tvCode, String strMobile) {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpGetCode(strMobile, 2, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast("获取验证码成功");
                clickCode = true;
                getCode(tvCode);
            }
        });
    }


    /**
     * 上传数据
     */
    public void httpSubmitData(EditText editPswOld, EditText editPsw, EditText editPsw2, EditText editCode, String sessionId) {
        String[] strEdit = EditUtil
                .getEditsStringAutoTip(getContext(), editPswOld, editPsw, editPsw2, editCode);
        if (strEdit == null) {
            return;
        }

        if (!EditUtil.checkSamePsw(getContext(), editPswOld, editPsw, editPsw2, DataConfig.PSW_MIN_LENGTH)) {
            return;
        }

        if (!clickCode) {
            showToast("请先获取验证码");
            return;
        }

        httpAlterPsw(sessionId, strEdit, editPsw);
    }


    private void httpAlterPsw(String sessionId, String[] strEdit, final EditText editPsw) {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpAlterPsw(sessionId, strEdit[1], strEdit[0], strEdit[3], new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showToast(obj.optString("desc"));
                finish();
            }
        });
    }

    private void getCode(TextView tvCode) {
        reciprocalUtil.getCode(60, tvCode);
    }

    public void closeReciprocal() {
        if (reciprocalUtil != null) {
            reciprocalUtil.closeReciprocal();
        }
    }
}
