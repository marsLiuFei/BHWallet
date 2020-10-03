package com.asuka.android.asukaandroid.widget.listViews.tkrefreshlayout;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.R;
import com.asuka.android.asukaandroid.view.EmptyView;
import com.asuka.android.asukaandroid.widget.listViews.BaseRecyclerAdapter;
import com.asuka.android.asukaandroid.widget.listViews.CommonHolder;
import com.asuka.android.asukaandroid.widget.listViews.RefreshCallBack;
import com.asuka.android.asukaandroid.widget.listViews.tkrefreshlayout.header.bezierlayout.BezierLayout;
import com.asuka.android.asukaandroid.widget.listViews.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.List;

/**
 * Created by egojit on 2017/1/9.
 */

public class AsukaPullRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;
    TwinklingRefreshLayout refreshLayout;
    private BaseRecyclerAdapter adapter;

    private RefreshCallBack callBack;

    private EmptyView emptyView;

    private  byte refreshOrLoadmore=0;//0下拉刷新1上拉加载更多

    public void startRefresh(){
        refreshLayout.startRefresh();
    }

    public void endRefresh(){
        if (refreshOrLoadmore==0){
            refreshLayout.finishRefreshing();
        }else {
            refreshLayout.finishLoadmore();
        }
    }

    public void setCallBack(RefreshCallBack callBack) {
        this.callBack = callBack;
    }

    public AsukaPullRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public AsukaPullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AsukaPullRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private  void init(Context context){

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.egojit_refresh_recyclerview, this);
        refreshLayout=(TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        emptyView = (EmptyView) findViewById(R.id.emptyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
////        photoAdapter = new PhotoAdapter();
////        recyclerView.setAdapter(photoAdapter);
////        ProgressLayout headerView = new ProgressLayout(this);
//        BezierLayout headerView = new BezierLayout(getContext());
//        refreshLayout.setHeaderView(headerView);
        refreshLayout.setWaveHeight(140);
//        refreshLayout.setFloatRefresh(true);
//        refreshLayout.setPureScrollModeOn(true);
        refreshLayout.setOverScrollBottomShow(false);
//        refreshLayout.setAutoLoadMore(true);

//        addHeader();


        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshOrLoadmore=0;
                callBack.onRefresh();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        refreshLayout.finishRefreshing();
//                    }
//                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                refreshOrLoadmore=1;
                callBack.onLoadMore();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        refreshLayout.finishLoadmore();
//                    }
//                }, 2000);
            }
        });














//        refreshLayout=new TwinklingRefreshLayout(context);
//        refreshLayout.setWaveHeight(180);
//        refreshLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        ProgressLayout headerView = new ProgressLayout(context);
//        refreshLayout.setHeaderView(headerView);
//        recyclerView=new RecyclerView(context);
//        recyclerView.setLayoutParams(new TwinklingRefreshLayout.LayoutParams(LayoutParams.MATCH_PARENT, TwinklingRefreshLayout.LayoutParams.MATCH_PARENT));
//        recyclerView.setAdapter(this.adapter);
//        this.addView(refreshLayout);
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
//            @Override
//            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishRefreshing();
//                    }
//                },2000);
////                refreshOrLoadmore=0;//刷新
////                callBack.onRefresh();
//
//            }
//
//            @Override
//            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.finishLoadmore();
//                    }
//                },2000);
////                refreshOrLoadmore=1;//加载更多
////                callBack.onLoadMore();
//
//
//            }
//        });
    }

    /**
     * 隐藏空视图
     */
    public void hideEmptyView() {
        emptyView.setVisibility(GONE);
    }

    /**
     * 显示空视图
     */
    public void showEmptyView() {
        emptyView.setVisibility(VISIBLE);
    }



    public BaseRecyclerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(this.adapter);
    }

    public  void setList(List<?> data){
        if (data != null && data.size() > 0) {
            hideEmptyView();
            recyclerView.setVisibility(VISIBLE);

        } else {
            showEmptyView();
            recyclerView.setVisibility(GONE);

        }
        this.adapter.setDataList(data);

    }











    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public TwinklingRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setRefreshLayout(TwinklingRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }



}
