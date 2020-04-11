package com.jm.core.common.tools.utils;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * 设置TextView局部文字变色与点击事件的简单封装
 *
 * @author ZhaoLei
 * @date 2018/8/27 9:16
 */
public class TextSetColorAndClickUtil {

    /**
     * 内容
     */
    private String content;

    /**
     * 为TextView设置一些必要属性
     */
    private TextView textView;

    private SpannableStringBuilder spannable;

    public TextSetColorAndClickUtil(TextView textView, String content) {
        this.content = content;
        this.textView = textView;

        //否则点击不生效
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //            去掉文字背景
        textView.setHighlightColor(Color.parseColor("#00ffffff"));
    }

    public SpannableStringBuilder setColorAndClick(int startIndex, int endIndex, final String color, final TextClickListener textClickListener) {
        return setColorAndClick(startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, color, textClickListener);
    }

    /**
     * @param startIndex        局部变色的起始下标
     * @param endIndex          局部变色的结束下标
     * @param spannableModel    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
     *                          Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
     *                          Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
     *                          Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
     * @param color
     * @param textClickListener
     */
    public SpannableStringBuilder setColorAndClick(int startIndex, int endIndex, int spannableModel, final String color, final TextClickListener textClickListener) {
        return setColorAndClick(startIndex, endIndex, spannableModel, Color.parseColor(color), textClickListener);
    }

    public SpannableStringBuilder setColorAndClick(int startIndex, int endIndex, final int color, final TextClickListener textClickListener) {
        return setColorAndClick(startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, color, textClickListener);
    }

    /**
     * @param startIndex        局部变色的起始下标
     * @param endIndex          局部变色的结束下标
     * @param spannableModel    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
     *                          Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
     *                          Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
     *                          Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
     * @param color
     * @param textClickListener
     */
    public SpannableStringBuilder setColorAndClick(int startIndex, int endIndex, int spannableModel, final int color, final TextClickListener textClickListener) {

        if (spannable == null) {
            spannable = new SpannableStringBuilder(content);
        }
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //在此处理点击事件
                if (textClickListener != null) {
                    textClickListener.onClick(view);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //设置文字颜色
                ds.setColor(color);
            }

        }, startIndex, endIndex, spannableModel);

        textView.setText(spannable);

        return spannable;
    }

    public SpannableStringBuilder setTextSize(int textSize, int startIndex, int endIndex) {

        return setTextSize(textSize, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置字体大小
     *
     * @param startIndex     局部的起始下标
     * @param endIndex       局部的结束下标
     * @param spannableModel Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
     *                       Spanned.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)
     *                       Spanned.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)
     *                       Spanned.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)
     */
    public SpannableStringBuilder setTextSize(int textSize, int startIndex, int endIndex, int spannableModel) {

        if (spannable == null) {
            spannable = new SpannableStringBuilder(content);
        }
        spannable.setSpan(new AbsoluteSizeSpan(textSize), startIndex, endIndex, spannableModel);

        textView.setText(spannable);

        return spannable;
    }

    public static SpannableStringBuilder setColorAndClick(TextView textView, String content, int startIndex,
                                                          int endIndex, int spannableModel, final int color, final TextClickListener textClickListener) {
        //否则点击不生效
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        //            去掉文字背景
        textView.setHighlightColor(Color.parseColor("#00ffffff"));
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                //在此处理点击事件
                if (textClickListener != null) {
                    textClickListener.onClick(view);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                //设置文字颜色
                ds.setColor(color);
            }

        }, startIndex, endIndex, spannableModel);

        textView.setText(spannable);

        return spannable;
    }

    /**
     * 文本事件点击
     */
    public interface TextClickListener {
        void onClick(View view);
    }


}
