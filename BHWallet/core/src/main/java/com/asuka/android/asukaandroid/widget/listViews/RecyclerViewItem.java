package com.asuka.android.asukaandroid.widget.listViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by egojit on 2017/1/9.
 */

public  interface  RecyclerViewItem{

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public RecyclerView.ViewHolder onCreateViewHolder(View itemView, ViewGroup parent, int viewType);

}
