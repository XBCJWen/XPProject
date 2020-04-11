package com.jm.xpproject.ui.login.act;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.ui.common.act.ProtocolAct;
import com.jm.xpproject.ui.login.util.XPRegisterUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 *
 * @author jinXiong.Xie
 */
public class RegisterAct extends MyTitleBarActivity {

    @BindView(R.id.edit_mobile)
    EditText editMobile;
    @BindView(R.id.edit_psw)
    EditText editPsw;
    @BindView(R.id.edit_psw2)
    EditText editPsw2;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.cb_protocol)
    CheckBox cbProtocol;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    private XPRegisterUtil xpRegisterUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "注册");
    }

    @Override
    public void initViewAndUtil() {
        xpRegisterUtil = new XPRegisterUtil(this);
    }

    @Override
    public void initNetLink() {

    }


    @OnClick({R.id.btn_get_code, R.id.tv_protocol, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                httpGetCode();
                break;
            case R.id.tv_protocol:
                ProtocolAct.actionStart(this, ProtocolAct.REGISTER);
                break;
            case R.id.btn_submit:
                httpRegister();
                break;
            default:
                break;
        }
    }

    private void httpRegister() {
        xpRegisterUtil.httpRegister(editMobile, editPsw, editPsw2, editCode, cbProtocol);

    }

    private void httpGetCode() {
        xpRegisterUtil.httpGetCode(editMobile, btnGetCode);

    }

    @Override
    protected void onDestroy() {
        xpRegisterUtil.closeReciprocal();
        super.onDestroy();
    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        IntentUtil.intentToActivity(context, RegisterAct.class);
    }
}
