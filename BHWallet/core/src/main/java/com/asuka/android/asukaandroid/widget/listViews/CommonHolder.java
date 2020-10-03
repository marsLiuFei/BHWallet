package com.asuka.android.asukaandroid.widget.listViews;

/**
 * Created by egojit on 2017/1/11.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonHolder<T> extends RecyclerView.ViewHolder {
    public CommonHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
//        ButterKnife.bind(this, itemView);
    }
    /**
     * 此适配器是为了能让详情页共用列表页的ViewHolder，一般情况无需重写该构造器
     */
    public CommonHolder(View itemView) {
        super(itemView);
//        ButterKnife.bind(this, itemView);
    }
    public Context getContext() {
        return itemView.getContext();
    }
    /**
     * 用给定的 data 对 holder 的 view 进行赋值
     */
    public abstract void bindData(T t);

    public void bindHeadData(){}
    /**
     * 通知适配器更新布局
     */
    public interface OnNotifyChangeListener {
        void onNotify();
    }
    OnNotifyChangeListener listener;
    public void setOnNotifyChangeListener(OnNotifyChangeListener listener) {
        this.listener = listener;
    }
    public void notifyChange() {
        listener.onNotify();
    }
}