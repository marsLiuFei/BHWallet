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
public class FindPasswordActivity extends AsukaActivity {
    private Button modify_password;

    private TextView tel;
    private TextView get_yzm;
    private EditText code;
    private EditText password;

    private String merUserId;
    private String mobilePhone;
    private String certNo;
    private String acctName;
    private String acctNo;

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
        setContentView(R.layout.activity_find_password);

        modify_password = findViewById(R.id.modify_password);
        tel = findViewById(R.id.tel);
        get_yzm = findViewById(R.id.get_yzm);
        code = findViewById(R.id.code);
        password = findViewById(R.id.password);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
            mobilePhone = bundle.getString("mobilePhone");
            tel.setText(mobilePhone);
            certNo = bundle.getString("certNo");
            acctName = bundle.getString("acctName");
            acctNo = bundle.getString("acctNo");
        }
    }

    @Override
    protected void initEvent() {
        modify_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();

            }
        });

        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYzm();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void getYzm(){
        String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)){
            showWarning("请输入手机号码");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctName",acctName);
        para.addBodyParameter("acctNo",acctNo);
        para.addBodyParameter("certNo",certNo);
        para.addBodyParameter("mobilePhone",mobilePhone);

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

    private void commit(){
        String strCode = code.getText().toString().trim();
        if (StringUtils.isEmpty(strCode)){
            showWarning("请输入验证码");
            return;
        }

        String strPwd = password.getText().toString().trim();
        if (StringUtils.isEmpty(strPwd)){
            showWarning("请输入新密码");
            return;
        }else if (strPwd.length() != 6){
            showWarning("密码必须是6位数字");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("certNo",certNo);
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("mobilePhone",mobilePhone);
        para.addBodyParameter("orderId",orderId);
        para.addBodyParameter("password",strPwd);
        para.addBodyParameter("verifyNo",strCode);
        HttpHelper.post(this, UrlConfig.RESET_PASSWORD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title","密码修改成功！");
                startActivity(ResetPasswordSuccessActivity.class,"找回密码",bundle);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });
    }

}
