package com.android.bhwallet.app.Money.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Card.PayPasswordActivity;

import static com.android.bhwallet.app.Card.PayPasswordActivity.ELE_PAY_OUT;
import static com.android.bhwallet.app.Card.PayPasswordActivity.WALLET_PAY_OUT;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/28
 * 邮箱：825814902@qq.com
 * 描述：提现
 */
public class PayOutActivity extends AsukaActivity {
    private TextView tv_all_money;
    private TextView bank;
    private TextView pay_all;
    private EditText et_money;
    private Button commit;

    private String merUserId;
    private Boolean is_ele;
    private float allMoney;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_pay_out);
        tv_all_money = findViewById(R.id.tv_all_money);
        bank = findViewById(R.id.bank);
        et_money = findViewById(R.id.et_money);
        commit = findViewById(R.id.commit);
        pay_all = findViewById(R.id.pay_all);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
            is_ele = bundle.getBoolean("is_ele", false);
        }
        getData();
        getWalletMessage();
    }

    private void getData(){
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                if (is_ele) {
                    JSONArray eleAccList = object.getJSONArray("eleAccList");
                    if (eleAccList != null && eleAccList.size() > 0) {
                        JSONObject jsonObject = (JSONObject) eleAccList.get(0);
                        String acctNo = jsonObject.getString("EacCardNo");
                        bank.setText("尾号" + acctNo.substring(acctNo.length() - 4));
                    } else {
                        showWarning("请先绑定银行卡");
                        finish();
                    }
                } else {
                    JSONObject user = object.getJSONObject("user");
                    if (StringUtils.isEmpty(user.getString("acctNo"))){
                        showWarning("请先绑定银行卡");
                        finish();
                    }else {
                        String acctNo = user.getString("acctNo");
                        bank.setText(user.getString("acctBankName")+"\n("+acctNo.substring(acctNo.length()-4)+")");
                    }
                }


            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getWalletMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_MESSAGE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                if (is_ele){
                    allMoney = data.getFloatValue("eacAmt");
                    tv_all_money.setText("可提现金额￥"+data.getFloatValue("eacAmt"));
                }else {
                    allMoney = data.getFloatValue("aviAmt");
                    tv_all_money.setText("可提现金额￥"+data.getFloatValue("aviAmt"));
                }


            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = et_money.getText().toString().trim();
                if (StringUtils.isEmpty(money)){
                    showWarning("请填写提现金额");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("merUserId",merUserId);
                if (is_ele){
                    bundle.putInt("type",ELE_PAY_OUT);
                }else {
                    bundle.putInt("type",WALLET_PAY_OUT);
                }

                bundle.putString("money",money);
                startActivityForResult(PayPasswordActivity.class,"验证密码",bundle);
            }
        });

        pay_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_money.setText(allMoney+"");
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
