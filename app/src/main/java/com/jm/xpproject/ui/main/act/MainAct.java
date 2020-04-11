package com.jm.xpproject.ui.main.act;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.api.appupdater.ApkInstallUtil;
import com.jm.api.util.IntentUtil;
import com.jm.api.util.SharedAccount;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.bean.GuideIndexBean;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.config.change.UIConfig;
import com.jm.xpproject.ui.main.util.XPGuideIndexUtil;
import com.jm.xpproject.ui.main.util.XPMainUtil;

import butterknife.BindView;

/**
 * 主页（动态生成导航栏）
 *
 * @author jinXiong.Xie
 */
public class MainAct extends MyTitleBarActivity {

    @BindView(R.id.ll_guide_layout)
    LinearLayout llGuideLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    /**
     * 导航栏的功能名称
     */
    private TextView[] tvGuideName = new TextView[UIConfig.GUIDE_NAME.length];
    /**
     * 导航栏的功能图标
     */
    private ImageView[] imgGuideIcon = new ImageView[UIConfig.GUIDE_NORMAL_ICON.length];
    /**
     * 导航栏的功能布局
     */
    private LinearLayout[] llGuide = new LinearLayout[UIConfig.GUIDE_FGM.length];
    /**
     * 导航栏的未读数
     */
    private TextView[] tvGuideUnReadNum = new TextView[UIConfig.GUIDE_FGM.length];

    private Fragment[] fgmList = new Fragment[UIConfig.GUIDE_FGM.length];


    private XPMainUtil xpMainUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        hideTitleBar();
    }

    @Override
    public void initViewAndUtil() {
        fillUserData();
        removeReplaceView();

        xpMainUtil = new XPMainUtil(this);

        XPGuideIndexUtil guideIndexUtil = new XPGuideIndexUtil(new GuideIndexBean(this, tvGuideName, imgGuideIcon, llGuide, tvGuideUnReadNum, llGuideLayout, viewPager, fgmList));
        guideIndexUtil.initGuide();

        checkUpdate();
    }

    private void fillUserData() {
        UserData.getInstance().setHead(SharedAccount.getInstance(getActivity()).getUserAvatar());
        UserData.getInstance().setNick(SharedAccount.getInstance(getActivity()).getUserName());
    }

    @Override
    public void initNetLink() {

    }

    /**
     * 3分钟检测一下更新
     */
    private void checkUpdate() {
        xpMainUtil.checkUpdate();
    }

    public static void actionStart(Context context) {
//        IntentUtil.intentToActivity(context, MainAct.class);
        DataConfig.turnToMain(context);
    }

    @Override
    public void onBackPressed() {
        IntentUtil.intentToDesktop(this);
    }


    @Override
    protected void onRestart() {
        ApkInstallUtil.openAPKFile(this);
        super.onRestart();
    }

    @Override
    public void onEventCallBack(MessageEvent event) {
        super.onEventCallBack(event);

        if (event.getId() == MessageEvent.DOWNLOAD_FAILED) {
            xpMainUtil.closeMustUpdateDialog();
        }
    }
}
