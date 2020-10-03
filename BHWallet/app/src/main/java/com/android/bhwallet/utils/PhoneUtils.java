package com.android.bhwallet.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 备注:***
 * 作者:朱宗庆
 * 日期:2017/4/26
 */
public class PhoneUtils {
    /*
*手机号码正则表达式
*/
    public static boolean isMobileNO(String mobiles) {
//        ^((13[0-9])|(15[^4,\D])|(18[0-9])|(14[5,7]))\d{8}$
//        Pattern p = Pattern.compile("^1([358]\\d|4[57]|7[^249])\\d{8}$");
//
//        Matcher m = p.matcher(mobiles);
        boolean flag = false;
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(mobiles);
        if (!StringUtils.isEmpty(mobiles)){
            if(m.matches() ){
                if (mobiles.length()!=11){
                    flag=false;
                }
                else {
                    flag=true;
                }
            }
        }

        return flag ;

    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9][0-9]{1,2}[0-9]{5,10}$");  // 验证带区号的
//        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
//        p3 = Pattern.compile("^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$");//验证带区号和带“—”
//        if (str.length()<9&&str.length()>=7){
//            m = p2.matcher(str);
//            b = m.matches();
//        }else if (str.length()>9&&str.length()<13) {
        m = p1.matcher(str);
        b = m.matches();
//        }
//        else {
//            m=p3.matcher(str);
//            b=m.matches();
//        }
        return b;
    }

    /**
     * 电话号码验证 不带区号
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isTelNoZone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
       p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
       if (str.length()<9&&str.length()>=7){
            m = p2.matcher(str);
           b = m.matches();
       }
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isTelWithZone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9][0-9]{1,2}[0-9]{5,10}$");  // 验证带区号的
        m = p1.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 精神关爱圈联系人电话号码正则验证,首位不是-并且
     * @param str 电话号码
     * @return
     */
    public static boolean isPhone2(String str) {
        //判空返回
        if (StringUtils.isEmpty(str)){
            return false;
        }
        //长度必须大于等于7
        if (str.length()<7){
            return false;
        }
        //首尾是-也直接返回false
        if (str.substring(0,1).equals("-") || str.substring(str.length()-1,str.length()).equals("-")){
            return false;
        }

        return true;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 打电话
     *
     * @param tel
     * @return
     */
    public static void call(String tel, Context context) {

        String inputStr = tel.trim();
        // 如果输入不为空创建打电话的Intent

        if (!StringUtils.isEmpty(inputStr)) {
            Intent phoneIntent = new Intent(
                    "android.intent.action.CALL", Uri.parse("tel:"
                    + inputStr));

            // 启动
            ((AsukaActivity) context).startActivityForResult(phoneIntent, 1);
        }
    }

    public static void checkPhonePermission(Context context, String tel) {
        try {
            PackageManager pm = context.getPackageManager();
            boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.CALL_PHONE", "com.jdd.android.spsp"));
            if (!permission) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((AsukaActivity) context,
                        Manifest.permission.CALL_PHONE)) {
                    // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                    // 弹窗需要解释为何需要该权限，再次请求授权

                    // 帮跳转到该应用的设置界面，让用户手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    ((AsukaActivity) context).startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions((AsukaActivity) context,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }

            } else {
                call(tel, context);
            }
        }
        catch (Exception ex) {
            ((AsukaActivity) context).showSuccess("请手动打开权限！");
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            ((AsukaActivity) context).startActivity(intent);
        }

    }

    //验证邮政编码
    public static  boolean isPostCode(String post){
        if(post.matches("[1-9]\\d{5}(?!\\d)")){
            return true;
        }else{
            return false;
        }
    }
}
