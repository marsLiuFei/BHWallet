package com.android.bhwallet.app.ChangeTel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/25
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class CheckPhoneActivity extends AsukaActivity {
    private TextView tel;
    //private TextView get_yzm;
    private EditText code;
    private Button commit;

    private String merUserId;
    private JSONObject user;

//    private int timeCount = 60;
//    private static final int TIME_START = 0X001;
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case TIME_START:
//                    timeCount--;
//                    if (timeCount<0){
//                        get_yzm.setEnabled(true);
//                        get_yzm.setText("获取验证码");
//                    }else {
//                        get_yzm.setEnabled(false);
//                        get_yzm.setText(timeCount+"s");
//                        sendEmptyMessageDelayed(TIME_START,1000);
//                    }
//                    break;
//            }
//        }
//    };
    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_phone);
        commit = findViewById(R.id.commit);
        tel = findViewById(R.id.tel);
       // get_yzm = findViewById(R.id.get_yzm);
        code = findViewById(R.id.code);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }

        //getUserMessage();
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkYzm();

            }
        });

//        get_yzm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getYzm();
//            }
//        });
    }

//    private void getUserMessage(){
//        EGRequestParams para = new EGRequestParams();
//        para.addBodyParameter("merUserId",merUserId);
//        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
//            @Override
//            public void success(String str) {
//                JSONObject object = JSON.parseObject(str);
//                user = object.getJSONObject("user");
//                tel.setText(user.getString("mobilePhone"));
//            }
//
//            @Override
//            public void complete(String str) {
//
//            }
//        });
//    }

//    private void getYzm(){
//
//        EGRequestParams para = new EGRequestParams();
//        para.addBodyParameter("merUserId",merUserId);
//        para.addBodyParameter("mobilePhone",user.getString("mobilePhone"));
//        para.addBodyParameter("msg","验证码");
//        HttpHelper.post(this, UrlConfig.CHECK_CARD, para, new HttpHelper.Ok() {
//            @Override
//            public void success(String str) {
//                showSuccess("验证码已发送，请注意查收");
//                timeCount = 60;
//                handler.sendEmptyMessageDelayed(TIME_START,1000);
//
//            }
//
//            @Override
//            public void complete(String str) {
//
//            }
//        });
//    }

    private void checkYzm(){
        final String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)){
            showWarning("请输入手机号码");
            return;
        }

        String strCode = code.getText().toString().trim();
        if (StringUtils.isEmpty(strCode)){
            showWarning("请输入验证码");
            return;
        }

        EGRequestParams  para = new EGRequestParams();
        para.addBodyParameter("MobilePhone",strTel);
        para.addBodyParameter("VerifyNo",strCode);
        HttpHelper.post(this, UrlConfig.CHECK_YZM, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                startActivity(ChangePhoneActivity.class,"变更手机号",bundle);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

}
