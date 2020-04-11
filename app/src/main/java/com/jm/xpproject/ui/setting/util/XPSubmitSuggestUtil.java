package com.jm.xpproject.ui.setting.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.core.common.tools.base.EditUtil;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.json.JSONObject;

/**
 * 基于小跑的反馈页面的工具类
 *
 * @author jinXiong.Xie
 */

public class XPSubmitSuggestUtil extends XPBaseUtil {

    /**
     * 最大的输入数
     */
    private final int MAX_LENGTH = 1000;

    public XPSubmitSuggestUtil(Context context) {
        super(context);
    }

    /**
     * 配置输入框
     *
     * @param editMessage
     * @param tvSumFont
     */
    public void initEditText(EditText editMessage, TextView tvSumFont) {
        EditUtil.setMaxLength(editMessage, tvSumFont, MAX_LENGTH);
    }


    public void httpSubmitFeedBack(String sessionId, EditText editMessage, EditText editContact) {
        String strMessage = editMessage.getText().toString();
        String strContent = editContact.getText().toString();

        if (TextUtils.isEmpty(strMessage)) {
            showToast("请输入你的任何意见或问题");
            return;
        }
//    if (TextUtils.isEmpty(strContant)){
//      showToast("请输入你的联系方式");
//      return;
//    }

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpSubmitFeedBack(sessionId, strMessage, strContent, new LoadingErrorResultListener(getContext()) {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                showDesc(obj);
                finish();
            }
        });

    }
}
