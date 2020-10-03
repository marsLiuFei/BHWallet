package com.asuka.android.asukaandroid.comm.utils;

import android.text.TextUtils;
import android.util.Log;

import com.asuka.android.asukaandroid.AsukaAndroid;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.http.HttpUtils;
import com.asuka.android.asukaandroid.http.ResponseInfo;
import com.asuka.android.asukaandroid.http.callback.RequestCallBack;
import com.asuka.android.asukaandroid.http.client.HttpRequest;
import com.asuka.android.asukaandroid.http.exception.HttpException;

/**
 * Author:Asuka
 * Time:2016-9-9
 * Mask:日志工具
 */
public class LogUtil {

    public static String customTagPrefix = "x_log";
    static HttpUtils httpUtils;

    //
    static {
        httpUtils =new HttpUtils(20000);
    }

    private LogUtil() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();
        Log.e(tag, content);
    }

    public static void errorPost(String errorType,String content,String projectName,
                                 String author, String errorOpr,String errorPwd) {

        EGRequestParams params=new EGRequestParams();
        params.addBodyParameter("errorType",errorType);
        params.addBodyParameter("errorDetail",content);
        params.addBodyParameter("errorProjectName",projectName);
        params.addBodyParameter("author",author);
        params.addBodyParameter("errorOpr",errorOpr);
        params.addBodyParameter("errorPwd",errorPwd);
        //错误环境0正式，1开发，2测试
        if(AsukaAndroid.isDebug()) {
            params.addBodyParameter("errorEv", "1");
        }
        else {
            params.addBodyParameter("errorEv", "0");
        }

        String urlString="http://112.27.245.63:3331/api/v1/exception/add";
        Log.i("post", urlString + "?" + params.toString());
        final String tempurl= urlString + "?" + params.toString();
        httpUtils.send(HttpRequest.HttpMethod.POST, urlString, params, new RequestCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                LogUtil.d(responseInfo.result);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                LogUtil.e("httputil=="+new Throwable(e)+"s==="+s);
            }
        });

        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();
        Log.e(tag, content);

    }


    public static void e(String content, Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.w(tag, tr);
    }


    public static void wtf(String content) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!AsukaAndroid.isDebug()) return;
        String tag = generateTag();

        Log.wtf(tag, tr);
    }
}
