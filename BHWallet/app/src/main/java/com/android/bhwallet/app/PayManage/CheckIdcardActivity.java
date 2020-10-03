package com.android.bhwallet.app.PayManage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class CheckIdcardActivity extends AsukaActivity {
    private EditText name;
    private EditText num;
    private Button commit;

    private String merUserId;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_check_idcard);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }
        commit = findViewById(R.id.commit);
        name = findViewById(R.id.name);
        num = findViewById(R.id.num);
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();

            }
        });
    }

    private void check(){
        final String strName = name.getText().toString().trim();
        if (StringUtils.isEmpty(strName)){
            showWarning("请输入姓名");
            return;
        }

        final String strNum = num.getText().toString().trim();
        if (StringUtils.isEmpty(strNum)){
            showWarning("请输入身份证号");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONObject user = object.getJSONObject("user");
                if (strName.equals(user.getString("customerName")) && strNum.equalsIgnoreCase(user.getString("certNo"))){
                    Bundle bundle = new Bundle();
                    bundle.putString("merUserId",merUserId);
                    bundle.putString("mobilePhone",user.getString("mobilePhone"));
                    bundle.putString("acctName",user.getString("customerName"));
                    bundle.putString("acctNo",user.getString("acctNo"));
                    bundle.putString("certNo",strNum);
                    startActivity(FindPasswordActivity.class,"找回密码",bundle);
                    finish();
                }else {
                    showWarning("信息不匹配，验证失败");
                }
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
