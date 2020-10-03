package com.android.bhwallet.app.Fund.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.AsukaFragment;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.asuka.android.asukaandroid.widget.recycleView.BaseViewHolder;
import com.asuka.android.asukaandroid.widget.recycleView.RecyclerView;
import com.asuka.android.asukaandroid.widget.recycleView.UITableViewDelegate;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.FullyGridLayoutManager;

import javax.microedition.khronos.egl.EGL;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class FundFragment extends AsukaFragment implements UITableViewDelegate {
    private RecyclerView recyclerView;

    private JSONArray array;
    private String merUserId;
    private String eleAcctNo;

    private static FundFragment fragment;
    public static FundFragment getInstance(){
        if (fragment == null){
            fragment = new FundFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_fund,null,false);
        initView(view);
        return view;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        array = new JSONArray();
        recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(getActivity(),1));
        recyclerView.setDelegate(this);

        Bundle bundle = getArguments();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
            eleAcctNo = bundle.getString("eleAcctNo");
        }

        initData();
    }

    private void initData(){
        EGRequestParams para = new EGRequestParams();
        HttpHelper.post((AsukaActivity) getActivity(), UrlConfig.JZ_LIST, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                array = object.getJSONArray("list");
                recyclerView.setDataSource(array);
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    public android.support.v7.widget.RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_fund,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindData(BaseViewHolder hoderView, int position) {
        JSONObject object = (JSONObject) array.get(position);
        ViewHolder holder = (ViewHolder) hoderView;
        holder.incomeSever.setText(object.getString("incomeSever"));
        holder.myriadValue.setText(object.getString("myriadValue"));
        holder.date.setText(object.getString("navDate"));
        holder.name.setText(object.getString("fundName")+"("+object.getString("fundCode")+")");
    }

    private class ViewHolder extends BaseViewHolder{
        TextView incomeSever;
        TextView myriadValue;
        TextView date;
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            incomeSever = itemView.findViewById(R.id.incomeSever);
            myriadValue = itemView.findViewById(R.id.myriadValue);
            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
