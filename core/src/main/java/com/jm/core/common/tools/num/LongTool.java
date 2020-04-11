package com.jm.core.common.tools.num;

import java.util.ArrayList;
import java.util.List;

/**
 * LongList与LongArray相互转化工具
 * Created by Jinxiong.Xie on 2017/3/14 11:21.
 * 邮箱：173500512@qq.com
 */

public class LongTool {

    public LongTool() {
    }

    /**
     * 数组转化为列表
     *
     * @param array
     * @return
     */
    public static final List<Long> arrayToList(long[] array) {
        List<Long> list = new ArrayList<Long>();
        for (long l : array) {
            list.add(l);
        }
        return list;
    }

    /**
     * 列表转化为数组
     *
     * @param list
     * @return
     */
    public static final long[] listToArray(List<Long> list) {
        long[] array = new long[0];
        if (list != null) {
            array = new long[list.size()];
            for (int index = 0; index < list.size(); index++) {
                array[index] = list.get(index);
            }
        }
        return array;
    }
}
