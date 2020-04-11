package com.jm.core.common.tools.num;

/**
 * Double功能
 *
 * @author jinXiong.Xie
 */

public class DoubleUtil {

    private DoubleUtil() {
    }

    public static String toFormatString(double d) {
        return toFormatString(d, "#.00");
    }

    /**
     * double保留两位小数
     */
    public static String toFormatString(String s) throws Exception {
        return toFormatString(Double.parseDouble(s));
    }


    public static String toFormatString(double d, String format) {
        try {
            return toFormatStringChangeFormat((new java.text.DecimalFormat(format)).format(d));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String toFormatString(String string, String format) {
        try {
            return toFormatString(Double.parseDouble(string), format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String toFormatStringChangeFormat(String str) {
//    if (d==0){
//      return format.replace('#','0');
//    }else {
//      return (new java.text.DecimalFormat(format)).format(d);
//    }
        if (str.startsWith(".")) {
            str = "0" + str;
        }
        return str.replace("-.", "-0.");
    }
}