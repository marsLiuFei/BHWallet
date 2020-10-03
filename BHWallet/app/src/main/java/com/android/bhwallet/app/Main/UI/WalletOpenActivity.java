package com.android.bhwallet.app.Main.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.app.Main.Interface.OnSexClickListener;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.ImageUtil;
import com.android.bhwallet.utils.UrlConfig;
import com.android.bhwallet.views.WiperSwitch;
import com.asuka.android.asukaandroid.AsukaTakePhotoActivity;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.dhc.gallery.ui.GalleryActivity;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/20
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class WalletOpenActivity extends AsukaTakePhotoActivity {
    private Button open;
    private EditText name;
    private EditText idcard;
    private ImageView card1;
    private ImageView card2;
    private EditText tel;
    private EditText bank_card;
    private EditText password1;
    private EditText password2;
    private EditText card_password;
    private TextView get_yzm;
    private EditText code;

    private WiperSwitch ws;

    private int clickIndex;
    private String outputPath;
    private String cardPath1;
    private String cardPath2;

    private String merUserId;
    private String mUserId;
    private String orderId;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wallet_open);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            mUserId = bundle.getString("mUserId");
        }


        name = findViewById(R.id.name);
        idcard = findViewById(R.id.idcard);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        tel = findViewById(R.id.tel);
        bank_card = findViewById(R.id.bank_card);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        card_password = findViewById(R.id.card_password);
        ws = findViewById(R.id.ws);
        ws.setChecked(true);
        get_yzm = findViewById(R.id.get_yzm);
        code = findViewById(R.id.code);

        open = findViewById(R.id.open);
    }

    @Override
    protected void initEvent() {
        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardCheck();
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickIndex = 1;
                outputPath = getOutputPath();
                FileUtil.showSelectSingleDialog(WalletOpenActivity.this, "");
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickIndex = 2;
                outputPath = getOutputPath();
                FileUtil.showSelectSingleDialog(WalletOpenActivity.this, "");
            }
        });

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void cardCheck() {

        final String strName = name.getText().toString();
        if (StringUtils.isEmpty(strName)) {
            showWarning("请输入姓名");
            return;
        }

        final String strIdCard = idcard.getText().toString().trim();
        if (StringUtils.isEmpty(strIdCard)) {
            showWarning("请输入身份证号");
            return;
        }


        final String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)) {
            showWarning("请输入手机号码");
            return;
        }

        final String strBackCard = bank_card.getText().toString();
        if (StringUtils.isEmpty(strBackCard)) {
            showWarning("请输入银行卡号");
            return;
        }


        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctName",strName);
        para.addBodyParameter("acctNo",strBackCard);
        para.addBodyParameter("certNo",strIdCard);
        para.addBodyParameter("mobilePhone",strTel);
        HttpHelper.post(this, UrlConfig.CHECK_CARD, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                orderId = object.getString("orderId");

            }

            @Override
            public void complete(String str) {

            }
        });

    }

    private void  commitData() {
        final String strName = name.getText().toString();
        if (StringUtils.isEmpty(strName)) {
            showWarning("请输入姓名");
            return;
        }

        final String strIdCard = idcard.getText().toString().trim();
        if (StringUtils.isEmpty(strIdCard)) {
            showWarning("请输入身份证号");
            return;
        }

        if (StringUtils.isEmpty(cardPath1)) {
            showWarning("请上传身份证正面照片");
            return;
        }
        if (StringUtils.isEmpty(cardPath2)) {
            showWarning("请上传身份证背面照片");
            return;
        }

        final String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)) {
            showWarning("请输入手机号码");
            return;
        }

        String strCode = code.getText().toString().trim();
        if (StringUtils.isEmpty(strCode)){
            showWarning("请输入验证码");
            return;
        }

        final String strBackCard = bank_card.getText().toString();
        if (StringUtils.isEmpty(strBackCard)) {
            showWarning("请输入银行卡号");
            return;
        }

        final String cardPassword = card_password.getText().toString().trim();
        if (StringUtils.isEmpty(cardPassword)){
            showWarning("请输入银行卡密码");
            return;
        }else if (cardPassword.length() != 6){
            showWarning("请输入6位银行卡密码");
            return;
        }


        final String strPassword1 = password1.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword1)) {
            showWarning("请输入交易密码");
            return;
        }else if (strPassword1.length() != 6){
            showWarning("请输入6位交易密码");
            return;
        }

        String strPassword2 = password2.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword2)) {
            showWarning("请输入确认密码");
            return;
        }

        if (!strPassword1.equals(strPassword2)) {
            showWarning("两次密码不一致，请确认后重新输入");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctName",strName);
        para.addBodyParameter("acctNo", strBackCard);
        para.addBodyParameter("certNo", strIdCard);

        para.addBodyParameter("idImgBack", cardPath1);
        para.addBodyParameter("IdImgFront", cardPath2);
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("mobilePhone", strTel);
        para.addBodyParameter("orderId",orderId);
        para.addBodyParameter("password", strPassword1);
        para.addBodyParameter("cardPassword", cardPassword);
        para.addBodyParameter("UserEa", ws.getNowStatus() ? "1" : "0");
        para.addBodyParameter("verifyNo",strCode);
        para.addBodyParameter("mUserId",mUserId);


        HttpHelper.post(this, UrlConfig.OPEN_WALLET, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("UserEa",ws.getNowStatus());
                bundle.putString("merUserId",merUserId);
                startActivity(WalletOpenSuccessActivity.class, "开通钱包", bundle);
                sendObseverMsg("update_user",new JSONObject());
                finish();
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
            if (requestCode == 12) {
                ArrayList<String> path = data.getStringArrayListExtra(GalleryActivity.PHOTOS);
                upPic(path.get(0));

            }
        }
    }

    private void upPic(String path) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("file", new File(path));
        HttpHelper.post(this, UrlConfig.UP_PIC, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                switch (clickIndex) {
                    case 1:
                        cardPath1 = object.getString("filePath");
                        ImageUtil.ShowIamge(card1, UrlConfig.BASE_IMAGE_URL + cardPath1);
                        break;
                    case 2:
                        cardPath2 = object.getString("filePath");
                        ImageUtil.ShowIamge(card2, UrlConfig.BASE_IMAGE_URL + cardPath2);
                        break;
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }
}
