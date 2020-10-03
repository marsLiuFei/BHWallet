package com.android.bhwallet.app.Finance;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

public class EleResetPasswordActivity extends AsukaActivity {
    private EditText old_password;
    private EditText new_password;
    private EditText new_password1;
    private Button commit;

    private String merUserId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_ele_reset_password);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }

        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        new_password1 = findViewById(R.id.new_password1);
        commit = findViewById(R.id.commit);


    }

    @Override
    protected void initEvent() {
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

    private void commitData() {
        String strOldPassword = old_password.getText().toString().trim();
        if (StringUtils.isEmpty(strOldPassword)) {
            showWarning("请输入原密码");
            return;
        }
        String strNewPassword = new_password.getText().toString().trim();
        if (StringUtils.isEmpty(strNewPassword)) {
            showWarning("请输入新密码");
            return;
        }

        String strNewPassword1 = new_password1.getText().toString().trim();
        if (StringUtils.isEmpty(strNewPassword1)) {
            showWarning("请输入确认新密码");
            return;
        }

        if (!strNewPassword.equals(strNewPassword1)) {
            showWarning("两次输入的新密码不一致，请确认后重新输入");
            return;
        } else if (strNewPassword.length() != 6) {
            showWarning("密码必须是6位数字");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("password",strNewPassword);
        para.addBodyParameter("oldPassword",strOldPassword);
        para.addBodyParameter("flag","2");
        HttpHelper.post(this, UrlConfig.MODIFY_ELE_PASSWORD, para, new HttpHelper.Ok() {
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
