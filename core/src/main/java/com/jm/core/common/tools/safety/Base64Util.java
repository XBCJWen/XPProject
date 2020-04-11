package com.jm.core.common.tools.safety;

import android.util.Base64;

/**
 * Base64工具类
 */
public class Base64Util {

    private Base64Util() {

    }

    /**
     * 加密
     *
     * @param string
     * @return
     */
    public static final String encodeToString(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
    }

    /**
     * 解密
     *
     * @param string
     * @return
     */
    public static final String decode(String string) {
        return new String(Base64.decode(string, Base64.DEFAULT));
    }
}
