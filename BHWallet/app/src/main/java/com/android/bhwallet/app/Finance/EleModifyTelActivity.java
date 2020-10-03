package com.android.bhwallet.app.Finance;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

public class EleModifyTelActivity extends AsukaActivity {
    private TextView card_num;
    private EditText tel;
    private EditText code;
    private TextView get_yzm;
    private Button commit;

    private String merUserId;

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

        setContentView(R.layout.activity_ele_modify_tel);
        card_num = findViewById(R.id.card_num);
        tel = findViewById(R.id.tel);
        code = findViewById(R.id.code);
        get_yzm = findViewById(R.id.get_yzm);
        commit = findViewById(R.id.commit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }

        getEleMessage();
    }

    @Override
    protected void initEvent() {
        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commitData();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void getEleMessage(){
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray eleAccList = object.getJSONArray("eleAccList");
                if (eleAccList != null && eleAccList.size() > 0) {
                    JSONObject jsonObject = (JSONObject) eleAccList.get(0);
                    card_num.setText(jsonObject.getString("EacBindAcctNo"));
                } else {
                    showWarning("请先开通电子钱包");
                    finish();
                }

            }

            @Override
            public void complete(String str) {

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

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("mobilePhone", strTel);
        para.addBodyParameter("verifyNo", strCode);
        para.addBodyParameter("acctNo",card_num.getText().toString());
        HttpHelper.post(this, UrlConfig.MODIFY_ELE_CARD, para, new HttpHelper.Ok() {
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
