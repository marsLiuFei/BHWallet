package com.android.bhwallet.app.Money.UI;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.android.bhwallet.R;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/30
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class MoneyDetailctivity extends AsukaActivity {
    private TextView money;
    private TextView remark;
    private TextView pay_way;
    private TextView date;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_money_detail);

        money = findViewById(R.id.money);
        remark = findViewById(R.id.remark);
        pay_way = findViewById(R.id.pay_way);
        date = findViewById(R.id.date);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String data = bundle.getString("data");
            JSONObject object = JSON.parseObject(data);
            money.setText("C".equals(object.getString("dorc")) ? object.getString("cebitAmount") : object.getString("debitAmount"));
            remark.setText(object.getString("transRemark"));
            pay_way.setText("余额支付");
            date.setText(object.getString("acctTransTime"));
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }
}
