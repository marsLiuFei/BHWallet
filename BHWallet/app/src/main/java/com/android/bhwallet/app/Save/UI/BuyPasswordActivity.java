package com.android.bhwallet.app.Save.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.ChangeSuccessActivity;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class BuyPasswordActivity extends AsukaActivity {
    private TextView card_num;
    private TextView time;
    private TextView rate;
    private TextView money;
    private TextView password;
    private Button commit;

    private String merUserId;
    private String productCode;
    private String bankNum;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_buy_password);
        card_num = findViewById(R.id.card_num);
        time = findViewById(R.id.time);
        rate = findViewById(R.id.rate);
        money = findViewById(R.id.money);
        password = findViewById(R.id.password);
        commit = findViewById(R.id.commit);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bankNum = bundle.getString("bankNum");
            if (!StringUtils.isEmpty(bankNum) && bankNum.length() > 4) {
                card_num.setText(bankNum.substring(0,3)+" **** **** " + bankNum.substring(bankNum.length() - 4));
            }

            time.setText(bundle.getString("time"));
            rate.setText(bundle.getString("rate"));
            money.setText(bundle.getString("money"));
            merUserId = bundle.getString("merUserId");
            productCode = bundle.getString("productCode");
        }
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

    private void commitData(){
        String strPassword = password.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword)) {
            showWarning("请输入密码");
            return;
        }else if (strPassword.length() != 6){
            showWarning("请输入6位数字密码");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        para.addBodyParameter("certNo",bankNum);
        para.addBodyParameter("productCode",productCode);
        para.addBodyParameter("transAmount",money.getText().toString());
        para.addBodyParameter("passWord",strPassword);
        HttpHelper.post(this, UrlConfig.BUY_PRODUCT, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "购买成功！");
                startActivity(ChangeSuccessActivity.class, toolBar.getTitle().toString(), bundle);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });



    }
}
