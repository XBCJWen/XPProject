package com.jm.xpproject.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.jm.api.widget.MySpecificDialog;
import com.jm.core.common.tools.base.PixelsTools;

/**
 * 基于XPBaseDialog的SpecificDialog封装
 *
 * @author jinXiong.Xie
 */
public abstract class BaseSpecificDialog extends XPBaseDialog {

    public BaseSpecificDialog(Context context) {
        super(context);
    }

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
     * 获取Dialog的Builder
     *
     * @return
     */
    public abstract MySpecificDialog.Builder requestDialogBuilder();

    @Override
    public void initDialog() {

        root = LayoutInflater.from(getContext()).inflate(requestLayoutId(), null);

        dialog = requestDialogBuilder()
                .view(root)
                .width(requestDialogWidth())
                .buildDialog();

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
