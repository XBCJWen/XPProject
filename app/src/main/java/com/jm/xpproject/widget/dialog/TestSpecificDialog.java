package com.jm.xpproject.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.jm.api.widget.MySpecificDialog;
import com.jm.xpproject.R;

public class TestSpecificDialog extends BaseSpecificDialog{
    public TestSpecificDialog(Context context) {
        super(context);
    }

    @Override
    public int requestLayoutId() {
        return R.layout.dialog_layout;
    }

    @Override
    public MySpecificDialog.Builder requestDialogBuilder() {
        return new MySpecificDialog.Builder(getContext()).strLeft("Left").strRight("Right")
                .myDialogCallBack(new MySpecificDialog.MyDialogCallBack() {
            @Override
            public void onLeftBtnFun(Dialog dialog) {
                showDataErrorToast();
            }

            @Override
            public void onRightBtnFun(Dialog dialog) {
                showDataErrorToast();
            }
        });
    }

    @Override
    public void initDialogView(View view) {
        view.findViewById(R.id.tv_left).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                showToast("Success");
                break;
            default:
        }
    }
}
