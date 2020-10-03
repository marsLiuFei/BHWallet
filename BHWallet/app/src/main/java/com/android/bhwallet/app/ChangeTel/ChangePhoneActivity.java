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
import com.android.bhwallet.app.PayManage.ResetPasswordSuccessActivity;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/25
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class ChangePhoneActivity extends AsukaActivity {
    private Button commit;

    private EditText tel;
    private EditText password;
    private TextView get_yzm;
    private EditText code;

    private String merUserId;
    private String orderId;

    private int timeCount = 60;
    private static final int TIME_START = 0X001;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_START:
                    timeCount--;
                    if (timeCount < 0) {
                        get_yzm.setEnabled(true);
                        get_yzm.setText("获取验证码");
                    } else {
                        get_yzm.setEnabled(false);
                        get_yzm.setText(timeCount + "s");
                        sendEmptyMessageDelayed(TIME_START, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_phone);
        commit = findViewById(R.id.commit);
        tel = findViewById(R.id.tel);
        password = findViewById(R.id.password);
        get_yzm = findViewById(R.id.get_yzm);
        code = findViewById(R.id.code);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });

        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTel = tel.getText().toString().trim();
                if (StringUtils.isEmpty(strTel)) {
                    showWarning("请输入手机号");
                    return;
                }
                getUserMessage(strTel);
            }
        });
    }

    private void getCode() {
        String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)) {
            showWarning("请输入手机号");
            return;
        }
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("mobilePhone", strTel);
        HttpHelper.post(this, UrlConfig.ELE_GET_CODE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                showSuccess("验证码已发送，请注意查收");
                timeCount = 60;
                handler.sendEmptyMessageDelayed(TIME_START, 1000);

            }

            @Override
            public void complete(String str) {

            }
        });
    }

    /**
     * 获取用户信息
     */
    private void getUserMessage(final String strTel) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONObject user = object.getJSONObject("user");
                checkTel(user.getString("acctNo"), user.getString("customerName"), user.getString("certNo"), strTel);
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void checkTel(final String strBankCard, final String strName, final String strIdCard, final String strTel) {
        EGRequestParams object = new EGRequestParams();
        object.addBodyParameter("acctNo", strBankCard);
        object.addBodyParameter("acctName", strName);
        object.addBodyParameter("certNo", strIdCard);
        object.addBodyParameter("mobilePhone", strTel);
        HttpHelper.post(this, UrlConfig.CHECK_CARD, object, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                orderId = data.getString("orderId");
                showSuccess("验证码已发送，请注意查收");
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void commitData() {
        String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)) {
            showWarning("请输入手机号");
            return;
        }
        String strCode = code.getText().toString().trim();
        if (StringUtils.isEmpty(strCode)) {
            showWarning("请输入验证码");
            return;
        }

        String strPassword = password.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword)) {
            showWarning("请输入交易密码");
            return;
        } else if (strPassword.length() != 6) {
            showWarning("请输入6位交易密码");
            return;
        }
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("mobilePhone", strTel);
        para.addBodyParameter("password", strPassword);
        para.addBodyParameter("orderId", orderId);
        para.addBodyParameter("verifyNo", strCode);
        HttpHelper.post(this, UrlConfig.WALLET_MODIFY_TEL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "手机号变更成功！");
                startActivity(ResetPasswordSuccessActivity.class, "变更手机号", bundle);
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
