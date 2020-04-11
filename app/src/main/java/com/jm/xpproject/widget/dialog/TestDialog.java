package com.jm.xpproject.widget.dialog;

import android.content.Context;
import android.view.View;

import com.jm.xpproject.R;

public class TestDialog extends BaseCustomDialog {

    public TestDialog(Context context) {
        super(context);
    }

    @Override
    public int requestLayoutId() {
        return R.layout.dialog_layout;
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
