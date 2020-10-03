package com.android.bhwallet.app.Save.Fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Save.UI.BuyActivity;
import com.android.bhwallet.utils.FullyGridLayoutManager;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.AsukaFragment;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.widget.recycleView.BaseViewHolder;
import com.asuka.android.asukaandroid.widget.recycleView.UITableViewDelegate;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class SaveProductFragment extends AsukaFragment implements UITableViewDelegate {

    private static SaveProductFragment fragment;

    public static SaveProductFragment getInstance() {
        if (fragment == null) {
            fragment = new SaveProductFragment();
        }
        return fragment;
    }

    private com.asuka.android.asukaandroid.widget.recycleView.RecyclerView recyclerView;

    private JSONArray array;
    private String merUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.comm_recycleview, null, false);
        initView(view);
        initEvent();
        return view;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        array = new JSONArray();
        recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(getActivity(), 1));
        recyclerView.setDelegate(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }
        initData();
    }

    private void initData() {
        EGRequestParams para = new EGRequestParams();
        HttpHelper.post((AsukaActivity) getActivity(), UrlConfig.SAVE_PRODUCT_LIST, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray list = object.getJSONArray("list");
                if (list != null) {
                    array.addAll(list);
                }
                recyclerView.setDataSource(list);
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
    public RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_save_product, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindData(BaseViewHolder hoderView, int position) {
        ViewHolder holder = (ViewHolder) hoderView;
        final JSONObject object = (JSONObject) array.get(position);
        holder.name.setText(object.getString("productName"));
        NumberFormat nf = NumberFormat.getPercentInstance();
        try {
            Number number = nf.parse(object.getString("exeRate"));
            holder.rate.setText(number.floatValue() + "%");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String termType = object.getString("termType");
        if ("Y".equalsIgnoreCase(termType)) {
            termType = "年";
        } else if ("M".equalsIgnoreCase(termType)) {
            termType = "个月";
        } else if ("D".equalsIgnoreCase(termType)) {
            termType = "天";
        }
        holder.time.setText(object.getString("term") + termType);
        String memoKind = object.getString("memoKind");
        if (StringUtils.isEmpty(memoKind)) {
            holder.rate_type.setText("指定利率");
        } else {
            switch (memoKind) {
                case "00":
                    holder.rate_type.setText("指定利率");
                    break;
                case "01":
                    holder.rate_type.setText("隔夜利率");
                    break;
                case "02":
                    holder.rate_type.setText("一周利率");
                    break;
                case "03":
                    holder.rate_type.setText("两周利率");
                    break;
                case "04":
                    holder.rate_type.setText("一个月利率");
                    break;
                case "05":
                    holder.rate_type.setText("三个月利率");
                    break;
                case "06":
                    holder.rate_type.setText("六个月利率");
                    break;
                case "07":
                    holder.rate_type.setText("九个月利率");
                    break;
                case "08":
                    holder.rate_type.setText("一年利率");
                    break;
                default:
                    holder.rate_type.setText("指定利率");
                    break;
            }
        }

        holder.least_money.setText(StringUtils.isEmpty(object.getString("amt1")) ? "无限制" : object.getString("amt1"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("merUserId", merUserId);
                bundle.getString("productCode", object.getString("productCode"));
                startActivityForResult(BuyActivity.class, object.getString("productName"), bundle);
            }
        });
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView name;
        private TextView rate;
        private TextView time;
        private TextView rate_type;
        private TextView least_money;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rate = itemView.findViewById(R.id.rate);
            time = itemView.findViewById(R.id.time);
            rate_type = itemView.findViewById(R.id.rate_type);
            least_money = itemView.findViewById(R.id.least_money);
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
