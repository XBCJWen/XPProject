package com.jm.xpproject.utils.xp;

import android.Manifest;
import android.content.Context;

import com.jm.api.util.PermissionTools;

import com.jm.core.common.http.okhttp.SimpleErrorResultListener;
import com.jm.core.common.tools.tools.GsonUtil;
import com.jm.xpproject.bean.VersionBean;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.listener.LoadingErrorResultListener;

import org.json.JSONObject;

/**
 * 基于小跑的更新工具类
 *
 * @author jinXiong.Xie
 */

public class XPVersionUtil extends XPBaseUtil {

    private XPCheckVersionUtil versionUtil;
    private Context context;
    private boolean showUpdateTip;
    private boolean showLoading;

    public XPVersionUtil(Context context, String appName) {
        this(context, appName, true, true);
    }

    public XPVersionUtil(Context context, String appName, boolean showUpdateTip, boolean showLoading) {
        super(context);
        versionUtil = new XPCheckVersionUtil(context, appName);
        this.context = context;
        this.showUpdateTip = showUpdateTip;
        this.showLoading = showLoading;
    }

    /**
     * 检测是否有更新的权限（储存权限）
     */
    public void checkVersion() {
        PermissionTools permissionTools = new PermissionTools(context);
        permissionTools.requestPermissionDefault(new PermissionTools.PermissionCallBack() {
            @Override
            public void onSuccess() {
                httpCheckVersion();
            }

            @Override
            public void onCancel() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 联网获取网络更新
     */
    private void httpCheckVersion() {
        if (showLoading) {
            HttpCenter.getInstance(context).getUserHttpTool().httpCheckVersion(new LoadingErrorResultListener(context) {
                @Override
                public void normal(int id, JSONObject obj, Object[] data) {
                    checkUpdate(obj);
                }
            });
        } else {
            HttpCenter.getInstance(context).getUserHttpTool().httpCheckVersion(new SimpleErrorResultListener() {
                @Override
                public void normal(int id, JSONObject obj, Object[] data) {
                    checkUpdate(obj);
                }
            });
        }
    }

    /**
     * 检测更新
     *
     * @param obj
     */
    private void checkUpdate(JSONObject obj) {
        JSONObject jsonObject = obj.optJSONObject("data");
        if (jsonObject == null) {
            if (showUpdateTip) {
                showToast("当前为最新版本，不需要更新");
            }
            return;
        }
        final VersionBean bean = GsonUtil.gsonToBean(jsonObject.toString(), VersionBean.class);
        versionUtil.checkUpdate(bean, showUpdateTip);
    }

    public void closeMustUpdateDialog() {
        if (versionUtil != null) {
            versionUtil.closeMustUpdateDialog();
        }
    }
}
