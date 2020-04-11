package com.jm.xpproject.ui.login.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.jm.api.util.PermissionTools;
import com.jm.api.util.SharedAccount;
import com.jm.core.common.http.okhttp.SimpleCodeResultListener;
import com.jm.core.common.tools.image.GlideUtil;
import com.jm.core.common.tools.net.ImageCache;
import com.jm.core.common.tools.tools.GsonUtil;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.config.change.UIConfig;
import com.jm.xpproject.http.HttpCenter;
import com.jm.xpproject.http.api.BaseCloudApi;
import com.jm.xpproject.ui.login.act.AdvertisingAct;
import com.jm.xpproject.ui.login.act.GuidePagesAct;
import com.jm.xpproject.ui.login.act.LoginAct;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import static com.jm.xpproject.config.GlobalConstant.ADVERTISING_VIEW;
import static com.jm.xpproject.config.GlobalConstant.START_VIEW;


/**
 * 基于小跑的启动页工具类
 *
 * @author jinXiong.Xie
 */

public class XPSplashUtil extends XPBaseUtil {
    /**
     * 去登录页
     */
    private final int toLogin = 0;
    /**
     * 去广告页
     */
    private final int toAd = 1;
    /**
     * 去主页页
     */
    private final int toMain = 2;
    /**
     * 去引导页
     */
    private final int toGuide = 3;

    /**
     * 延迟进入时间
     */
    private final int DELAYED_TIME = 2000;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case toLogin:
                    if (DataConfig.NOT_JUMP_OVER_LOGIN){
                        LoginAct.actionStart(getContext());
                    }else {
                        DataConfig.turnToMain(getContext());
                    }
                    finish();
                    break;
                case toAd:
                    AdvertisingAct.actionStart(getContext());
                    finish();
                    break;
                case toMain:
                    DataConfig.turnToMain(getContext());
                    finish();
                    break;
                case toGuide:
                    GuidePagesAct.actionStart(getContext());
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    public XPSplashUtil(Context context) {
        super(context);
    }


    /**
     * 必须有全部权限才能进去
     */
    public void checkAppPermission(PermissionTools.PermissionCallBack permissionCallBack) {
        PermissionTools permissionTools = new PermissionTools(getContext());
        permissionTools.requestPermissionDefault(permissionCallBack, DataConfig.LOGIN_ALL_PERMISSION);
    }

    /**
     * 自动登录
     */
    public void autoLogin() {

        if (!DataConfig.LOGIN_SHOW_START_VIEW) {
            handler.sendEmptyMessage(toLogin);
            return;
        }

        requestUserInfo();
    }

    /**
     * 获取用户信息（是否保存了用户信息）
     *
     * @return
     */
    private boolean requestUserInfo() {
        if (DataConfig.SAVE_USER_INFO) {
            String strUserInfo = SharedAccount.getInstance(getContext()).getUserInfo();
            if (TextUtils.isEmpty(strUserInfo)) {
                advertisementFunc();
                return false;
            } else {
                UserData.getInstance().save(GsonUtil.gsonToBean(strUserInfo, UserData.class));
                UserData.getInstance().setMobile(SharedAccount.getInstance(getContext()).getMobile());
                advertisementFunc();
                return true;
            }
        }
        return false;

    }


    /**
     * 显示引导页
     */
    public boolean showGuideView() {
        if (DataConfig.LOGIN_SHOW_GUIDE_VIEW) {
            boolean isFirst = SharedAccount.getInstance(getContext()).isFirst();
            if (isFirst) {
                if (DataConfig.LOGIN_SHOW_ADVERTISING_VIEW) {
                    //缓存广告图
                    httpGetAdView();
                }
                if (DataConfig.LOGIN_SHOW_START_VIEW) {
                    handler.sendEmptyMessageDelayed(toGuide, DELAYED_TIME);
                } else {
                    handler.sendEmptyMessage(toGuide);
                }
                return true;
            }
        }
        return false;
    }


    /**
     * 广告功能
     */
    private void advertisementFunc() {
        if (DataConfig.LOGIN_SHOW_ADVERTISING_VIEW) {
            ImageCache.requestImageCache(getContext(), ADVERTISING_VIEW, new ImageCache.RequestImageCacheCallBack() {
                @Override
                public void noCache() {
                    noAdvertisingToNext();
                }

                @Override
                public void loadCacheSuccess(File file, String link) {
                    handler.sendEmptyMessageDelayed(toAd, DELAYED_TIME);
                }
            });

            httpGetAdView();
        } else {
            noAdvertisingToNext();
        }

    }

    /**
     * 无广告图进行跳转到下个页面
     */
    private void noAdvertisingToNext() {
        if (TextUtils.isEmpty(getSessionIdText())) {
            handler.sendEmptyMessageDelayed(toLogin, DELAYED_TIME);
        } else {
            handler.sendEmptyMessageDelayed(toMain, DELAYED_TIME);
        }
    }

    /**
     * 显示启动页
     */
    public void showWelcomeImg(final ImageView imageView) {
//        imageView.setImageResource(UIConfig.SPLASH_IMAGE_VIEW);
        ImageCache.requestImageCache(getContext(), START_VIEW, new ImageCache.RequestImageCacheCallBack() {
            @Override
            public void noCache() {
                imageView.setImageResource(UIConfig.SPLASH_IMAGE_VIEW);
            }

            @Override
            public void loadCacheSuccess(File file, String link) {
                GlideUtil.loadImage(getContext(), file, imageView);
            }
        });
        //寻找最新的图片资源
        httpGetWelcomeView();
    }

    /**
     * 获取启动页图片
     */
    private void httpGetWelcomeView() {

        HttpCenter.getInstance(getContext()).getUserHttpTool().httpGetWelcomeView(new SimpleCodeResultListener() {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                String strImg = BaseCloudApi.getFileUrl(obj.optString("data"));
                String strLink = obj.optString("link");
                saveImageCache(strImg, strLink, START_VIEW);
            }

            @Override
            public void error(int id, JSONObject obj, Object[] data) {

            }
        });

    }


    /**
     * 保存图片缓存
     *
     * @param strImg
     * @param strLink
     * @param mark
     */
    private void saveImageCache(String strImg, String strLink, String mark) {

        ImageCache.saveImageCache(getContext(), mark, strImg, strLink, null);
    }


    /**
     * 获取广告页图片
     */
    public void httpGetAdView() {
        HttpCenter.getInstance(getContext()).getUserHttpTool().httpGetAdView(new SimpleCodeResultListener() {
            @Override
            public void normal(int id, JSONObject obj, Object[] data) {
                JSONArray jsonArray = obj.optJSONArray("data");
                if (jsonArray == null || jsonArray.length() <= 0) {
                    return;
                }
                JSONObject jsonObject = jsonArray.optJSONObject(0);
                if (jsonObject == null) {
                    return;
                }
                final String strImg = BaseCloudApi.getFileUrl(jsonObject.optString("image"));
                final String strLink = jsonObject.optString("link");
                saveImageCache(strImg, strLink, ADVERTISING_VIEW);
            }

            @Override
            public void error(int id, JSONObject obj, Object[] data) {

            }
        });

    }

}
