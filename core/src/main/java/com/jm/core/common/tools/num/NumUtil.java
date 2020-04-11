package com.jm.core.common.tools.num;

import java.util.Random;

/**
 * 数字工具类
 *
 * @author jinXiong.Xie
 */
public class NumUtil {

    /**
     * 生成随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /**
     * 生成随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static double randDouble(int min, int max) {

        return min + Math.random() * (max - min + 1);
    }
}
