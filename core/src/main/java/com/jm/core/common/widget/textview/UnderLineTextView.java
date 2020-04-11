package com.jm.core.common.widget.textview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 文本下划线效果
 */
public class UnderLineTextView extends TextView {

    public UnderLineTextView(Context context) {
        super(context);
        setDeleteLine();
    }

    public UnderLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDeleteLine();
    }

    public UnderLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDeleteLine();
    }

    private void setDeleteLine() {
        getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        getPaint().setAntiAlias(true);// 抗锯齿
    }


}
