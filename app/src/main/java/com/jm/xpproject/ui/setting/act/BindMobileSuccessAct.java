package com.jm.xpproject.ui.setting.act;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.core.common.tools.base.StringUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.config.MessageEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机号成功
 *
 * @author jinXiong.Xie
 */
public class BindMobileSuccessAct extends MyTitleBarActivity {

    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    private String mobile;

    @Override
    protected int layoutResID() {
        return R.layout.activity_bind_mobile_success;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "绑定手机号码");
    }


    @Override
    protected void initData(Bundle data) {
        super.initData(data);

        mobile = data.getString("mobile");
    }

    @Override
    public void initViewAndUtil() {
        if (TextUtils.isEmpty(mobile)) {
            showDataErrorToast();
            return;
        }

        tvMobile.setText("您已经绑定手机号" + StringUtil.hideMobile(mobile) + "成功，账号安全。");
    }

    @Override
    public void initNetLink() {

    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context, String strMobile) {
        Bundle bundle = new Bundle();
        bundle.putString("mobile", strMobile);
        IntentUtil.intentToActivity(context, BindMobileSuccessAct.class, bundle);
    }

    @Override
    public void onEventCallBack(MessageEvent event) {
        super.onEventCallBack(event);

        if (event.getId() == MessageEvent.BIND_MOBILE_SUCCESS) {
            finish();
        }
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        BindMobileAct.actionStart(this);
    }
}
