package com.jm.xpproject.ui.login.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.core.common.tools.base.ReciprocalUtil;
import com.jm.core.common.tools.click.ClickUtils;
import com.jm.core.common.tools.image.GlideUtil;
import com.jm.core.common.tools.net.ImageCache;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.GlobalConstant;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.ui.login.act.LoginAct;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import java.io.File;

/**
 * 基于小跑的广告页的工具类
 *
 * @author jinXiong.Xie
 */

public class XPAdvertisingUtil extends XPBaseUtil {

    private ReciprocalUtil util;

    public XPAdvertisingUtil(Context context) {
        super(context);
    }

    /**
     * 显示广告页
     *
     * @param imageView
     */
    public void showAdView(final ImageView imageView) {
        ImageCache.requestImageCache(getContext(), GlobalConstant.ADVERTISING_VIEW, new ImageCache.RequestImageCacheCallBack() {
            @Override
            public void noCache() {

            }

            @Override
            public void loadCacheSuccess(File file, final String link) {
                GlideUtil.loadImage(getContext(), file, imageView);
                if (!TextUtils.isEmpty(link)) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            IntentUtil.intentToBrowser(getContext(), link);
                        }
                    });
                }
            }
        });

    }

    /**
     * 倒数
     *
     * @param tvNum
     */
    public void countdown(final TextView tvNum) {
        if (util == null) {
            util = new ReciprocalUtil();
        }
        util.reciprocal(3, new ReciprocalUtil.OnGetCodeCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCode(int num) {
                tvNum.setText("跳过" + num + "s");

            }

            @Override
            public void onFinish() {
                turnToNextActivity();
            }
        });
    }


    private void turnToNextActivity() {
        //判断是否已经登录
        if (!TextUtils.isEmpty(getSessionIdText())) {
            UserData userData = UserData.getInstance();
            if (userData.getRyToken() == null) {
                turnToLogin();
                finish();
                return;
            }
//            connectRongIM(GuidePagesAct.this, userData.getRyToken());

            DataConfig.turnToMain(getContext());
//            MainAct.actionStart(getContext());
        } else {
            turnToLogin();
        }
        finish();

    }

    private void turnToLogin() {
        if (DataConfig.NOT_JUMP_OVER_LOGIN) {
            LoginAct.actionStart(getContext());
        } else {
            DataConfig.turnToMain(getContext());
        }
    }

    /**
     * 关闭倒数
     */
    public void closeReciprocal() {
        if (util != null) {
            util.closeReciprocal();
        }
    }

    public void clickToNext() {
        if (ClickUtils.isFastClick()) {
            if (util != null) {
                util.closeReciprocal();
            }
            turnToNextActivity();
        }
    }
}
