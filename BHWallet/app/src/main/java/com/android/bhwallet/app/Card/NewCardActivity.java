package com.android.bhwallet.app.Card;

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
 * 创建日期：2020/7/24
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class NewCardActivity extends AsukaActivity {
    private Button commit;
    private EditText bank_card;
    private EditText card_password;
    private TextView tel;
    private EditText code;
    private TextView get_yzm;

    private String merUserId;
    private JSONObject user;
    private String orderId;
    private String password;

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
        setContentView(R.layout.activity_new_card);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            password = bundle.getString("password");
        }
        commit = findViewById(R.id.commit);
        bank_card = findViewById(R.id.bank_card);
        card_password = findViewById(R.id.card_password);
        tel = findViewById(R.id.tel);
        code = findViewById(R.id.code);
        get_yzm = findViewById(R.id.get_yzm);

        getUserMessage();
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCardMessage();

            }
        });

        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
    }

    private void getUserMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                user = object.getJSONObject("user");
                tel.setText(user.getString("mobilePhone"));

            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getCode(){
        String strBankCard = bank_card.getText().toString().trim();
        if (StringUtils.isEmpty(strBankCard)) {
            showWarning("请输入新的银行卡号");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctName",user.getString("customerName"));
        para.addBodyParameter("acctNo",strBankCard);
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

    private void changeCard(JSONObject data) {
        if (StringUtils.isEmpty(orderId)){
            showWarning("请获取验证码");
            return;
        }
        String strBankCard = bank_card.getText().toString().trim();
        if (StringUtils.isEmpty(strBankCard)) {
            showWarning("请输入新的银行卡号");
            return;
        }
        String strPwd = card_password.getText().toString().trim();
        if (StringUtils.isEmpty(strPwd)){
            showWarning("请输入卡密码");
            return;
        }

        String strCode = code.getText().toString().trim();
        if (StringUtils.isEmpty(strCode)){
            showWarning("请输入验证码");
            return;
        }
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctNo", strBankCard);
        para.addBodyParameter("cardPassword", strPwd);
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("mobilePhone",user.getString("mobilePhone"));
        para.addBodyParameter("orderId",orderId);
        para.addBodyParameter("verifyNo", strCode);
        para.addBodyParameter("password",password);
        HttpHelper.post(this, UrlConfig.CHANGE_CARD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "卡号变更成功");
                startActivity(ChangeSuccessActivity.class, "更改绑定卡", bundle);
                sendObseverMsg("card_update", new JSONObject());
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getCardMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                changeCard(data);
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
