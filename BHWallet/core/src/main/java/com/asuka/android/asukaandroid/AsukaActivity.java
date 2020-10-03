package com.asuka.android.asukaandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.comm.RefreshSender;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.asuka.android.asukaandroid.widget.AsukaToolbar;
import com.asuka.android.asukaandroid.widget.DialogProgress;
import com.githang.statusbar.StatusBarCompat;

import java.util.Observable;
import java.util.Observer;

/**
 * Author:Asuka
 * Time:2016-9-9
 * Mask: 所有activity继承这个Activity
 */
public abstract class AsukaActivity extends AppCompatActivity implements Observer {

    private View asukaView;
    private FrameLayout linAsukaBox;
    public AsukaToolbar toolBar;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //AsukaAndroid.app().getAppManager().addActivity(this);
        AsukaAndroid.view().inject(this);//启动注入

        StatusBarCompat.setStatusBarColor(this, getResources().getColor(com.asuka.android.asukaandroid.R.color.colorPrimary));

        toolBar = getToolBar();


        context=this;
        //LogUtil.d("activity创建,并且注入");
        RefreshSender.getInstances().addObserver(this);
        initView();
        initEvent();
    }

    public Context getContext() {
        return context;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefreshSender.getInstances().deleteObserver(this);
        //AsukaAndroid.app().getAppManager().finishActivity(this);
    }

    @Override
    public Resources getResources() {
        //设置字体不随用户设置改变，一直使用默认字体大小
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
        asukaView = LayoutInflater.from(this).inflate(R.layout.activity_asuka, null);
        linAsukaBox = (FrameLayout) asukaView.findViewById(R.id.linAsukaBox);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        linAsukaBox.addView(view);
        super.setContentView(asukaView);

        toolBar= asukaView.findViewById(R.id.asuka_tool_bar);
        setSupportActionBar(toolBar);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String strTitle = b.getString("activityTitle");
            if (!TextUtils.isEmpty(strTitle)) {
                toolBar.setTitle(strTitle);
            }
        }
    }

    public void setView(@LayoutRes int layoutResID){
        setContentView(layoutResID);
    }


    public AsukaToolbar getToolBar(){
        return toolBar;
    }
    public void setMenu(@MenuRes int menu){
        toolBar.inflateMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {

        toolBar.setTitle(title);
    }
    public void addCustomView(int resoursId){
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mUserView = mInflater.inflate(resoursId, null);
        toolBar.addCustomView(mUserView);
    }

    @Override
    public void setTitle(int titleId) {
        toolBar.setTitle(titleId);
    }

    public void showSuccess(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 20, 20);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(com.asuka.android.asukaandroid.R.drawable.ico_success);
        toastView.addView(imageCodeProject, 0);
        toastView.setPadding(100,85,100,85);

        toast.show();
//        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }



    public void showWarning(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 20, 20);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(com.asuka.android.asukaandroid.R.drawable.ico_warning);
        toastView.addView(imageCodeProject, 0);
        toastView.setPadding(100,85,100,85);
        toast.show();
//        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示进度对话框
     */
    public void showLoging(){
        DialogProgress.getInstance().registDialogProgress(this);
    }

    /**
     * 显示进度对话框
     */
    public void dissmisLoging(){
        DialogProgress.getInstance().unRegistDialogProgress();
    }

    /**
     * 发消息
     * @param type
     * @param object
     */
    public void sendObseverMsg(String type,JSONObject object){
        object.put("type",type);
        RefreshSender.getInstances().sendMessage(object.toJSONString());
    }

    protected  abstract void initView();
    protected  abstract void initEvent();
    protected  abstract void oberserMsg(String type, JSONObject object);
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if(bundle != null) {
            intent.putExtras(bundle);
        }
        this.startActivity(intent);
    }

    public void startActivity(Class<?> cls, String title) {
        Bundle b = new Bundle();
        b.putString("activityTitle", title);
        this.startActivity(cls, b);
    }

    public void startActivity(Class<?> cls, String title, Bundle bundle) {
        if(bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString("activityTitle", title);
        this.startActivity(cls, bundle);
    }

    protected void startActivityForResult(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        this.startActivityForResult(intent, 1);
    }

    public void startActivityForResult(Class<?> cls, String title, Bundle bundle) {
        if(bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString("activityTitle", title);
        this.startActivityForResult(cls, bundle);
    }

    protected void startActivity(String action) {
        this.startActivity((String)action, (Bundle)null);
    }

    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        this.startActivity(intent);
    }

    @Override
    public void update(Observable observable, Object data) {
        try {
            JSONObject object= JSON.parseObject(data.toString());
            String type=object.getString("type");
            oberserMsg(type,object);
        }catch (Exception e){

        }
    }


    /**
     * 隐藏键盘
     * 弹窗弹出的时候把键盘隐藏掉
     */
    protected void hideInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    /**
     * 弹起键盘
     */
    protected void showInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

}

