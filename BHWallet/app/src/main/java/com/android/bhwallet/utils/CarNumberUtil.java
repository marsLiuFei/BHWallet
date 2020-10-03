package com.android.bhwallet.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/12/2.
 */
public class CarNumberUtil {
    /*
    *车牌号格式：汉字 + A-Z + 5位A-Z或0-9
    *（只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
    */
    public static boolean isCarnumberNO(String carnumber) {

        String carnumRegex = "[A-Z_a-z]{1}[A-Z_a-z_0-9]{5}";
        if (TextUtils.isEmpty(carnumber)) return false;
        else return carnumber.matches(carnumRegex);
    }

    public static boolean isCar(String carnumber){
        String carnumRegex = "[\\u4E00-\\u9FA5][\\da-zA-Z]{6}$";
//        String carnumRegex="/^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/";
        if (TextUtils.isEmpty(carnumber)) return false;
        else return carnumber.matches(carnumRegex);
    }

/*
车架号由数字和字母组成
 */
    public static boolean isCarjao(String carnumber){
        Pattern idNumPattern = Pattern.compile("^[A-Za-z0-9]+$");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(carnumber);

        return idNumMatcher.matches();
    }
    public static boolean isCarjao1(String carnumber){
        Pattern idNumPattern = Pattern.compile("^[0-9]+$");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(carnumber);

        return idNumMatcher.matches();
    }
    public static boolean isCarjao2(String carnumber){
        Pattern idNumPattern = Pattern.compile("^[A-Za-z]+$");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(carnumber);

        return idNumMatcher.matches();
    }
}
