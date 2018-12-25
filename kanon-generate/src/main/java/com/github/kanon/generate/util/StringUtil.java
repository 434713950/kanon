package com.github.kanon.generate.util;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2018/12/25
 */
public class StringUtil {

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String firstLowerCase(String str){
        if (Character.isLowerCase(str.charAt(0))){
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String firstUpperCase(String str){
        if (Character.isUpperCase(str.charAt(0))){
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
