package com.jm.xpproject.http;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jm.xpproject.http.tool.UserHttpTool;

import static com.jm.xpproject.http.HttpTool.getContext;

/**
 * Http连接中转站
 *
 * @author jinXiong.Xie
 */

public class HttpCenter {

    private static HttpTool httpTool;
    private static HttpCenter httpCenter;

    private HttpCenter(Context context) {
        httpTool = HttpTool.getInstance(context);
    }

    public static HttpCenter getInstance(Context context) {
        Context beforeContext = getContext();
        if (httpCenter == null || httpTool == null || getContext() == null || getContext() instanceof Application) {
            httpCenter = new HttpCenter(context);
        } else if (beforeContext instanceof Activity) {
            //假如绑定的Activity被销毁，则重新创建
            Activity activity = (Activity) beforeContext;
            if (activity.isFinishing()) {
                httpCenter = new HttpCenter(context);
            }
        }
        return httpCenter;
    }

    public UserHttpTool getUserHttpTool() {
        return UserHttpTool.getInstance(httpTool);
    }
}
