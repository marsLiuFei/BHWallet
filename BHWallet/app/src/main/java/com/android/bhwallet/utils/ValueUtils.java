package com.android.bhwallet.utils;

import android.text.TextUtils;
import android.widget.EditText;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xhb on 2016/8/11.
 */
public class ValueUtils {

    public static String touzi_ed_values22 = "";
    /**
     * 在数字型字符串千分位加逗号
     * @param str
     * @param edtext
     * @return sb.toString()
     */
    public static String addComma(String str,EditText edtext){

        touzi_ed_values22 = edtext.getText().toString().trim().replaceAll(",","");

        boolean neg = false;
        if (str.startsWith("-")){  //处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1){ //处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4){
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg){
            sb.insert(0, '-');
        }
        if (tail != null){
            sb.append(tail);
        }
        return sb.toString();
    }

    public static String getValue(String str){
        if (TextUtils.isEmpty(str)){
            return "";
        }
        String[] s = str.split(",");
        String value = "";
        for (int i=0;i<s.length;i++){
            value += s[i];
        }
        return value;
    }
    public static String spl(String str){
        String s = "";
        if (TextUtils.isEmpty(str)){
            return s;
        }
        String []arr = str.split(",");
        for (int i=0;i<arr.length;i++){
            s += arr[i];
        }
        return s;
    }
    public static String getValues(String str){
        String[] s = str.split("-");
        String value = "";
        for (int i=0;i<s.length;i++){
            value += s[i];
        }
        return value;
    }
    public static String gettel(String str){
        String[] s = str.split("-");
        String value = "";
        for (int i=0;i<s.length;i++){
            value += s[i];
        }
        return value;
    }

    /*
*价格正则表达式
*/
    public static boolean isValue(String value){
        Pattern p = Pattern.compile("(^([1-9]{1}\\d{0,9})$)|((^[1-9]{1}\\d{0,9})\\.(\\d{1,2})$)");

        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     *
     * @param str
     * @param exchangeChars
     * @param indexs
     * @return
     */
    public static String replaceCharsAtIndex(String str,String[] exchangeChars,int[] indexs){
        if (TextUtils.isEmpty(str)){
            return "";
        }
        if (exchangeChars==null||indexs==null){
            return str;
        }
        if (exchangeChars.length==0||indexs.length==0){
            return str;
        }
        if (exchangeChars.length!=indexs.length){
            return str;
        }
        String result="";
        char[] temp=new char[str.length()];
        for (int i=0;i<str.length();i++){
            temp[i]=str.charAt(i);
        }
        for (int j=0;j<indexs.length;j++){
            if (temp.length<=indexs[j]){
                return str;
            }
            temp[indexs[j]]=exchangeChars[j].toCharArray()[0];
        }
        for (int k=0;k<temp.length;k++){
            result=result+temp[k];
        }

        return result;
    }

}
