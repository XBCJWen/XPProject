package com.jm.core.common.widget.textview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 文本删除线效果
 */
public class LineTextView extends TextView {

    public LineTextView(Context context) {
        super(context);
        setDeleteLine();
    }

    public LineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDeleteLine();
    }

    public LineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDeleteLine();
    }

    private void setDeleteLine() {
        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
        getPaint().setAntiAlias(true);// 抗锯齿
    }


}
