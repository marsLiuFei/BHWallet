package com.android.bhwallet.app.Main.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.FileUtil;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.AsukaTakePhotoActivity;
import com.dhc.gallery.ui.GalleryActivity;

import java.io.File;
import java.util.ArrayList;

public class BuLuActivity extends AsukaTakePhotoActivity {
    WebView webView;
    private ValueCallback<Uri> mUploadMessage;
    private final static int PHOTO_REQUEST = 100;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bulu);
        toolBar.setVisibility(View.GONE);
        webView = findViewById(R.id.webView);
        initWebViewSetting();
    }

    private void initWebViewSetting() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);
        //辅助WebView设置处理关于页面跳转，页面请求等操作
        webView.setWebViewClient(new MyWebViewClient());
        //辅助WebView处理图片上传操作
        webView.setWebChromeClient(new MyChromeWebClient());
        //加载地址
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String url = bundle.getString("url");
            webView.loadUrl(url);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    //自定义 WebViewClient 辅助WebView设置处理关于页面跳转，页面请求等操作【处理tel协议和视频通讯请求url的拦截转发】
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.trim().startsWith("tel")) {//特殊情况tel，调用系统的拨号软件拨号【<a href="tel:1111111111">1111111111</a>】
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } else {
                String port = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));//尝试要拦截的视频通讯url格式(808端口)：【http://xxxx:808/?roomName】
                if (port.equals("808")) {//特殊情况【若打开的链接是视频通讯地址格式则调用系统浏览器打开】
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else {//其它非特殊情况全部放行
                    view.loadUrl(url);
                }
            }
            return true;
        }
    }

    private Uri imageUri;

    //自定义 WebChromeClient 辅助WebView处理图片上传操作【<input type=file> 文件上传标签】
    public class MyChromeWebClient extends WebChromeClient {
        // For Android 3.0-
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            takePhoto();


        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            takePhoto();

        }

        //For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            takePhoto();
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            takePhoto();
            return true;
        }
    }

    private void takePhoto() {
        FileUtil.showSelectSingleDialog(BuLuActivity.this, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
        Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
        if (mUploadCallbackAboveL != null) {
            onActivityResultAboveL(requestCode, resultCode, data);
        } else if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 12) {
                ArrayList<String> path = data.getStringArrayListExtra(GalleryActivity.PHOTOS);
                results = new Uri[]{Uri.parse(path.get(0))};
            }
        }

        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            webView.destroy();
            webView = null;
        }
    }
}
