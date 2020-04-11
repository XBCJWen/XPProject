package com.jm.core.framework;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.core.R;

/**
 * 该类已被遗弃，暂不使用
 *
 * @author jinXiong.Xie
 * @deprecated Use {@link BaseTitleBarActivity} instead.
 */
@Deprecated
public abstract class BaseTitleActivity extends BaseActivity {

    /**
     * ActionBar的主布局
     */
    protected RelativeLayout actionBarRL;
    /**
     * ActionBar的左边按钮
     */
    protected ImageButton actionBarImgBtnLeft;
    /**
     * ActionBar的右边按钮
     */
    protected ImageButton actionBarImgBtnRight;
    /**
     * ActionBar的中间标题
     */
    protected TextView actionBarTVTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();

    }

    /**
     * 初始化UI
     */
    protected abstract void initUI();

    /**
     * 初始化Title
     */
    protected void initTitle(View view) {
        actionBarRL = (RelativeLayout) view;
        actionBarImgBtnLeft = (ImageButton) view
                .findViewById(R.id.actionbar_imgbtn_Left);
        actionBarImgBtnRight = (ImageButton) view
                .findViewById(R.id.actionbar_imgbtn_right);
        actionBarTVTitle = (TextView) view
                .findViewById(R.id.actionbar_tv_title);
    }

    /**
     * 获取ActionBar的主布局
     *
     * @return
     */
    public RelativeLayout getActionBarRL() {
        return actionBarRL;
    }

    /**
     * 获取ActionBar的左边按钮
     *
     * @return
     */
    public ImageButton getActionBarImgBtnLeft() {
        return actionBarImgBtnLeft;
    }

    /**
     * 获取ActionBar的右边按钮
     *
     * @return
     */
    public ImageButton getActionBarImgBtnRight() {
        return actionBarImgBtnRight;
    }

    /**
     * 获取ActionBar的中间标题
     *
     * @return
     */
    public TextView getActionBarTVTitle() {
        return actionBarTVTitle;
    }

}
