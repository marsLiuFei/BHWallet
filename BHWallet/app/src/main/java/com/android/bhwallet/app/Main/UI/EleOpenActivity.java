package com.android.bhwallet.app.Main.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Main.Interface.OnSexClickListener;
import com.android.bhwallet.utils.BitmapUtils;
import com.android.bhwallet.utils.Constants;
import com.android.bhwallet.utils.FileUtil;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.ImageUtil;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaTakePhotoActivity;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.utils.PermissionTool;
import com.asuka.android.asukaandroid.widget.pickview.DatePickerPopWin;
import com.dhc.gallery.GalleryConfig;
import com.dhc.gallery.GalleryHelper;
import com.dhc.gallery.ui.GalleryActivity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/20
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class EleOpenActivity extends AsukaTakePhotoActivity {
    private Button open;
    private ImageView head;
    private EditText name;
    private EditText idcard;
    private TextView start_time;
    private TextView end_time;
    private EditText address;
    private ImageView card1;
    private ImageView card2;
    private EditText tel;
    private TextView get_yzm;
    private EditText code;

    private EditText bank_card;
    private TextView job;
    private EditText password1;
    private EditText password2;
    private TextView sex;

    private int clickIndex;
    private String outputPath;
    private String headPath;
    private String cardPath1;
    private String cardPath2;
    private int iSex;

    private String merUserId;
    private String jobId;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_ele_open);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }

        iSex = -1;
        head = findViewById(R.id.head);
        name = findViewById(R.id.name);
        idcard = findViewById(R.id.idcard);
        start_time = findViewById(R.id.start_time);
        end_time = findViewById(R.id.end_time);
        address = findViewById(R.id.address);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        tel = findViewById(R.id.tel);
        get_yzm = findViewById(R.id.get_yzm);
        code = findViewById(R.id.code);

        bank_card = findViewById(R.id.bank_card);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        sex = findViewById(R.id.sex);
        job = findViewById(R.id.job);

        open = findViewById(R.id.open);

        getUserMessage();
    }

    @Override
    protected void initEvent() {
        get_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getYzm();
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitData();
            }
        });

        job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(JobListActivity.class, "职业列表", null);
            }
        });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickIndex = 0;
                outputPath = getOutputPath();
