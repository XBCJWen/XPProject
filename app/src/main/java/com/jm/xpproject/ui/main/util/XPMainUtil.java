package com.jm.xpproject.ui.main.util;

import android.content.Context;

import com.jm.core.common.tools.base.ReciprocalUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.utils.xp.XPBaseUtil;
import com.jm.xpproject.utils.xp.XPVersionUtil;

/**
 * 基于小跑的主页工具类
 *
 * @author jinXiong.Xie
 */

public class XPMainUtil extends XPBaseUtil {

    private XPVersionUtil xpVersionUtil;

    public XPMainUtil(Context context) {
        super(context);
    }

    /**
     * 3分钟检测一下更新
     */
    public void checkUpdate() {
        if (!DataConfig.AUTO_CHECK_VERSION) {
            return;
        }
        ReciprocalUtil reciprocalUtil = new ReciprocalUtil();
        reciprocalUtil.cycle(DataConfig.CHECK_VERSION_DELAY_TIME, new ReciprocalUtil.OnCycleCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCycle() {
                if (xpVersionUtil == null) {
                    xpVersionUtil = new XPVersionUtil(getContext(), getContext().getResources().getString(R.string.app_name), false, false);
                }

                xpVersionUtil.checkVersion();
            }
        });
    }

    /**
     * 关闭正在更新对话框
     */
    public void closeMustUpdateDialog() {
        if (xpVersionUtil != null) {
            xpVersionUtil.closeMustUpdateDialog();
        }
    }
}
