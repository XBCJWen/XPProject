package com.jm.xpproject;

import android.os.Bundle;

import com.jm.xpproject.base.MyTitleBarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 类说明
 *
 * @author 作者名称
 * @date 日期
 */
public class TestAct extends MyTitleBarActivity {

    /**
     * @return
     */
    @Override
    protected int layoutResID() {
        return R.layout.activity_test;
    }

    @Override
    protected void initSetting() {
        super.initSetting();
    }

    @Override
    protected void initTitle() {
        setTitle(true, "标题");

        hideTitleBar();
        hideStatusBar();
    }

    @Override
    public void initViewAndUtil() {


    }

    @Override
    public void initNetLink() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private static String key = "12345678";

    @OnClick(R.id.btn_add)
    public void onViewClicked() {

    }
}
