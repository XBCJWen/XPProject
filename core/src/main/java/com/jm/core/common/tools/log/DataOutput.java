package com.jm.core.common.tools.log;

import android.util.Log;

/**
 * 本类作用：日志输出（可改用log4j等）
 *
 * @author jinXiong.Xie
 */
public class DataOutput {

    private DataOutput() {
    }

    /**
     * 控制台及面板信息输出:1为数据信息，2为异常信息
     *
     * @param info
     * @param flag
     */
    public static void print(String info, int flag) {
        try {
            // 输出信息
            if (flag == 1) {
                System.out.println(info);
            }
            // 输出异常信息
            if (flag == 2) {
                System.err.println(info);
            }
        } catch (Exception e) {
            System.out.println("disData.Outinfo:" + e.getMessage());
            System.err.println(e.getMessage());

        }
    }

    /**
     * 使用Log.e的方式输出到logcat中
     *
     * @param tag
     * @param msg
     */
    public static void logE(String tag, String msg) {
        Log.e(tag, msg);
    }

}
