package com.android.bhwallet.utils;

/**
 * 备注:***
 * 作者:朱宗庆
 * 日期:2017/4/28
 */
public class Helper {
    public static String value(String object, String... show) {
        String returnShow = "";
        if (show != null && show.length > 0) {
            returnShow = show[0];
        }
        if (object == null || object.length() == 0)
            return returnShow;
        else
            return object;
    }
}
