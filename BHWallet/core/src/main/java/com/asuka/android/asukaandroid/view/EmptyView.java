package com.asuka.android.asukaandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asuka.android.asukaandroid.R;


/**
 * Created by lugao on 16/5/17.
 */
public class EmptyView extends LinearLayout {

    private ImageView imgView;
    private TextView txtTip;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context){
        //在构造函数中将Xml中定义的布局解析出来。
        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true);
        //imgView=(ImageView)findViewById(R.id.textViewMessage);
        //txtTip=(TextView)findViewById(R.id.textViewMessage);
    }


//    public void setImage(int res){
//        this.txtTip.setImageResource(res);
//    }
//
//    public void setTxtTip(String tip){
//        this.txtTip.setText(tip);
//    }


}
