package com.android.bhwallet.app.Save.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class BuyActivity extends AsukaActivity {
    private TextView card_num;
    private TextView money;
    private TextView time;
    private TextView rate;
    private EditText save_money;
    private Button next;

    private String merUserId;
    private String productCode;
    private String bankNum;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_buy);
        card_num = findViewById(R.id.card_num);
        money = findViewById(R.id.money);
        time = findViewById(R.id.time);
        rate = findViewById(R.id.rate);
        save_money = findViewById(R.id.save_money);
        next = findViewById(R.id.next);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            productCode = bundle.getString("productCode");
        }

        getUserData();
        getWalletMessage();
        getProductDetail();
    }

    @Override
    protected void initEvent() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String money = save_money.getText().toString().trim();
                if (StringUtils.isEmpty(money)){
                    showWarning("请输入存入金额");
                    return;
                }else {
                    Integer iMoney = Integer.parseInt(money);
                    if(iMoney<=0){
                        showWarning("请输入正确的金额");
                        return;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("card_num",card_num.getText().toString());
                bundle.putString("time",time.getText().toString());
                bundle.putString("rate",rate.getText().toString());
                bundle.putString("money", money);
                bundle.putString("merUserId",merUserId);
                bundle.putString("productCode",productCode);
                bundle.putString("EacBindAcctNo",bankNum);
                startActivityForResult(BuyPasswordActivity.class, toolBar.getTitle().toString(), bundle);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void getUserData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                JSONArray eleAccList = data.getJSONArray("eleAccList");
                if (eleAccList != null && eleAccList.size() > 0) {
                    JSONObject object = (JSONObject) eleAccList.get(0);
                    String EacBindAcctNo = object.getString("EacBindAcctNo");
                    bankNum = EacBindAcctNo;
                    if (!StringUtils.isEmpty(EacBindAcctNo) && EacBindAcctNo.length() > 4) {
                        EacBindAcctNo = EacBindAcctNo.substring(0,4)+" **** **** " + EacBindAcctNo.substring(EacBindAcctNo.length() - 4);

                    }
                    card_num.setText(EacBindAcctNo);
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
                money.setText(data.getString("eacTotalAmt")+"元");
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getProductDetail() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("productCode", productCode);
        HttpHelper.post(this, UrlConfig.PRODUCT_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray array = object.getJSONArray("list");
                if (array != null && array.size() > 0) {
                    JSONObject jsonObject = (JSONObject) array.get(0);
                    String termType = jsonObject.getString("termType");
                    if ("Y".equalsIgnoreCase(termType)) {
                        termType = "年";
                    } else if ("M".equalsIgnoreCase(termType)) {
                        termType = "个月";
                    } else if ("D".equalsIgnoreCase(termType)) {
                        termType = "天";
                    }
                    time.setText(jsonObject.getString("term") + termType);

                    NumberFormat nf = NumberFormat.getPercentInstance();
                    try {
                        Number number = nf.parse(jsonObject.getString("exeRate"));
                        rate.setText(number.floatValue() + "%");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.tb_describe) {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            finish();
        }
    }
}
