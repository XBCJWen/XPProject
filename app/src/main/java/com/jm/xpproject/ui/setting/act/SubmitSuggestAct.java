package com.jm.xpproject.ui.setting.act;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.ui.setting.util.XPSubmitSuggestUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提交意见
 *
 * @author jinXiong.Xie
 */
public class SubmitSuggestAct extends MyTitleBarActivity {

    @BindView(R.id.editMessage)
    EditText editMessage;
    @BindView(R.id.tvSumFont)
    TextView tvSumFont;
    @BindView(R.id.edit_contact)
    EditText editContact;

    private XPSubmitSuggestUtil xpSubmitSuggestUtil;

    @Override
    protected int layoutResID() {
        return R.layout.activity_submit_suggest;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "提交意见");
    }

    @Override
    public void initViewAndUtil() {
        xpSubmitSuggestUtil = new XPSubmitSuggestUtil(this);

        xpSubmitSuggestUtil.initEditText(editMessage, tvSumFont);
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
        Bundle bundle = new Bundle();
        IntentUtil.intentToActivity(context, SubmitSuggestAct.class, bundle);
    }


    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        xpSubmitSuggestUtil.httpSubmitFeedBack(getSessionId(), editMessage, editContact);
    }


}
