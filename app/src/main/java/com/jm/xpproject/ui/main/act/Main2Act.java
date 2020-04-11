package com.jm.xpproject.ui.main.act;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.api.appupdater.ApkInstallUtil;
import com.jm.api.util.IntentUtil;
import com.jm.api.util.SharedAccount;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.ui.main.util.XPMain2Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页（静态导航栏）
 *
 * @author jinXiong.Xie
 */
public class Main2Act extends MyTitleBarActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.tv_four)
    TextView tvFour;
    @BindView(R.id.tv_unread_num_one)
    TextView tvUnreadNumOne;
    @BindView(R.id.tv_unread_num_two)
    TextView tvUnreadNumTwo;
    @BindView(R.id.tv_unread_num_three)
    TextView tvUnreadNumThree;
    @BindView(R.id.tv_unread_num_four)
    TextView tvUnreadNumFour;


    private XPMain2Util xpMain2Util;

    @Override
    protected int layoutResID() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initTitle() {
        hideTitleBar();
    }

    @Override
    public void initViewAndUtil() {

        fillUserData();

        removeReplaceView();

        xpMain2Util = new XPMain2Util(this, viewPager, ivOne, tvOne, ivTwo, tvTwo, ivThree, tvThree, ivFour, tvFour);

        xpMain2Util.changeTabBar(0);
        xpMain2Util.initViewPager();

        checkUpdate();
    }

    private void fillUserData() {
        UserData.getInstance().setHead(SharedAccount.getInstance(getActivity()).getUserAvatar());
        UserData.getInstance().setNick(SharedAccount.getInstance(getActivity()).getUserName());
    }

    @Override
    public void initNetLink() {

    }


    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_one:
                xpMain2Util.changeViewPagerIndex(0);
                break;
            case R.id.ll_two:
                xpMain2Util.changeViewPagerIndex(1);
                break;
            case R.id.ll_three:
                xpMain2Util.changeViewPagerIndex(2);
                break;
            case R.id.ll_four:
                xpMain2Util.changeViewPagerIndex(3);
                break;
            default:
                break;
        }
    }


    /**
     * 3分钟检测一下更新
     */
    private void checkUpdate() {
        xpMain2Util.checkUpdate();
    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Bundle bundle = new Bundle();
        IntentUtil.intentToActivity(context, Main2Act.class, bundle);
    }

    @Override
    public void onBackPressed() {
        IntentUtil.intentToDesktop(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        ApkInstallUtil.openAPKFile(this);
        super.onRestart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getId() == MessageEvent.DOWNLOAD_FAILED) {
            xpMain2Util.closeMustUpdateDialog();
        }
    }
}
