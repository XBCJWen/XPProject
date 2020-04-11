package com.jm.core.common.widget.textview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.jm.core.R;


/**
 * 有全文和收起的TextView ExpandableTextView
 *
 * @author chenguiquan
 */

@SuppressLint("AppCompatCustomView")
public class CollapsedTextView extends TextView {

    private Context context;
    private static final String TAG = CollapsedTextView.class.getName();

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    /**
     * 收起状态下的最大行数
     */
    private int maxLine = 6;
    /**
     * 截取后，文本末尾的字符串
     */
    private static final String ELLIPSE = "...";
    /**
     * 默认全文的Text
     */
    private static final String EXPANDEDTEXT = "全文";
    /**
     * 默认收起的text
     */
    private static final String COLLAPSEDTEXT = "收起";
    /**
     * 全文的text
     */
    private String expandedText = EXPANDEDTEXT;
    /**
     * 收起的text
     */
    private String collapsedText = COLLAPSEDTEXT;
    /**
     * 所有行数
     */
    private int allLines;
    /**
     * 是否是收起状态，默认收起
     */
    private boolean collapsed = true;
    /**
     * 真实的text
     */
    private String text;
    /**
     * 收起时实际显示的text
     */
    private CharSequence collapsedCs;
    /**
     * 全文和收起的点击事件处理
     */
    private ReadMoreClickableSpan viewMoreSpan = new ReadMoreClickableSpan();

    private MyUnderlineSpan underlineSpan = new MyUnderlineSpan();

    public CollapsedTextView(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    public CollapsedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public CollapsedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CollapsedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(context, attrs);
    }

    @Override
    public TextPaint getPaint() {
        return super.getPaint();
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CollapsedTextview);
            allLines = ta.getInt(R.styleable.CollapsedTextview_trimLines, 1);
            expandedText = ta.getString(R.styleable.CollapsedTextview_expandedText);
            if (TextUtils.isEmpty(expandedText)) {
                expandedText = EXPANDEDTEXT;
            }
            collapsedText = ta.getString(R.styleable.CollapsedTextview_collapsedText);
            if (TextUtils.isEmpty(collapsedText)) {
                collapsedText = COLLAPSEDTEXT;
            }
        }

    }

    public void setShowText(final String text) {
        this.text = text;
        if (allLines > 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver obs = getViewTreeObserver();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        obs.removeOnGlobalLayoutListener(this);
                    } else {
                        obs.removeGlobalOnLayoutListener(this);
                    }
                    TextPaint tp = getPaint();
                    float width = tp.measureText(text);
                    /* 计算行数 */
                    //获取显示宽度
                    int showWidth = getWidth() - getPaddingRight() - getPaddingLeft();
                    int lines = (int) (width / showWidth);
                    if (width % showWidth != 0) {
                        lines++;
                    }
                    allLines = (int) (tp.measureText(text + collapsedText) / showWidth);
                    if (lines > maxLine) {
                        int expect = text.length() / lines;
                        int end = 0;
                        int lastLineEnd = 0;
                        //...+expandedText的宽度，需要在最后一行加入计算
                        int expandedTextWidth = (int) tp.measureText(ELLIPSE + expandedText);
                        //计算每行显示文本数
                        for (int i = 1; i <= maxLine; i++) {
                            int tempWidth = 0;
                            if (i == maxLine) {

                                tempWidth = expandedTextWidth;
                            }
                            end += expect;
                            if (end > text.length()) {
                                end = text.length();
                            }
                            if (tp.measureText(text, lastLineEnd, end) > showWidth - tempWidth) {
                                //预期的第一行超过了实际显示的宽度
                                end--;
                                while (tp.measureText(text, lastLineEnd, end) > showWidth - tempWidth) {
                                    end--;
                                }
                            } else {
                                end++;
                                while (tp.measureText(text, lastLineEnd, end) < showWidth - tempWidth) {
                                    end++;
                                }
                                end--;
                            }
                            lastLineEnd = end;
                        }

                        SpannableStringBuilder s = new SpannableStringBuilder(text, 0, end)
                                .append(ELLIPSE)
                                .append(expandedText);

                        ForegroundColorSpan nameSpan_1 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color00FF4E));
                        s.setSpan(nameSpan_1, 0, s.length() - expandedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        collapsedCs = addClickableSpan(s, expandedText);
                        setText(collapsedCs);

                        setMovementMethod(LinkMovementMethod.getInstance());
                    } else {
                        setText(text);
                    }
                }
            });
            setText("");
        } else {
            setText(text);
        }
    }


    private CharSequence addClickableSpan(SpannableStringBuilder s, CharSequence trimText) {
        s.setSpan(viewMoreSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        s.setSpan(underlineSpan, s.length() - trimText.length(), s.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return s;
    }

    private class MyUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(ContextCompat.getColor(context, R.color.color1D2122));
            // 去掉下划线
            ds.setUnderlineText(false);
        }
    }

    private class ReadMoreClickableSpan extends ClickableSpan {
        @Override
        public void onClick(final View widget) {
            if (collapsed) {
                SpannableStringBuilder s = new SpannableStringBuilder(text).append(collapsedText);
                ForegroundColorSpan nameSpan_1 = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color00FF4E));
                s.setSpan(nameSpan_1, 0, s.length() - collapsedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                setText(addClickableSpan(s, collapsedText));
            } else {
                setText(collapsedCs);
            }
            collapsed = !collapsed;
        }
    }


}
