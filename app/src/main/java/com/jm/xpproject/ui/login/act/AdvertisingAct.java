package com.jm.xpproject.ui.login.act;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.ui.login.util.XPAdvertisingUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告页
 *
 * @author jinXiong.Xie
 */
public class AdvertisingAct extends MyTitleBarActivity {

    @BindView(R.id.img_bg)
    ImageView imageView;
    @BindView(R.id.tv_over)
    TextView tvNum;

    private XPAdvertisingUtil xpAdvertisingUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_advertising;
    }

    @Override
    protected void initTitle() {
        hideTitleBar();
    }

    @Override
    public void initViewAndUtil() {

        xpAdvertisingUtil = new XPAdvertisingUtil(this);

        countdown();

        showAdView();
    }

    @Override
    public void initNetLink() {

    }

    private void countdown() {
        xpAdvertisingUtil.countdown(tvNum);
    }

    private void showAdView() {
        xpAdvertisingUtil.showAdView(imageView);
    }


    @OnClick(R.id.tv_over)
    public void onViewClicked() {
        xpAdvertisingUtil.clickToNext();
    }


    @Override
    protected void onDestroy() {
        xpAdvertisingUtil.closeReciprocal();
        super.onDestroy();
    }

    public static void actionStart(Context context) {
        IntentUtil.intentToActivity(context, AdvertisingAct.class);
    }
}
