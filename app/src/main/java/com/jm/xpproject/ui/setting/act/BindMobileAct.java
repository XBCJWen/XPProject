package com.jm.xpproject.ui.setting.act;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.ui.setting.util.XPBindMobileUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定手机号
 *
 * @author jinXiong.Xie
 */
public class BindMobileAct extends MyTitleBarActivity {

    @BindView(R.id.edit_mobile)
    EditText editMobile;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.tv_code)
    TextView tvCode;

    private XPBindMobileUtil xpBindMobileUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_bind_mobile;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "绑定手机号码");
    }

    @Override
    public void initViewAndUtil() {
        xpBindMobileUtil = new XPBindMobileUtil(this);
    }

    @Override
    public void initNetLink() {

    }


    @OnClick({R.id.tv_code, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                requestCode();
                break;
            case R.id.tv_save:
                bindMobile();
                break;
            default:
                break;
        }
    }

    /**
     * 绑定手机号
     */
    private void bindMobile() {
        xpBindMobileUtil.bindMobile(getSessionId(), editMobile, editCode);
    }


    /**
     * 获取验证码功能
     */
    private void requestCode() {

        xpBindMobileUtil.requestCode(editMobile, tvCode);
    }


    @Override
    protected void onDestroy() {
        xpBindMobileUtil.closeRequestCode();
        super.onDestroy();
    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Bundle bundle = new Bundle();
        IntentUtil.intentToActivity(context, BindMobileAct.class, bundle);
    }
}
