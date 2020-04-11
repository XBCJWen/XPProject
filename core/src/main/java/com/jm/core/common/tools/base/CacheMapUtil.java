package com.jm.core.common.tools.base;

import android.text.TextUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Map缓存数据
 *
 * @author jinXiong.Xie
 */
public class CacheMapUtil {

    /**
     * 分隔符
     */
    public static String separator = "!@#$%^&*()_+";

    private static HashMap<String, String> cacheMap = new HashMap<>();

    public static String getValue(String key) {
        return getValue(key, Integer.MAX_VALUE);
    }

    /**
     * 获取值
     *
     * @param key
     * @param effectiveTime 有效时间，秒为单位
     * @return
     */
    public static String getValue(String key, int effectiveTime) {
        String value = cacheMap.get(key);
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        String[] values = value.split(separator);
        int time = DateUtil.getTimeDelta(new Date(Long.parseLong(values[0])), Calendar.getInstance().getTime());
        if (time <= effectiveTime) {
            return values[1];
        }
        return null;
    }

    /**
     * 放入值
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, String value) {
        cacheMap.put(key, System.currentTimeMillis() + separator + value);
    }
}
