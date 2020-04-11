
package com.jm.core.common.widget.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.jm.core.R;

/**
 * 带删除按钮的输入框
 */
public class PKClearEditText extends EditText implements View.OnTouchListener, View.OnFocusChangeListener {

    final Drawable mClear = getResources().getDrawable(R.drawable.editdelete);

    /**
     * @param context
     */
    public PKClearEditText(Context context) {
        super(context);
        initCCPClearEditTextRef();
    }

    /**
     * @param context
     * @param attrs
     */
    public PKClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCCPClearEditTextRef();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PKClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCCPClearEditTextRef();
    }

    private void initCCPClearEditTextRef() {

        mClear.setBounds(0, 0, mClear.getIntrinsicHeight(), mClear.getIntrinsicHeight());

        doClearDrawable();
        setHeight(mClear.getIntrinsicHeight() + 5 * getResources().getDimensionPixelSize(R.dimen.OneDPPadding));
        setOnTouchListener(this);
        addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doClearDrawable();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setOnFocusChangeListener(this);
    }

    /**
     *
     */
    private void doClearDrawable() {
        if ("".equals(getText().toString()) || !isFocused()) {
            setClearDrawableNull();
            return;
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mClear, getCompoundDrawables()[3]);
    }

    /**
     *
     */
    private void setClearDrawableNull() {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (getCompoundDrawables()[2] == null) {
            return false;
        }

        if ((event.getAction() != MotionEvent.ACTION_UP)
                || event.getX() <= (getWidth() - getPaddingRight() - mClear.getIntrinsicWidth())) {
            return false;

        }
        getText().clear();

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        doClearDrawable();
    }
}
