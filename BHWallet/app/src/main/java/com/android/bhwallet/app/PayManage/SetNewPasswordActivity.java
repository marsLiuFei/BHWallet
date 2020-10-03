package com.android.bhwallet.app.PayManage;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.CodeView.VerificationCodeView;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/24
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class SetNewPasswordActivity extends AsukaActivity implements VerificationCodeView.OnCodeFinishListener {
    private VerificationCodeView password;

    private String merUserId;
    private String oldPassword;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_new_password);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
            oldPassword = bundle.getString("oldPassword");
        }
        password = findViewById(R.id.password);
    }

    @Override
    protected void initEvent() {
        password.setOnCodeFinishListener(this);
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    public void onTextChange(View view, String content) {

    }

    @Override
    public void onComplete(View view, String content) {
        updatePassword(content);

    }

    private void updatePassword(String newPassword){
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("newPassword",newPassword);
        para.addBodyParameter("oldPassword",oldPassword);

        HttpHelper.post(this, UrlConfig.UPDATE_PWD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "密码重置成功！");
                startActivity(ResetPasswordSuccessActivity.class, "支付密码重置", bundle);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });

    }
}
