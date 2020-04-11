package com.jm.xpproject.ui.setting.act;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.ui.setting.util.XPSettingUtil;

import butterknife.OnClick;

/**
 * 设置
 *
 * @author jinXiong.Xie
 */
public class SettingAct extends MyTitleBarActivity {

    private XPSettingUtil xpSettingUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "设置");
    }

    @Override
    public void initViewAndUtil() {
        xpSettingUtil = new XPSettingUtil(this);
    }

    @Override
    public void initNetLink() {

    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        IntentUtil.intentToActivity(context, SettingAct.class);
    }


    @OnClick({R.id.tv_alter_psw, R.id.tv_bind_phone, R.id.tv_suggestion, R.id.tv_about_us, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_alter_psw:
                AlterPswAct.actionStart(this, UserData.getInstance().getMobile());
                break;
            case R.id.tv_bind_phone:
                String strMobile = UserData.getInstance().getMobile();
                if (TextUtils.isEmpty(strMobile)) {
                    BindMobileAct.actionStart(this);
                } else {
                    BindMobileSuccessAct.actionStart(this, strMobile);
                }
                break;
            case R.id.tv_suggestion:
                SubmitSuggestAct.actionStart(this);
                break;
            case R.id.tv_about_us:
                AboutUsAct.actionStart(this);
                break;
            case R.id.tv_logout:
                xpSettingUtil.logout(getSessionId());
                break;
            default:
                break;
        }
    }


}
