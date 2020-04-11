package com.jm.core.common.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jm.core.R;

public class LoadingDialog {

    public Dialog mDialog;
    public TextView dialog_title;
    public TextView dialog_message;
    public TextView positive;
    public TextView negative;
    public ImageView imageView;
    private AnimationDrawable animationDrawable;
    private Context context;

    public LoadingDialog(Context context, String title, String message) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.d_loading, null);
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        imageView = (ImageView) view.findViewById(R.id.progress_view);
        imageView.setImageResource(R.drawable.loading_animation);
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

    }

    public void show() {
        if (mDialog != null && isNormal()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && isNormal()) {
            mDialog.dismiss();
        }
    }

    public boolean isNormal() {

        if (context != null && context instanceof Activity && !((Activity) context).isFinishing()) {
            return true;
        }
        return false;
    }

}
