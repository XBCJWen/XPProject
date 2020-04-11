package com.jm.core.common.widget.edittext;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义一个具有清除功能的editText
 *
 * @author xiejinxiong
 */
public class MyClearEditText extends AppCompatEditText implements TextWatcher {
	/**
	 * 储存清除的图片
	 */
	private Drawable drawClear;

	public MyClearEditText(Context context) {
		super(context);
		this.addTextChangedListener(this);
	}

	public MyClearEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.addTextChangedListener(this);
	}

	public MyClearEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.addTextChangedListener(this);

	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
								  Rect previouslyFocusedRect) {
		initClearDrawable();
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		// 判断焦点失去和得到时的操作
		if (focused && !this.getText().toString().equals("")) {
			this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1],
					drawClear, getCompoundDrawables()[3]);
		} else if (focused) {
			this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1],
					null, getCompoundDrawables()[3]);
		} else {
			this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1],
					null, getCompoundDrawables()[3]);
		}
	}

	/**
	 * 初始化清除的图片
	 */
	private void initClearDrawable() {
		if (getCompoundDrawables()[2] != null) {
			drawClear = getCompoundDrawables()[2];
		}


	}

	@Override
	public void onTextChanged(CharSequence text, int start, int lengthBefore,
							  int lengthAfter) {
		initClearDrawable();
		// 判断输入框中是否有内容
		if (text.length() > 0) {
			this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1],
					drawClear, getCompoundDrawables()[3]);
		} else {
			this.setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1],
					null, getCompoundDrawables()[3]);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		initClearDrawable();
		// 判断触碰是否结束
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// 判断所触碰的位置是否为清除的按钮
			if (event.getX() > (getWidth() - getTotalPaddingRight())
					&& event.getX() < (getWidth() - getPaddingRight())) {
				// 将editText里面的内容清除
				setText("");
			}
		}
		return super.onTouchEvent(event);
	}
}
