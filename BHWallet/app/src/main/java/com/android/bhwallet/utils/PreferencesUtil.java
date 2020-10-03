package com.android.bhwallet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaAndroid;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.android.bhwallet.R;
import com.android.bhwallet.base.Constant;

/**
 * Created by egojit on 2017/1/13.
 */

public class PreferencesUtil {
    private static PreferencesUtil instatnce;
     String loginUser;
    private Context mContext;

    public static PreferencesUtil getInstatnce(Context context) {
        if (instatnce == null) {
            instatnce = new PreferencesUtil(context);

        }
        return instatnce;

    }

    public PreferencesUtil(Context context) {
        this.mContext = context;
        this.instatnce = this;

        pref = context.getSharedPreferences(Constant.SharedPreferences, Context.MODE_PRIVATE);
        loginUser= context.getResources().getString(R.string.loginUser);
    }


    private static SharedPreferences pref;

    public String getToken() {
        return pref.getString("token", "");
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.commit();
    }


    public boolean saveUser(String userModel) {
        SharedPreferences.Editor mEditor = pref.edit();
        mEditor.putString(loginUser, userModel);
        return mEditor.commit();
    }

    public String getUserName() {

        String userNameField= mContext.getResources().getString(R.string.userNameField);
        String str = getUser().getString(userNameField);

        return str;
    }
    public String getUserPwd() {
        String loginPwdField= mContext.getResources().getString(R.string.loginPwdField);
        String str = getUser().getString(loginPwdField);
        return str;
    }

    public JSONObject getUser() {

        String str = pref.getString(loginUser, "");
        return JSON.parseObject(str);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void save(String key, Boolean value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void save(String key, int value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return pref.getString(key, "");
    }

    public String getString(String key,String def) {
        return pref.getString(key, def);
    }
    public boolean getBoolean(String key, boolean defValue) {
        return pref.getBoolean(key, defValue);
    }


    public int getInt(String key) {
        return pref.getInt(key, 0);
    }

    /**
     * 清除登录的用户信息
     */
    public void clearLoginUser() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(loginUser);
        editor.commit();
    }

    /**
     * 清除手势密码状态信息
     */
    public void clearjewelryState() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("jewelryState");
        editor.commit();
    }
    /**
     * 清除手势密码信息
     */
    public void clearjewelryPass() {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("jewelryPass");
        editor.commit();
    }
    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin() {

        String str = pref.getString(loginUser, null);
        if (StringUtils.isEmpty(str) || str.length() < 10) {
            return false;
        } else {
            return true;
        }
    }
    public String getBaseFileUrl() {
        String str = pref.getString("BaseFileUrl", "");
        return str;
    }

    public String getPwd() {
        return null;
    }
}
