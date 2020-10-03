package com.android.bhwallet.app.Save.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.app.Save.UI.SellActivity;
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

import static android.app.Activity.RESULT_OK;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class MyProductFragment extends AsukaFragment implements UITableViewDelegate {
    private static MyProductFragment fragment;

    public static MyProductFragment getInstance() {
        if (fragment == null) {
            fragment = new MyProductFragment();
        }
        return fragment;
    }
    private RecyclerView recyclerView;

    private JSONArray array;
    private String merUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my_product,null,false);
        initView(view);
        initEvent();
        Bundle bundle = getArguments();
        if (bundle != null){
            merUserId = bundle.getString("merUserId");
        }
        initData();
        return view;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        array = new JSONArray();
        recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(getActivity(),1));
        recyclerView.setDelegate(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void initData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("merUserId",merUserId);
        HttpHelper.postNoProcess((AsukaActivity) getActivity(), UrlConfig.MY_PRODUCT, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray list = object.getJSONArray("list");
                if (list != null && list.size()>0){
                    array.clear();
                    array.addAll(list);
                    recyclerView.setDataSource(array);
                }
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    public android.support.v7.widget.RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_my_product,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindData(BaseViewHolder hoderView, int position) {
        final JSONObject object = (JSONObject) array.get(position);
        ViewHolder holder = (ViewHolder) hoderView;
        holder.name.setText(object.getString("ProductName"));
        holder.date.setText(object.getString("SignDate")+"~"+object.getString("IntBeginDate"));
        holder.rate.setText(object.getFloatValue("Rate")+"%");
        holder.money.setText(object.getString("AcctBalance"));
        holder.sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data",object.toJSONString());
                bundle.putString("merUserId",merUserId);
                startActivityForResult(SellActivity.class,object.getString("ProductName"),bundle);
            }
        });
    }

    private class ViewHolder extends BaseViewHolder{
        TextView name;
        TextView money;
        TextView date;
        TextView rate;
        TextView sell;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            money = itemView.findViewById(R.id.money);
            date = itemView.findViewById(R.id.date);
            rate = itemView.findViewById(R.id.rate);
            sell = itemView.findViewById(R.id.sell);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            initData();
        }
    }
}
