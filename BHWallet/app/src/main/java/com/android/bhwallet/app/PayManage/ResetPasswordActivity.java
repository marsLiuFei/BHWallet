package com.android.bhwallet.app.PayManage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/10/3
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class ResetPasswordActivity extends AsukaActivity {
    private TextView bank_card;
    private TextView tel;
    private TextView get_yzm;
    private EditText code;
    private EditText card_password;

    private Button commit;

    JSONObject user;

    private String merUserId;
    private String orderId;

    private int timeCount = 60;
    private static final int TIME_START = 0X001;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIME_START:
                    timeCount--;
                    if (timeCount<0){
                        get_yzm.setEnabled(true);
                        get_yzm.setText("获取验证码");
                    }else {
                        get_yzm.setEnabled(false);
                        get_yzm.setText(timeCount+"s");
                        sendEmptyMessageDelayed(TIME_START,1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reset_password);

        bank_card = findViewById(R.id.bank_card);
        tel = findViewById(R.id.tel);
        get_yzm = findViewById(R.id.get_yzm);
        code = findViewById(R.id.code);
        card_password = findViewById(R.id.card_password);
        commit = findViewById(R.id.commit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }

        getUserMessage();
    }

    @Override
    protected void initEvent() {
        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYzm();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void getUserMessage(){
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                user = object.getJSONObject("user");
                bank_card.setText(user.getString("acctNo"));
                tel.setText(user.getString("mobilePhone"));
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getYzm(){
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctName",user.getString("customerName"));
        para.addBodyParameter("acctNo",user.getString("acctNo"));
        para.addBodyParameter("certNo",user.getString("certNo"));
        para.addBodyParameter("mobilePhone",user.getString("mobilePhone"));

        HttpHelper.post(this, UrlConfig.CHECK_CARD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                orderId = object.getString("orderId");
                showSuccess("验证码已发送，请注意查收");
                timeCount = 60;
                handler.sendEmptyMessageDelayed(TIME_START,1000);

            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void commitData(){
        String strCode = code.getText().toString();
        if (StringUtils.isEmpty(strCode)){
            showWarning("请输入验证码");
            return;
        }

        String strPassword = card_password.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword)){
            showWarning("请输入密码");
            return;
        }else if (strPassword.length() != 6){
            showWarning("请输入6位数字密码");
            return;
        }

        EGRequestParams para= new EGRequestParams();
        para.addBodyParameter("certNo",user.getString("certNo"));
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("mobilePhone",user.getString("mobilePhone"));
        para.addBodyParameter("orderId",orderId);
        para.addBodyParameter("password",strPassword);
        para.addBodyParameter("verifyNo",strCode);
        HttpHelper.post(this, UrlConfig.RESET_PASSWORD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("修改成功");
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }
}
