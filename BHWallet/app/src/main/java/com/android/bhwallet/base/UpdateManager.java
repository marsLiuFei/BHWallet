//package com.android.bhwallet.base;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.content.FileProvider;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSONObject;
//
//import com.asuka.android.asukaandroid.AsukaActivity;
//import com.asuka.android.asukaandroid.comm.utils.LogUtil;
//import com.asuka.android.asukaandroid.http.EGRequestParams;
//import com.asuka.android.asukaandroid.http.HttpUtils;
//import com.android.bhwallet.BuildConfig;
//import com.android.bhwallet.R;
//
//import com.android.bhwallet.app.Main.UI.WalletMainActivity;
//import com.android.bhwallet.utils.HttpHelper;
//import com.android.bhwallet.utils.PreferencesUtil;
//import com.android.bhwallet.utils.UrlConfig;
//import com.maning.updatelibrary.InstallUtils;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * Created by egojit on 16/8/11.
// */
//public class UpdateManager {
//
//    private static String TAG = "UpdateManager";
//    private Context mContext; //上下文
//    private HttpUtils httpUtils;
//
//    //    private String apkUrl =
////            "http://183.232.83.10/dd.myapp.com/16891/" +
////                    "C4F9FF39FAE9184222DC278DC7309611.apk?mkey" +
////                    "=5541c92b6a9cca8d&f=d388&fsname=com.yangmbin" +
////                    ".beauty_1.1_2.apk&asr=8eff&p=.apk"; //apk下载地址
//    private static String savePath = "/sdcard/updateAPK/"; //apk保存到SD卡的路径
//    private static String saveFileName = savePath + "apkName.apk"; //完整路径名
//
//    private ProgressBar mProgress; //下载进度条控件
//    private static final int DOWNLOADING = 1; //表示正在下载
//    private static final int DOWNLOADED = 2; //下载完毕
//    private static final int DOWNLOAD_FAILED = 3; //下载失败
//    private int progress; //下载进度
//    private boolean cancelFlag = false; //取消下载标志位
//
//    private int clientVersion = 1; //客户端当前的版本号
//    private String updateDescription = "更新描述"; //更新内容描述信息
//    private boolean forceUpdate = true; //是否强制更新
//
//    private AlertDialog alertDialog1, alertDialog2; //表示提示对话框、进度条对话框
//
//    private JSONObject updateInfo;
//    private boolean isFromFlash = false;
//
//
//    private InstallUtils.DownloadCallBack downloadCallBack;
//
//
//    /**
//     * 构造函数
//     */
//    public UpdateManager(Context context) {
//        this.mContext = context;
//        this.httpUtils = new HttpUtils(20000);
//        initCallBack();
//        InstallUtils.setDownloadCallBack(downloadCallBack);
//    }
//
//    private void initCallBack() {
//        downloadCallBack = new InstallUtils.DownloadCallBack() {
//            @Override
//            public void onStart() {
//                LogUtil.i("InstallUtils---onStart");
//                mProgress.setProgress(0);
//            }
//
//            @Override
//            public void onComplete(String path) {
//                LogUtil.i( "InstallUtils---onComplete:" + path);
//                InstallUtils.installAPK(mContext, path, new InstallUtils.InstallCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(mContext, "正在安装程序", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFail(Exception e) {
//                        LogUtil.e(e.getMessage().toString());
//                    }
//                });
//                mProgress.setProgress(100);
//            }
//
//            @Override
//            public void onLoading(long total, long current) {
//                LogUtil.i("InstallUtils----onLoading:-----total:" + total + ",current:" + current);
//                mProgress.setProgress((int) (current * 100 / total));
//            }
//
//            @Override
//            public void onFail(Exception e) {
//                LogUtil.i("InstallUtils---onFail:" + e.getMessage());
//                ((AsukaActivity)mContext).showWarning("下载失败\n"+e.getMessage().toString());
//            }
//
//            @Override
//            public void cancle() {
//                LogUtil.i( "InstallUtils---cancle");
//            }
//        };
//    }
//
//    /**
//     * 显示更新对话框
//     */
//    public void showNoticeDialog(final boolean isClose) {
//        //如果版本最新，则不需要更新
////        if (serverVersion <= clientVersion)
////            return;
//        String version = "";
//        String remark = "";
//        try {
//            version = updateInfo.getString("name");
//            remark = updateInfo.getString("remark");
//        } catch (Exception e) {
//
//        }
//        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//        dialog.setTitle("发现新版本 ：" + "v" + version);
//        dialog.setMessage(updateDescription + "\n" + remark);
//        dialog.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                // TODO Auto-generated method stub
//                arg0.dismiss();
//                showDownloadDialog();
//            }
//        });
//        //是否强制更新
//        if ("0".equals(updateInfo.getString("isUpGrade")) || TextUtils.isEmpty(updateInfo.getString("isUpGrade"))) {
//            dialog.setNegativeButton("待会更新", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface arg0, int arg1) {
//                    // TODO Auto-generated method stub
//                    arg0.dismiss();
//
//
//                    if (isClose) {
//                        JSONObject object = PreferencesUtil.getInstatnce(mContext).getUser();
//                        if (object != null) {
//
//                            if (2 == PreferencesUtil.getInstatnce(mContext).getInt("jewelryState")) {//手势密码开启状态
//                                Bundle bundle = new Bundle();
//                                bundle.putString("from", "login");
//                                bundle.putString("password", PreferencesUtil.getInstatnce(mContext).getString("jewelryPass"));
//
//
//                                ((AsukaActivity) mContext).finish();
//                            } else {
//
//                                ((AsukaActivity) mContext).startActivity(WalletMainActivity.class, "首页");
//                                ((AsukaActivity) mContext).finish();
//                            }
//                        } else {
//                            Bundle bundle = new Bundle();
//                            bundle.getString("phone", PreferencesUtil.getInstatnce(mContext).getString("phone"));
//                            bundle.getString("pswd", PreferencesUtil.getInstatnce(mContext).getString("pswd"));
//                            //((AsukaActivity) mContext).startActivity(LoginActivity.class, "登录",bundle);
//                            ((AsukaActivity) mContext).finish();
//                        }
//
//                    }
//
//                }
//            });
//        }
//        alertDialog1 = dialog.create();
//        alertDialog1.setCancelable(false);
//        alertDialog1.show();
//    }
//
//    /**
//     * 显示进度条对话框
//     */
//    public void showDownloadDialog() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this.mContext);
//        dialog.setTitle("正在更新");
//        final LayoutInflater inflater = LayoutInflater.from(this.mContext);
//        View v = inflater.inflate(R.layout.softupdate_progress, null);
//        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
//        dialog.setView(v);
//        alertDialog2 = dialog.create();
//        alertDialog2.setCancelable(false);
//        alertDialog2.show();
//        //下载apk
//        downloadAPK();
//    }
//
//    /**
//     * 下载apk的线程
//     */
//    public void downloadAPK() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                try {
//                    URL url = new URL(UrlConfig.BASE_IMAGE_URL + updateInfo.getString("path"));
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.connect();
//                    int length = conn.getContentLength();
//                    InputStream is = conn.getInputStream();
//
//                    File file = new File(savePath);
//                    if (!file.exists()) {
//                        file.mkdir();
//                    }
//                    String apkFile = saveFileName;
//                    File ApkFile = new File(apkFile);
//                    FileOutputStream fos = new FileOutputStream(ApkFile);
//
//                    int count = 0;
//                    byte buf[] = new byte[1024];
//
//                    do {
//                        int numread = is.read(buf);
//                        count += numread;
//                        progress = (int) (((float) count / length) * 100);
//                        //更新进度
//                        mHandler.sendEmptyMessage(DOWNLOADING);
//                        if (numread <= 0) {
//                            //下载完成通知安装
//                            mHandler.sendEmptyMessage(DOWNLOADED);
//                            break;
//                        }
//                        fos.write(buf, 0, numread);
//                    } while (!cancelFlag); //点击取消就停止下载.
//                    fos.close();
//                    is.close();
//                } catch (Exception e) {
//                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    /**
//     * 更新UI的handler
//     */
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            // TODO Auto-generated method stub
//            switch (msg.what) {
//                case DOWNLOADING:
//                    mProgress.setProgress(progress);
//                    break;
//                case DOWNLOADED:
//                    if (alertDialog2 != null)
//                        alertDialog2.dismiss();
//                    installAPK();
//                    break;
//                case DOWNLOAD_FAILED:
//                    Toast.makeText(mContext, "网络断开，请稍候再试", Toast.LENGTH_LONG).show();
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 下载完成后自动安装apk
//     */
//    public void installAPK() {
//        File apkFile = new File(saveFileName);
//        if (!apkFile.getParentFile().exists()){
//            apkFile.getParentFile().mkdirs();
//        }
//        if (!apkFile.exists()) {
//            return;
//        }
//
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
//            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        }else {
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
//
//        }
//
//        if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
//            mContext.startActivity(intent);
//        }
//    }
//
//    /*
// * 获取当前程序的版本号
// */
//    private int getVersionCode(Context context) throws Exception {
//        //获取packagemanager的实例
//        PackageManager packageManager = context.getPackageManager();
//        //getPackageName()是你当前类的包名，0代表是获取版本信息
//        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
//        return packInfo.versionCode;
//    }
//
//    /*
//    * 获取当前程序的版本名称
//    */
//    public String getVersionName(Context context) throws Exception {
//        //获取packagemanager的实例
//        PackageManager packageManager = context.getPackageManager();
//        //getPackageName()是你当前类的包名，0代表是获取版本信息
//        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
//        return packInfo.versionName;
//    }
//
//    boolean hasReturn = false;
//
//    /*
//    * 从服务器获取xml解析并进行比对版本号
//    */
//    public boolean CheckVersion(Context context, String urlRe, final Boolean showToast, final Boolean isClose) {
//        isFromFlash = isClose;
//        hasReturn = false;
//
//        try {
//            clientVersion = getVersionCode(context);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        mContext = context;
//        EGRequestParams params = new EGRequestParams();
//        JSONObject user = PreferencesUtil.getInstatnce(context).getUser();
//        String token = "";
//
//        int v = 1;
////        try {
////            v = getVersionCode(context);
////            params.addBodyParameter("versionNo", clientVersion + "");
////            params.addBodyParameter("category", "1");
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
//
//        HttpHelper.postNoProcess2(context, urlRe, params, new HttpHelper.Ok() {
//            @Override
//            public void success(String str) {
//                hasReturn = true;
//                try {
//                    updateInfo = JSONObject.parseObject(str);
//                } catch (Exception e) {
//
//                }
//
//                if (updateInfo != null) {
//                    LogUtil.i(TAG, new Throwable(updateInfo.getString("path")));
//                    LogUtil.i(TAG, new Throwable("versionCode=" + clientVersion + "====updateInfo.getVERSIONNO()==" + updateInfo.getInteger("versionNo")));
//                    if (updateInfo.getInteger("versionNo") > clientVersion) {
//                        LogUtil.i(TAG, new Throwable("有新版本"));
//                        showNoticeDialog(isClose);
//                        return;
//                    } else {
//                        if (showToast) {
//                            Toast.makeText(mContext, "当前已经是最新版本！", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                } else {
//                    if (showToast) {
//                        Toast.makeText(mContext, "当前已经是最新版本！", Toast.LENGTH_LONG).show();
//                    }
////                    if (isClose){
////                        ((BaseAppActivity)mContext).startActivity(LoginActivity.class, "登录");
////                        ((BaseAppActivity)mContext).finish();
////                    }
//
//                }
//                if (isClose) {
//                    ((AsukaActivity) mContext).finish();
//                }
//            }
//
//            //isUpgrade
//            @Override
//            public void complete(String str) {
//                hasReturn = true;
//                if (isClose) {
//                    ((AsukaActivity) mContext).finish();
//                }
//            }
//        });
//
//        return true;
//    }
//
//    public void downloadAPK(String url){
//        InstallUtils.with(mContext)
//                //必须-下载地址
//                .setApkUrl(url)
//                //非必须，默认update
//                .setApkName("update")
//                //非必须-下载保存的路径
////                        .setApkPath(Constants.APK_SAVE_PATH)
//                //非必须-下载回调
//                .setCallBack(downloadCallBack)
//                //开始下载
//                .startDownload();
//    }
//
//
//}
