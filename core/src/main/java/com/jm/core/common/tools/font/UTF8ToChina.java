package com.jm.core.common.tools.font;

/**
 * 将Unicode编码转换为中文
 *
 * @author xiejinxiong
 */
public class UTF8ToChina {

    private UTF8ToChina() {
    }

    /**
     * 将Unicode编码转换为中文
     *
     * @param str
     * @return
     */
    public static String UnicodeToChinese(String str) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ) {
            char ch = str.charAt(i);
            // 判断是否以\为开头
            if (ch != '\\') {
                sb.append(ch);
                i++;
            } else if (str.charAt(i + 1) == 'u') {// 判断是否以\\u开头
                // 获取\\u后的字符数字位置
                String code = str.substring(i + 2, i + 6);
                // 将字符数字位置转换为对于字符
                char ch2 = (char) Integer.parseInt(code, 16);
                sb.append(ch2);
                i += 6;
            }
        }
        return sb.toString();
    }
}
