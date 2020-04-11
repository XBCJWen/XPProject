package com.jm.xpproject.ui.login.act;

import android.content.Context;
import android.widget.ImageView;

import com.jm.api.util.IntentUtil;
import com.jm.api.util.PermissionTools;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.ui.login.util.XPSplashUtil;

import butterknife.BindView;

/**
 * 启动页
 *
 * @author jinXiong.Xie
 */
public class SplashAct extends MyTitleBarActivity {

    private static final String TAG = "SplashAct";
    @BindView(R.id.imageView)
    ImageView imageView;

    private XPSplashUtil xpSplashUtil;

    /**
     *加载布局
     */
    @Override
    protected int layoutResID() {
        return R.layout.activity_splash;
    }

    /**
     *隐藏标题栏
     */
    @Override
    protected void initTitle() {
        hideTitleBar();
    }

    @Override
    public void initViewAndUtil() {

        xpSplashUtil = new XPSplashUtil(this);
        /**
         *判断是否显示启动页
         */
        if (DataConfig.LOGIN_SHOW_START_VIEW) {
            showWelcomeImg();
        }

        checkAppPermission();
    }

    @Override
    public void initNetLink() {

    }

    /**
     *检查权限
     */
    private void checkAppPermission() {
        xpSplashUtil.checkAppPermission(new PermissionTools.PermissionCallBack() {
            @Override
            public void onSuccess() {
                if (xpSplashUtil.showGuideView()) {
                    return;
                }
                /**
                 *登录页面
                 */
                xpSplashUtil.autoLogin();
            }
            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    /**
     *传入引导頁图片
     */
    private void showWelcomeImg() {
        xpSplashUtil.showWelcomeImg(imageView);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        checkAppPermission();
    }

    public static void actionStart(Context context) {
        IntentUtil.intentToActivity(context, SplashAct.class);
    }
}
