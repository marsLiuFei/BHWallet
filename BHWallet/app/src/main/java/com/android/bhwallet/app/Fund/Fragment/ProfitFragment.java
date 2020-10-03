package com.android.bhwallet.app.Fund.Fragment;

import android.content.Context;
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
import com.asuka.android.asukaandroid.widget.listViews.BaseRecyclerAdapter;
import com.asuka.android.asukaandroid.widget.listViews.CommonHolder;
import com.asuka.android.asukaandroid.widget.listViews.RefreshCallBack;
import com.asuka.android.asukaandroid.widget.listViews.tkrefreshlayout.AsukaPullRecyclerView;
import com.asuka.android.asukaandroid.widget.recycleView.BaseViewHolder;
import com.asuka.android.asukaandroid.widget.recycleView.RecyclerView;
import com.asuka.android.asukaandroid.widget.recycleView.UITableViewDelegate;
import com.android.bhwallet.R;
import com.android.bhwallet.utils.FullyGridLayoutManager;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class ProfitFragment extends AsukaFragment {

    private AsukaPullRecyclerView recyclerView;

    private JSONArray array;
    private String merUserId;
    private String eleAcctNo;
    private int pageNo;

    private static ProfitFragment fragment;

    public static ProfitFragment getInstance() {
        if (fragment == null) {
            fragment = new ProfitFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.comm_refresh_recycleview, null, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    protected void initView(View view) {
        pageNo = 1;
        recyclerView = view.findViewById(R.id.recyclerView);
        array = new JSONArray();
        recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(adapter);
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            eleAcctNo = bundle.getString("eleAcctNo");
        }

        getData();
    }

    @Override
    protected void initEvent() {

        recyclerView.setCallBack(new RefreshCallBack() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                array.clear();
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private void getData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("virlAcctNo", eleAcctNo);
        para.addBodyParameter("pageNo", pageNo + "");
        HttpHelper.postNoProcess((AsukaActivity) getActivity(), UrlConfig.INCOME_LIST, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray jsonArray = object.getJSONArray("list");
                if (jsonArray != null && jsonArray.size() > 0) {
                    array.addAll(jsonArray);
                    pageNo++;
                    recyclerView.setList(array);
                }
            }

            @Override
            public void complete(String str) {
                recyclerView.endRefresh();
            }
        });
    }

    private BaseRecyclerAdapter adapter = new BaseRecyclerAdapter() {
        @Override
        public CommonHolder setViewHolder(ViewGroup parent) {
            return new CardHolder(parent.getContext(), parent);
        }

        class CardHolder extends CommonHolder<JSONObject> {
            private TextView add_money;
            private TextView date;

            public CardHolder(Context context, ViewGroup root) {
                super(context, root, R.layout.item_profit);
                add_money = itemView.findViewById(R.id.add_money);
                date = itemView.findViewById(R.id.date);
            }

            @Override
            public void bindData(JSONObject jsonObject) {
                add_money.setText("+" + jsonObject.getString("Income"));
                date.setText(jsonObject.getString("IncomDdate"));
            }
        }
    };
}
