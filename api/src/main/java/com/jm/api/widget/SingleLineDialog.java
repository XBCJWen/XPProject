package com.jm.api.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jm.api.R;
import com.jm.core.common.tools.base.PixelsTools;
import com.jm.core.common.tools.base.ReciprocalUtil;
import com.jm.core.common.widget.dialog.MyCustomDialog;

/**
 * 单行的提示框
 *
 * @author jinXiong.Xie
 */

public class SingleLineDialog {

    private MyCustomDialog myCustomDialog;

    /**
     * 初始化dialog
     *
     * @param context
     * @param strMessage
     * @return
     */
    public SingleLineDialog initDialog(Context context, String strMessage) {

//        myCustomDialog = new MyUniversalDialog(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_single_line, null);
        if (view == null) {
            return this;
        }

        myCustomDialog = new MyCustomDialog.Builder(context).view(view).gravity(MyCustomDialog.DialogGravity.CENTER)
                .setWidth((int) (PixelsTools.getWidthPixels(context)*0.8)).build();


        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        if (tvMessage != null) {
            tvMessage.setText(strMessage);
        }

        myCustomDialog.setCancelable(true);
        myCustomDialog.setCanceledOnTouchOutside(true);

        return this;
    }

    /**
     * 显示
     */
    public void show() {
        if (myCustomDialog != null) {
            myCustomDialog.show();
        }
    }

    /**
     * 显示
     */
    public void showNoClose() {
        if (myCustomDialog != null) {

            myCustomDialog.setCancelable(false);
            myCustomDialog.setCanceledOnTouchOutside(false);

            myCustomDialog.show();
        }
    }

    /**
     * 显示(延迟关闭)
     */
    public void showDelayClose(int time) {
        if (myCustomDialog != null) {

            myCustomDialog.setCancelable(false);
            myCustomDialog.setCanceledOnTouchOutside(false);

            myCustomDialog.show();

            final ReciprocalUtil reciprocalUtil = new ReciprocalUtil();
            reciprocalUtil.reciprocal(time, new ReciprocalUtil.OnGetCodeCallBack() {
                @Override
                public void onStart() {

                }

                @Override
                public void onCode(int num) {

                }

                @Override
                public void onFinish() {
                    myCustomDialog.dismiss();
                    reciprocalUtil.closeAll();
                }
            });
        }
    }

    /**
     * 关闭
     */
    public void dismiss() {
        if (myCustomDialog != null) {
            myCustomDialog.dismiss();
        }
    }
}
