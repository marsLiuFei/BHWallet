package com.asuka.android.asukaandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.comm.RefreshSender;
import com.asuka.android.asukaandroid.widget.DialogProgress;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;


/**
 * Author:Asuka
 * Time:2016-9-9
 * Mask: 所有Fragment继承这个Fragment
 */
public abstract class AsukaFragment extends Fragment implements Observer , Serializable {

    private boolean injected = false;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        View view = AsukaAndroid.view().inject(this, inflater, container);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        RefreshSender.getInstances().addObserver(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefreshSender.getInstances().deleteObserver(this);
    }

    public void sendObseverMsg(String type, JSONObject object){
        object.put("type",type);
        RefreshSender.getInstances().sendMessage(object.toJSONString());
    }

    protected  abstract void initView(View view);
    protected  abstract void initEvent();
    protected  abstract void oberserMsg(String type, JSONObject object);
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (!injected) {
//            AsukaAndroid.view().inject(this, this.getView());
//        }
    }

    public Context getApplicationContext() {
        return context;
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
        DialogProgress.getInstance().registDialogProgress(getActivity());
    }

    /**
     * 显示进度对话框
     */
    public void dissmisLoging(){
        DialogProgress.getInstance().unRegistDialogProgress();
    }


    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), cls);
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
        intent.setClass(this.getActivity(), cls);
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

    @Override
    public void onResume() {
        super.onResume();
    }
}