package com.android.bhwallet.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.base.BaseResultModel;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.http.HttpUtils;
import com.asuka.android.asukaandroid.http.ResponseInfo;
import com.asuka.android.asukaandroid.http.callback.RequestCallBack;
import com.asuka.android.asukaandroid.http.client.HttpRequest;
import com.asuka.android.asukaandroid.http.exception.HttpException;

import org.apache.http.entity.StringEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by egojit on 2017/1/13.
 */

public class HttpHelper {
    private Boolean ishowLoading = true;

    public interface Ok {
        void success(String str);

        void complete(String str);
    }

    static HttpUtils httpUtils;


    //
//    static {
//        httpUtils = new HttpUtils(20000);
//
//    }

    public void setIshowLoading(Boolean ishowLoading) {
        this.ishowLoading = ishowLoading;
    }


    public static void post(final AsukaActivity activity, final String urlString, EGRequestParams para, final Ok onOk) {
        try {

            String token = "";
            if (PreferencesUtil.getInstatnce(activity) != null) {
                token = PreferencesUtil.getInstatnce(activity).getString("token");
            }
            if (!StringUtils.isEmpty(token)) {
                para.addBodyParameter("token", token);
            }
            if (httpUtils == null) {
                Log.e("httpUtils", "==================httpUtils is null===============");
                httpUtils = new HttpUtils(100000);
            }

//            LogUtil.e(urlString);
//            LogUtil.e(para.toString());
            httpUtils.send(HttpRequest.HttpMethod.POST, urlString, para, new RequestCallBack<String>() {
                @Override
                public void onStart() {
                    super.onStart();
                    if (activity != null) {
                        activity.showLoging();
                    }
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //Log.e("返回数据", responseInfo.result);
                    BaseResultModel baseModel = null;
                    try {
                        baseModel = JSON.parseObject(responseInfo.result, BaseResultModel.class);
                    } catch (JSONException e) {
                        activity.showWarning(activity.getString(R.string.json_decode_err_hint));
                        activity.dissmisLoging();
                        return;
                    }

                    if ("1".equals(baseModel.getCode())) {
                        onOk.success(baseModel.getData());
                    } else {
                        activity.showWarning(baseModel.getMsg());
                        onOk.complete(baseModel.getMsg());
                    }
                    onOk.complete("");
                    activity.dissmisLoging();
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //Log.e("error", "httputil==" + new Throwable(e) + "s===" + s);
                    activity.showWarning("网络错误!");
                    activity.dissmisLoging();
                    onOk.complete("网络错误");
                }
            });
        } catch (Exception ex) {
            //Log.e("error", ex.getMessage());
            StringBuffer sb = new StringBuffer();
            //递归获取全部的exception信息
            try {
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                ex.printStackTrace(printWriter);
                Throwable cause = ex.getCause();
                while (cause != null) {
                    cause.printStackTrace(printWriter);
                    cause = cause.getCause();
                }
                printWriter.close();
                String result = writer.toString();
                sb.append(result);
                //Log.e("error", sb.toString());

            } catch (Exception e) {

            }
            activity.dissmisLoging();
            activity.showWarning("请求失败……");
        }
    }


    public static void postNoProcess(final AsukaActivity activity, String urlString, EGRequestParams para, final Ok onOk) //
    {

        try {
            String token = "";
            if (PreferencesUtil.getInstatnce(activity) != null) {
                token = PreferencesUtil.getInstatnce(activity).getString("token");
            }
            if (!StringUtils.isEmpty(token)) {
                para.addBodyParameter("token", token);
            }
            if (httpUtils == null) {
                //Log.e("httpUtils", "==================httpUtils is null===============");
                httpUtils = new HttpUtils(100000);
            }
            //LogUtil.e(urlString);
            //LogUtil.e(para.toString());

            httpUtils.send(HttpRequest.HttpMethod.POST, urlString, para, new RequestCallBack<String>() {
                @Override
                public void onStart() {
                    super.onStart();

                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //LogUtil.e(responseInfo.result);
                    try {
                        BaseResultModel baseModel = JSON.parseObject(responseInfo.result, BaseResultModel.class);
                        if ("1".equals(baseModel.getCode())) {
                            onOk.success(baseModel.getData());
                        } else {
//                            activity.showWarning(baseModel.getMessage());
                        }
                        onOk.complete("");
                    } catch (Exception e) {
                        //LogUtil.e(e.getMessage());
                    }

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    onOk.complete("网络错误");
                }
            });
        } catch (Exception ex) {
            //LogUtil.e(ex.getMessage());

        }
    }

    public static void postSpecialNoProcess(final AsukaActivity activity, String urlString, EGRequestParams para, final Ok onOk) //
    {

        try {
            String token = "";
            if (PreferencesUtil.getInstatnce(activity) != null) {
                token = PreferencesUtil.getInstatnce(activity).getString("token");
            }

            if (!StringUtils.isEmpty(token)) {
                para.addBodyParameter("token", token);
            }
            if (httpUtils == null) {
                httpUtils = new HttpUtils(100000);
            }
            //LogUtil.e(urlString);
            //LogUtil.e(para.toString());

            httpUtils.send(HttpRequest.HttpMethod.POST, urlString, para, new RequestCallBack<String>() {
                @Override
                public void onStart() {
                    super.onStart();

                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //LogUtil.e(responseInfo.result);
                    try {
                        onOk.success(responseInfo.result);
                        onOk.complete("");
                    } catch (Exception e) {
                        //LogUtil.e(e.getMessage());
                    }

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    onOk.complete("网络错误");
                }
            });
        } catch (Exception ex) {
            //LogUtil.e(ex.getMessage());

        }
    }
}
