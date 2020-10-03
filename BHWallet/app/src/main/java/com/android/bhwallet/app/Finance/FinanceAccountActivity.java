package com.android.bhwallet.app.Finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.EleNewCardActivity;
import com.android.bhwallet.app.Card.PayPasswordActivity;
import com.android.bhwallet.app.Money.UI.MoneyListActivity;
import com.android.bhwallet.app.Money.UI.PayInActivity;
import com.android.bhwallet.app.Money.UI.PayOutActivity;
import com.android.bhwallet.app.WriteOff.WriteOffActivity;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/23
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class FinanceAccountActivity extends AsukaActivity {

    private TextView money;
    private TextView zd;
    private TextView pay_in;
    private TextView pay_out;
    private TextView ele_change_card;
    private TextView reset_password;
    private TextView modify_tel;

    private TextView cancellation;

    private String merUserId;
    private String EacAcctNo;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_finance_account);
        money = findViewById(R.id.money);
        zd = findViewById(R.id.zd);
        pay_in = findViewById(R.id.pay_in);
        pay_out = findViewById(R.id.pay_out);
        ele_change_card = findViewById(R.id.ele_change_card);
        reset_password = findViewById(R.id.reset_password);
        modify_tel = findViewById(R.id.modify_tel);
        cancellation = findViewById(R.id.cancellation);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            EacAcctNo = bundle.getString("EacAcctNo");
        }

        getEleMessage();
    }

    @Override
    protected void initEvent() {
        zd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("merUserId",merUserId);
//                bundle.putBoolean("is_ele",true);
//                startActivity(MoneyListActivity.class,"账单",bundle);
            }
        });

        pay_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                bundle.putBoolean("is_ele", true);
                startActivityForResult(PayInActivity.class, "充值", bundle);
            }
        });

        pay_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                bundle.putBoolean("is_ele", true);
                startActivityForResult(PayOutActivity.class, "提现", bundle);
            }
        });

        ele_change_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                startActivity(EleNewCardActivity.class, "修改绑定卡", bundle);
            }
        });
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                startActivity(EleResetPasswordActivity.class, "重置密码", bundle);
            }
        });

        modify_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                startActivity(EleModifyTelActivity.class, "修改手机号", bundle);
            }
        });

        cancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                bundle.putBoolean("is_ele", true);
                startActivityForResult(WriteOffActivity.class, "注销账户", bundle);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void getEleMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_MESSAGE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                money.setText(object.getString("eacAmt"));
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null){
                    if ("ele_write_off".equals(bundle.getString("type"))){
                        finish();
                    }else {
                        getEleMessage();
                    }
                }
            }

        }
    }
}
