package com.jm.xpproject.ui.login.act;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.jm.api.util.IntentUtil;
import com.jm.api.util.UMengUtil;
import com.jm.core.common.tools.base.EditUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.ui.login.util.XPLoginUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录页
 *
 * @author jinXiong.Xie
 */
public class LoginAct extends MyTitleBarActivity {


    @BindView(R.id.edit_mobile)
    EditText editMobile;
    @BindView(R.id.edit_psw)
    EditText editPsw;

    private XPLoginUtil xpLoginUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        hideTitleBar();
    }

    @Override
    public void initViewAndUtil() {

        clearOtherLoginData();

        xpLoginUtil = new XPLoginUtil(this);


        //自动填充手机号
        xpLoginUtil.autoFillMobile(editMobile);
    }

    /**
     * 重新获取第三方登录信息
     */
    private void clearOtherLoginData() {

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
    }

    @Override
    public void initNetLink() {

    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_find_psw, R.id.img_qq, R.id.img_we_chat, R.id.img_wei_bo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkLogin();
                break;
            case R.id.tv_register:
                RegisterAct.actionStart(this);
                break;
            case R.id.tv_find_psw:
                if (editMobile != null) {
                    FindPswAct.actionStart(this, editMobile.getText().toString());
                }
                break;
            case R.id.img_qq:
                xpLoginUtil.otherTypeLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.img_we_chat:
                xpLoginUtil.otherTypeLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.img_wei_bo:
                xpLoginUtil.otherTypeLogin(SHARE_MEDIA.SINA);
                break;
            default:
                break;
        }
    }

    /**
     * 判断登录条件
     */
    private void checkLogin() {
        xpLoginUtil.checkLogin(editMobile, editPsw);
    }

    public static void actionStart(Context context) {
        IntentUtil.intentToActivity(context, LoginAct.class);
    }


    @Override
    protected void onDestroy() {
        UMengUtil.release(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        IntentUtil.intentToDesktop(this);
    }

    @Override
    public void onEventCallBack(MessageEvent event) {
        super.onEventCallBack(event);
        if (event.getId() == MessageEvent.REGISTER_SUCCESS) {
            String strPhone = (String) event.getMessage()[0];
            EditUtil.setText(editMobile, strPhone);
            editPsw.setText("");
        }
        if (event.getId() == MessageEvent.FIND_PSW_SUCCESS) {
            String strPhone = (String) event.getMessage()[0];
            EditUtil.setText(editMobile, strPhone);
            editPsw.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
}
