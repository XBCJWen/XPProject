package com.jm.core.common.tools.base;

import android.view.View;
import android.widget.TextView;

/**
 * 未读数工具类
 *
 * @author jinXiong.Xie
 */

public class UnReadUtil {

    private UnReadUtil() {
    }

    /**
     * 设置未读数
     *
     * @param tvUnRead
     * @param unReadNum
     */
    public static void setUnReadText(TextView tvUnRead, int unReadNum) {
        setUnReadText(tvUnRead, unReadNum, 99);
    }

    /**
     * 设置未读数
     *
     * @param tvUnRead
     * @param unReadNum
     */
    public static void setUnReadText(TextView tvUnRead, int unReadNum, int maxNum) {
        if (tvUnRead != null) {
            if (unReadNum <= 0) {
                tvUnRead.setText("0");
                tvUnRead.setVisibility(View.GONE);
            } else {
                tvUnRead.setVisibility(View.VISIBLE);
                if (unReadNum > maxNum) {
                    tvUnRead.setText("...");
                } else {
                    tvUnRead.setText(unReadNum + "");
                }
            }
        }
    }

    /**
     * 设置未读样式
     *
     * @param viewUnRead
     * @param unReadNum
     */
    public static void setUnRead(View viewUnRead, int unReadNum) {
        if (viewUnRead != null) {
            if (unReadNum <= 0) {
                viewUnRead.setVisibility(View.GONE);
            } else {
                viewUnRead.setVisibility(View.VISIBLE);
            }
        }
    }
}
