package com.android.bhwallet.app.Photo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.asuka.android.asukaandroid.widget.photo.MovieRecorderView;
import com.android.bhwallet.R;


/**
 * Auther:egojit
 * Time:2016-6-30
 * QQ:408365330
 * Mark:录像
 */
public class RecordMediaAcitivty extends AsukaActivity {
    private Button mShootBtn;
    private MovieRecorderView mRecorderView;
    private boolean isFinish = true;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_media_record);
        mShootBtn = findViewById(R.id.shoot_button);
        mRecorderView = findViewById(R.id.movieRecorderView);
        getToolBar().setVisibility(View.GONE);
        mShootBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mRecorderView.record(new MovieRecorderView.OnRecordFinishListener() {

                        @Override
                        public void onRecordFinish() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (mRecorderView.getTimeCount() > 1)
                        handler.sendEmptyMessage(1);
                    else {
                        if (mRecorderView.getVecordFile() != null)
                            mRecorderView.getVecordFile().delete();
                        mRecorderView.stop();
                        Toast.makeText(RecordMediaAcitivty.this, "视频录制时间太短", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }


    @Override
    public void onResume() {
        super.onResume();
        isFinish = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isFinish = false;
        mRecorderView.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecorderView.stop();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finishActivity();
        }
    };

    private void finishActivity() {
        if (isFinish) {
            mRecorderView.stop();
            String str=mRecorderView.getVecordFile().toString();
            Bundle bundle=new Bundle();
            bundle.putString("fileUrl",str);
            Intent intent=new Intent();
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        }
    }

    /**
     * 录制完成回调
     *
     * @author liuyinjun
     *
     * @date 2015-2-9
     */
    public interface OnShootCompletionListener {
        public void OnShootSuccess(String path, int second);
        public void OnShootFailure();
    }



}
