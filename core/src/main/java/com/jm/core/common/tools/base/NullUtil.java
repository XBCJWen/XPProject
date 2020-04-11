package com.jm.core.common.tools.base;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * 判null工具类
 */

public class NullUtil {

    private NullUtil() {
    }

    /**
     * 判断参数是否含有null，有则返回true，没有则返回false
     */
    public static boolean checkNull(Object... objects) {
        if (objects == null || objects.length == 0) {
            return true;
        }
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为null
     *
     * @param string
     * @return
     */
    public static String checkNull(String string) {
        if (TextUtils.isEmpty(string)) {
            return new String();
        } else {
            return string;
        }
    }

    /**
     * 判断Edit与其内容是否有空(true为null，false为 不为空)
     *
     * @return
     */
    private boolean checkEditNull(EditText... edits) {
        String[] strings = EditUtil.getEditsStringTip(null, edits);
        if (strings == null) {
            return true;
        }
        for (String s : strings) {
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
}
