package com.jm.xpproject.ui.login.act;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jm.api.util.IntentUtil;
import com.jm.core.common.tools.base.EditUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.ui.login.util.XPFindPswUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 *
 * @author jinXiong.Xie
 */
public class FindPswAct extends MyTitleBarActivity {

    private static final String TAG = "FindPswAct";


    @BindView(R.id.edit_mobile)
    EditText editMobile;
    @BindView(R.id.edit_psw)
    EditText editPsw;
    @BindView(R.id.edit_psw2)
    EditText editPsw2;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;

    private XPFindPswUtil xpFindPswUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_find_psw;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "忘记密码");
    }

    @Override
    public void initViewAndUtil() {
        xpFindPswUtil = new XPFindPswUtil(this);

    }

    @Override
    public void initNetLink() {

    }

    @Override
    protected void initData(Bundle bundle) {

        String mobile = bundle.getString("mobile");
        EditUtil.setText(editMobile, mobile);
    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context, String mobile) {
        Bundle bundle = new Bundle();
        bundle.putString("mobile", mobile);
        IntentUtil.intentToActivity(context, FindPswAct.class, bundle);
    }


    @OnClick({R.id.btn_get_code, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                httpGetCode();
                break;
            case R.id.btn_save:
                httpFindPsw();
                break;
            default:
                break;
        }
    }


    /**
     * 获取验证码功能
     */
    private void httpGetCode() {

        xpFindPswUtil.httpGetCode(editMobile, btnGetCode);
    }

    /**
     * 找回密码功能
     */
    private void httpFindPsw() {
        xpFindPswUtil.httpFindPsw(editMobile, editPsw, editPsw2, editCode);
    }

    @Override
    protected void onDestroy() {
        xpFindPswUtil.closeReciprocal();
        super.onDestroy();
    }
}
