package com.jm.core.common.tools.base;

/**
 * 进度工具类
 *
 * @author jinXiong.Xie
 */

public class ProgressUtil {

    private ProgressUtil() {
    }

    /**
     * 获取当前进度
     *
     * @param now
     * @param max
     * @return
     */
    public static int getProgress(double now, double max) {
        return (int) (now / max * 100);
    }
}
