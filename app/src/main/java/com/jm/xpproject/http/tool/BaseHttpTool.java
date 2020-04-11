package com.jm.xpproject.http.tool;

import android.text.TextUtils;

import com.jm.xpproject.http.HttpTool;
import com.jm.core.common.tools.log.LogUtil;

/**
 * 基础网络工具
 *
 * @author jinXiong.Xie
 */

public class BaseHttpTool {

    protected HttpTool httpTool;

    public BaseHttpTool(HttpTool httpTool) {
        this.httpTool = httpTool;
    }

    /**
     * 判断sessionId是否为null
     *
     * @param sessionId
     * @return
     */
    public boolean checkSessionIdNull(String sessionId) {
        if (TextUtils.isEmpty(sessionId)) {
            LogUtil.e("sessionId", "sessionId为null");
            return true;
        }
        return false;
    }

}
