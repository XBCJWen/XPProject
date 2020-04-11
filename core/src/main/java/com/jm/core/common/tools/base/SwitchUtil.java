package com.jm.core.common.tools.base;

/**
 * Switch工具类
 */

public class SwitchUtil {

    private SwitchUtil() {
    }

    /**
     * 根据状态标识获取状态的名称
     *
     * @param state
     * @param strings
     * @return
     */
    public static String getStringState(int state, String[] strings) {
        if (state < strings.length) {
            return strings[state];
        }
        return null;
    }

    /**
     * 获取value在num中的间隔（array为升序,若array不为升序，可以先使用Arrays.sort(array);）,没有用排序算法，只适合小array
     *
     * @param value
     * @param array
     * @return 第一个为在那个阶级：0,1,2...第二第三为间隔(重点在于更改Integer.MIN_VALUE和Integer.MAX_VALUE的情况)
     */
    public static int[] getIntInterval(int value, int[] array) {
        if (array.length == 0) {
            return new int[]{0, 0, 0};
        }
        //极端两点判断
        if (value <= array[0]) {
            return new int[]{0, Integer.MIN_VALUE, array[0]};
        }
        if (value >= array[array.length - 1]) {
            return new int[]{array.length, array[array.length - 1], Integer.MAX_VALUE};
        }
        //在array之间
        for (int index = 0; index < array.length - 1; index++) {
            if (value >= array[index] && value <= array[index + 1]) {
                return new int[]{index + 1, array[index], array[index + 1]};
            }
        }
        return new int[]{0, 0, 0};
    }
}
