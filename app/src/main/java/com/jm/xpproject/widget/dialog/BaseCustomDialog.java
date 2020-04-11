package com.jm.xpproject.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jm.core.common.tools.base.PixelsTools;
import com.jm.core.common.widget.dialog.MyCustomDialog;

/**
 * 基于XPBaseDialog的UniversalDialog封装
 *
 * @author jinXiong.Xie
 */
public abstract class BaseCustomDialog extends XPBaseDialog {

    public BaseCustomDialog(Context context) {
        super(context);
    }

    private MyCustomDialog.Builder builder;

    /**
     * 获取页面布局
     *
     * @return
     */
    public abstract int requestLayoutId();

    /**
     * 获取Dialog宽度
     *
     * @return
     */
    public int requestDialogWidth() {
        return (int) (PixelsTools.getWidthPixels(getContext()) * 0.8);
    }

    /**
     * 获取Dialog位置
     *
     * @return
     */
    public MyCustomDialog.DialogGravity requestDialogGravity() {
        return MyCustomDialog.DialogGravity.CENTER;
    }

    /**
     * 获取Dialog的背景透明度
     *
     * @return
     */
    public int requestDialogBgAlpha() {
        return -1;
    }

    @Override
    public void initDialog() {

        root = LayoutInflater.from(getContext()).inflate(requestLayoutId(), null);

        builder = new MyCustomDialog.Builder(getContext());
        builder.view(root).gravity(requestDialogGravity()).setWidth(requestDialogWidth()).alpha(requestDialogBgAlpha());

        dialog = builder.build();

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

    }

    @Override
    public void initDialogView() {

        initDialogView(root);
    }

    /**
     * 初始化布局内容
     *
     * @param view
     */
    public abstract void initDialogView(View view);
}
