package com.jm.core.common.tools.base;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author jinXiong.Xie
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || input.equals("null")) {
            return true;
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测两个字符串是否相同；
     */
    public final static boolean isSame(String value1, String value2) {
        if (isEmpty(value1) && isEmpty(value2)) {
            return true;
        } else if (!isEmpty(value1) && !isEmpty(value2)) {
            return (value1.trim().equalsIgnoreCase(value2.trim()));
        } else {
            return false;
        }
    }


    /**
     * num为实数，n为要保留的小数位数。
     *
     * @param num
     * @param n
     * @return
     */
    public static double round(double num, int n) {
        return Math.round(num * Math.pow(10, n)) / Math.pow(10, n);
    }

    public static double round(int num, int n) {
        return Math.round(1.0 * num * Math.pow(10, n)) / Math.pow(10, n);
    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public final static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

//    /**
//     * 根据UnicodeBlock方法判断中文标点符号
//     * 中文的标点符号主要存在于以下5个UnicodeBlock中，
//
//     U2000-General Punctuation (百分号，千分号，单引号，双引号等)
//
//     U3000-CJK Symbols and Punctuation ( 顿号，句号，书名号，〸，〹，〺 等；PS: 后面三个字符你知道什么意思吗？ : )    )
//
//     UFF00-Halfwidth and Fullwidth Forms ( 大于，小于，等于，括号，感叹号，加，减，冒号，分号等等)
//
//     UFE30-CJK Compatibility Forms  (主要是给竖写方式使用的括号，以及间断线﹉，波浪线﹌等)
//
//     UFE10-Vertical Forms (主要是一些竖着写的标点符号，    等等)
//     * @param c
//     * @return
//     */
//    public boolean isChinesePunctuation(char c) {
//        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
//        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
//                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
//                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
//                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS) {
//            return true;
//        } else {
//            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && ub == Character.UnicodeBlock.VERTICAL_FORMS;
//        }
//    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public final static boolean isChineseWithoutPunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            return true;
        }
        return false;
    }


    /**
     * 判断手机格式是否正确
     *
     * @param mobile 手机号码
     * @return 是/否
     */
    public static boolean isMobile(String mobile) {
        Pattern p_10086 = Pattern
                .compile("^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$");
        Pattern p_10010 = Pattern
                .compile("^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$");
        Pattern p_10001 = Pattern
                .compile("^((133)|(153)|(177)|(173)|(18[0,1,9]))\\d{8}$");
        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        Matcher m_10086 = p_10086.matcher(mobile);
        Matcher m_10010 = p_10010.matcher(mobile);
        Matcher m_10001 = p_10001.matcher(mobile);
        Matcher m = p.matcher(mobile);
        if (m_10086.matches() || m_10010.matches() || m_10001.matches() || m.matches()) {
            return true;
        }

        return false;
    }

    public static <T extends TextView> void setText(T tv, String string) {
        tv.setText(NullUtil.checkNull(string));
    }

    public static <T extends TextView> String getText(T tv) {
        return tv.getText().toString().trim();
    }

    /**
     * StringArray转换为String
     *
     * @param strings
     * @return
     */
    public static String arrayToString(List<String> strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s + ",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * 隐藏手机号码显示
     *
     * @param strMobile
     * @return
     */
    public static String hideMobile(String strMobile) {
        return hideMobile(strMobile, 2, 3);
//        if ((!TextUtils.isEmpty(strMobile)) && strMobile.length() > 6) {
//            return strMobile.substring(0, 2) + "******" + strMobile.substring(strMobile.length() - 3, strMobile.length());
//        } else {
//            return strMobile;
//        }
    }

    /**
     * 手机号码长度
     */
    private static final int PHONE_LENGTH = 11;

    /**
     * 隐藏手机号码显示（例如182******32）
     *
     * @param strMobile
     * @param start     首部显示的数字位数
     * @param end       尾部显示的数字位数
     * @return
     */
    public static String hideMobile(String strMobile, int start, int end) {
        return hideNum(strMobile, start, end, PHONE_LENGTH);
    }

    /**
     * 隐藏数据显示（例如182******32）
     *
     * @param strNum
     * @param start  首部显示的数字位数
     * @param end    尾部显示的数字位数
     * @param length 数据总长度
     * @return
     */
    public static String hideNum(String strNum, int start, int end, int length) {
        if ((!TextUtils.isEmpty(strNum)) && strNum.length() > start + end) {
            StringBuilder builder = new StringBuilder();
            for (int i = start + end; i < length; i++) {
                builder.append('*');
            }
            return strNum.substring(0, start) + builder.toString() + strNum.substring(strNum.length() - end, strNum.length());
        } else {
            return strNum;
        }
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(Context context, String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content);
        Toast.makeText(context, "已复制到系统剪贴板", Toast.LENGTH_SHORT).show();
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getText().toString();
    }

    /**
     * 获取剪切板内容
     *
     * @param context
     */
    public void getTextFromClip(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if (!clipboardManager.hasPrimaryClip())
            return;
        ClipData clipData = clipboardManager.getPrimaryClip();
        //获取 ClipDescription
        ClipDescription clipDescription = clipboardManager.getPrimaryClipDescription();
        //获取 lable
        String lable = clipDescription.getLabel().toString();
        //获取 text
        String text = clipData.getItemAt(0).getText().toString();
    }

    /**
     * 获取完整协议
     *
     * @param url
     * @return
     */
    public static String getCompleteUrl(String url) {
        String completeUrl = url.trim();
        if (TextUtils.isEmpty(completeUrl) || completeUrl.startsWith("http://") || completeUrl.startsWith("https://")) {
            return completeUrl;
        }
        return "http://" + completeUrl;
    }

    /**
     * 是否包含Url
     *
     * @param string
     * @return
     */
    public static boolean containUrl(String string) {
        //把中文替换为#
        string = string.replaceAll("[\u4E00-\u9FA5]", "#");
        String url[] = string.split("#");
        //转换为小写
        if (url != null && url.length > 0) {
            for (String tempurl : url) {
                if (TextUtils.isEmpty(tempurl)) {
                    continue;
                }
                tempurl = tempurl.toLowerCase();

                if (Patterns.WEB_URL.matcher(tempurl).matches() || URLUtil.isValidUrl(tempurl)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 设置数量
     *
     * @param textNum
     * @param root
     * @param num
     */
    public static void setTextNum(TextView textNum, String root, int num) {
        if (num == 0) {
            textNum.setText(root);
        } else {
            textNum.setText(root + "(" + num + ")");
        }
    }
}
