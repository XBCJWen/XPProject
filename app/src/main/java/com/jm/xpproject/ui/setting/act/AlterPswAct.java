package com.jm.xpproject.ui.setting.act;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.ui.setting.util.XPAlterPswUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 *
 * @author jinXiong.Xie
 */
public class AlterPswAct extends MyTitleBarActivity {

    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.edit_psw_old)
    EditText editPswOld;
    @BindView(R.id.edit_psw)
    EditText editPsw;
    @BindView(R.id.edit_psw2)
    EditText editPsw2;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.tv_code)
    TextView tvCode;

    /**
     * 手机号码
     */
    private String strMobile;


    private XPAlterPswUtil xpAlterPswUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_alter_psw;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "修改密码");
    }

    @Override
    public void initViewAndUtil() {

        xpAlterPswUtil = new XPAlterPswUtil(this);
        xpAlterPswUtil.showMobile(tvMobile, strMobile);
    }

    @Override
    public void initNetLink() {

    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);

        strMobile = bundle.getString("mobile");
    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context, String strMobile) {
        Bundle bundle = new Bundle();
        bundle.putString("mobile", strMobile);
        IntentUtil.intentToActivity(context, AlterPswAct.class, bundle);
    }


    @OnClick({R.id.tv_code, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                xpAlterPswUtil.httpGetCode(tvCode, strMobile);
                break;
            case R.id.tv_save:
                httpSubmitData();
                break;
            default:
                break;
        }
    }

    /**
     * 上传数据
     */
    private void httpSubmitData() {
        xpAlterPswUtil.httpSubmitData(editPswOld, editPsw, editPsw2, editCode, getSessionId());
    }


    @Override
    protected void onDestroy() {
        xpAlterPswUtil.closeReciprocal();
        super.onDestroy();
    }
}
