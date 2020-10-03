package com.android.bhwallet.app.Money.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/28
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class MoneyActivity extends AsukaActivity {
    private TextView money;
    private LinearLayout pay_in;
    private LinearLayout pay_out;
    private String merUserId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_money);
        pay_in = findViewById(R.id.pay_in);
        pay_out = findViewById(R.id.pay_out);
        money = findViewById(R.id.money);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
            getWalletMessage();
        }
    }

    private void getWalletMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_MESSAGE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = new JSONObject();
                money.setText(data.getFloatValue("aviAmt") + "元");
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void initEvent() {
        pay_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                startActivity(PayInActivity.class,"余额充值",bundle);
            }
        });
        pay_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                startActivity(PayOutActivity.class,"余额提现",bundle);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tb_detail){
            startActivity(MoneyListActivity.class,"账单");
        }
        return super.onOptionsItemSelected(item);
    }
}