//                if (Build.VERSION.SDK_INT > 22) {
//                    List<String> permissionList = new ArrayList<>();
//                    if (!PermissionTool.isCameraCanUse())
//                        permissionList.add(android.Manifest.permission.CAMERA);
//                    int check = ContextCompat.checkSelfPermission(EleOpenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//                    if (check != PackageManager.PERMISSION_GRANTED) {
//                        permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    }
//                    if (permissionList.size() == 0) {
//                        GalleryHelper.with(EleOpenActivity.this).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
//                    } else {
//                        ActivityCompat.requestPermissions(EleOpenActivity.this,
//                                permissionList.toArray(new String[permissionList.size()]), Constants.media_ok);
//                    }
//                } else {
//                    GalleryHelper.with(EleOpenActivity.this).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
//                }
                FileUtil.showSelectSingleDialog(EleOpenActivity.this, "");
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickIndex = 1;
                outputPath = getOutputPath();
                FileUtil.showSelectSingleDialog(EleOpenActivity.this, "");
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickIndex = 2;
                outputPath = getOutputPath();
                FileUtil.showSelectSingleDialog(EleOpenActivity.this, "");
            }
        });

        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUtil.SexSelectDialog(EleOpenActivity.this, new OnSexClickListener() {
                    @Override
                    public void onClick(int index, String name) {
                        sex.setText(name);
                        iSex = index;
                    }
                });
            }
        });

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerPopWin pop = new DatePickerPopWin(new DatePickerPopWin.Builder(EleOpenActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        start_time.setText(dateDesc);
                    }
                }, false));

                pop.showPopWin(EleOpenActivity.this);
            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerPopWin pop = new DatePickerPopWin(new DatePickerPopWin.Builder(EleOpenActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        end_time.setText(dateDesc);
                    }
                }, true));
                pop.showPopWin(EleOpenActivity.this);
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 12) {
                ArrayList<String> path = data.getStringArrayListExtra(GalleryActivity.PHOTOS);
                upPic(path.get(0));

            } else {
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        jobId = bundle.getString("id");
                        job.setText(bundle.getString("name"));
                    }

                }

            }
        }
    }

    private void upPic(String path) {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("file", new File(path));
        HttpHelper.post(this, UrlConfig.UP_PIC, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject data = JSON.parseObject(str);
                switch (clickIndex) {
                    case 0:
                        headPath = data.getString("filePath");
                        ImageUtil.ShowIamge(head, UrlConfig.BASE_IMAGE_URL + headPath);
                        break;
                    case 1:
                        cardPath1 = data.getString("filePath");
                        ImageUtil.ShowIamge(card1, UrlConfig.BASE_IMAGE_URL + cardPath1);
                        break;
                    case 2:
                        cardPath2 = data.getString("filePath");
                        ImageUtil.ShowIamge(card2, UrlConfig.BASE_IMAGE_URL + cardPath2);
                        break;
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void getYzm() {
        final String strTel = tel.getText().toString().trim();
        if (StringUtils.isEmpty(strTel)) {
            showWarning("请输入手机号码");
            return;
        }

        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("mobilePhone", strTel);
        HttpHelper.post(this, UrlConfig.ELE_GET_CODE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("验证码已发送，请注意查收");
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    private void commitData() {
        if (StringUtils.isEmpty(headPath)) {
            showWarning("请拍摄人脸");
            return;
        }
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

        final String strStartTime = start_time.getText().toString().trim();
        if (StringUtils.isEmpty(strStartTime)) {
            showWarning("请选择身份证签发日期");
            return;
        }

        final String strEndTime = end_time.getText().toString().trim();
        if (StringUtils.isEmpty(strEndTime)) {
            showWarning("请选择身份证到期日期");
            return;
        }

        final String strAddress = address.getText().toString().trim();
        if (StringUtils.isEmpty(strAddress)) {
            showWarning("请输入户籍地址");
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

        final String strPassword1 = password1.getText().toString().trim();
        if (StringUtils.isEmpty(strPassword1)) {
            showWarning("请输入交易密码");
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

        if (iSex == -1) {
            showWarning("请选择性别");
            return;
        }

        if (StringUtils.isEmpty(jobId)) {
            showWarning("请输入职业");
            return;
        }
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("acctNo", strBackCard);
        para.addBodyParameter("certLostDate", strEndTime);
        para.addBodyParameter("certNo", strIdCard);
        para.addBodyParameter("certVisaDate", strStartTime);
        para.addBodyParameter("eleAddress", strAddress);
        para.addBodyParameter("eleCustomerName", strName);
        para.addBodyParameter("eleSex", iSex + "");
        para.addBodyParameter("idImgBack", cardPath2);
        para.addBodyParameter("idImgFront", cardPath1);
        para.addBodyParameter("imgBase", headPath);
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("mobilePhone", strTel);
        para.addBodyParameter("passWord", strPassword1);
        para.addBodyParameter("verifyNo", strCode);
        para.addBodyParameter("taxFlag", "1");
        para.addBodyParameter("workType", jobId);


        HttpHelper.post(this, UrlConfig.OPEN_ELE, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                showSuccess("电子钱包开通成功");
                sendObseverMsg("update_user", new JSONObject());
                finish();
            }

            @Override
            public void complete(String str) {

            }
        });


    }

    private void getUserMessage() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId", merUserId);
        HttpHelper.post(this, UrlConfig.WALLET_OPEN_DETAIL, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONObject user = object.getJSONObject("user");
                name.setText(user.getString("customerName"));
                tel.setText(user.getString("mobilePhone"));
                idcard.setText(user.getString("certNo"));
                bank_card.setText(user.getString("acctNo"));
                String supplyInfoUrl = user.getString("supplyInfoUrl");
                if (!StringUtils.isEmpty(supplyInfoUrl)) {
                    try {
                        String url = URLDecoder.decode(supplyInfoUrl, "utf-8");
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        showWarning("请先补录信息");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }
}
