package com.android.bhwallet.app.Photo.UI;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.HttpUtils;
import com.asuka.android.asukaandroid.http.ResponseInfo;
import com.asuka.android.asukaandroid.http.callback.RequestCallBack;
import com.asuka.android.asukaandroid.http.exception.HttpException;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.android.bhwallet.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by JDD on 2016-07-14.
 */
public class PlayVideoActivity extends AsukaActivity {
    private String url;
    private SurfaceView surfaceview;

    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_video_play);
        surfaceview = findViewById(R.id.surfaceview);
        LogUtil.e("init-------------");
        getToolBar().setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            url = bundle.getString("url");
            if (!StringUtils.isEmpty(url)){
                downLoadVideo();
            }
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void downLoadVideo(){
        String target = Environment.getExternalStorageDirectory() + "/" + url;
        HttpUtils utils = new HttpUtils();
        Toast.makeText(this,"下载中...",Toast.LENGTH_SHORT).show();
//        showCustomToast("下载中...");
//        showLoadingDialog("下载中...");
        utils.download(url, target, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                if (responseInfo != null) {
                    File file = responseInfo.result;
                    playMovie(file);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    private void playMovie(File file){
        if (b)return;
        if (file != null && file.exists()){
            surfaceHolder = surfaceview.getHolder();
            surfaceHolder.setFixedSize(100, 100);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            mediaPlayer = new MediaPlayer();
            try{
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                if (surfaceHolder==null){
                    return;
                }
                mediaPlayer.setDisplay(surfaceHolder);//设置屏幕
                mediaPlayer.prepare();
                mediaPlayer.start();
            }catch (IOException ex){

            }

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer arg0) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                    finish();
                }
            });
        }
    }
    Boolean b=false;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        b=true;
        LogUtil.e("onDestroy-------------");
    }
}
