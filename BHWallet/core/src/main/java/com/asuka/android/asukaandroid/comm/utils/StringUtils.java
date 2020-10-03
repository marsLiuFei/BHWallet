package com.asuka.android.asukaandroid.comm.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by egojit on 2017/1/13.
 */

public class StringUtils {
    public StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null?str2 == null:str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null?str2 == null:str1.equalsIgnoreCase(str2);
    }

    public static boolean gbk(String str) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        while(m.find()) {
            for(int i = 0; i <= m.groupCount(); ++i) {
                ++count;
            }
        }

        if(count > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
}
