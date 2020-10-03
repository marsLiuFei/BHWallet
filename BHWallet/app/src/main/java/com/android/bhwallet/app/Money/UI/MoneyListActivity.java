package com.android.bhwallet.app.Money.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.TimerHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.asuka.android.asukaandroid.widget.listViews.BaseRecyclerAdapter;
import com.asuka.android.asukaandroid.widget.listViews.CommonHolder;
import com.asuka.android.asukaandroid.widget.listViews.RefreshCallBack;
import com.asuka.android.asukaandroid.widget.listViews.tkrefreshlayout.AsukaPullRecyclerView;
import com.asuka.android.asukaandroid.widget.recycleView.BaseViewHolder;
import com.asuka.android.asukaandroid.widget.recycleView.RecyclerView;
import com.asuka.android.asukaandroid.widget.recycleView.UITableViewDelegate;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Money.Interface.OnSearchClickListener;
import com.android.bhwallet.app.Money.Popwindow.SearchPopwindow;
import com.android.bhwallet.utils.FullyGridLayoutManager;

import java.util.Calendar;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/29
 * 邮箱：825814902@qq.com
 * 描述：账单
 */
public class MoneyListActivity extends AsukaActivity implements UITableViewDelegate {
    private AsukaPullRecyclerView recyclerView;
    private TextView tv_search;
    private LinearLayout ll_search;
    private TextView search;
    private LinearLayout ll_screen;

    private SearchPopwindow popwindow;

    private String merUserId;

    private JSONArray array;
    private int pageNum;
    private String beginTime;
    private String endTime;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_money_list);
        recyclerView = findViewById(R.id.recyclerView);
        tv_search = findViewById(R.id.tv_search);
        ll_search = findViewById(R.id.ll_search);
        search = findViewById(R.id.search);
        ll_screen = findViewById(R.id.ll_screen);


        array = new JSONArray();
        recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
        }
        pageNum = 1;
        Calendar calendar = Calendar.getInstance();
        beginTime = calendar.get(Calendar.YEAR) + "-01-01";
        endTime = TimerHelper.getFromatDate("yyyy-MM-dd", System.currentTimeMillis());
        initData();
    }

    private void initData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("beginDate", beginTime);
        para.addBodyParameter("endDate", endTime);
        para.addBodyParameter("merUserId", merUserId);
        para.addBodyParameter("pageNum", pageNum + "");
        HttpHelper.post(this, UrlConfig.BILL_LIST, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                JSONArray jsonArray = object.getJSONArray("billList");
                if (jsonArray.size() > 0) {
                    pageNum++;
                    array.addAll(jsonArray);
                }
                recyclerView.setList(array);
            }

            @Override
            public void complete(String str) {
                recyclerView.endRefresh();
            }
        });
    }

    @Override
    protected void initEvent() {
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popwindow == null) {
                    popwindow = new SearchPopwindow(MoneyListActivity.this, new OnSearchClickListener() {
                        @Override
                        public void onSearch(String content) {
                            search.setText("搜索");
                            tv_search.setText(content);
                        }
                    });
                }

                if (popwindow.isShowing()) {
                    popwindow.dismiss();
                    search.setText("搜索");
                } else {
                    hideInputKeyboard(tv_search);
                    popwindow.showAsDropDown(ll_search);
                    search.setText("取消");
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("取消".equals(search.getText())) {
                    popwindow.dismiss();
                    search.setText("搜索");
                } else {
                    //调取搜索接口
                }
            }
        });


        ll_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(DateSelectActivity.class, "账单", null);
            }
        });

        recyclerView.setCallBack(new RefreshCallBack() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                array.clear();
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private BaseRecyclerAdapter adapter = new BaseRecyclerAdapter() {
        @Override
        public CommonHolder setViewHolder(ViewGroup parent) {
            return new CardHolder(parent.getContext(), parent);
        }

        class CardHolder extends CommonHolder<JSONObject> {
            private TextView name;
            private TextView money;
            private TextView date;

            public CardHolder(Context context, ViewGroup root) {
                super(context, root, R.layout.item_money);
                name = itemView.findViewById(R.id.name);
                money = itemView.findViewById(R.id.money);
                date = itemView.findViewById(R.id.date);

            }

            @Override
            public void bindData(final JSONObject jsonObject) {
                name.setText(jsonObject.getString("oppAcctName"));
                money.setText("C".equals(jsonObject.getString("dorc")) ? jsonObject.getString("cebitAmount") : jsonObject.getString("debitAmount"));
                date.setText(jsonObject.getString("acctTransTime"));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("data", jsonObject.toJSONString());
                        startActivity(MoneyDetailctivity.class, "账单", bundle);
                    }
                });
            }
        }
    };


    @Override
    public android.support.v7.widget.RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_money_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindData(BaseViewHolder hoderView, int position) {
        ViewHolder holder = (ViewHolder) hoderView;
        holder.recyclerView.getRecyclerView().setLayoutManager(new FullyGridLayoutManager(MoneyListActivity.this, 1));
        holder.recyclerView.setDelegate(new UITableViewDelegate() {
            @Override
            public android.support.v7.widget.RecyclerView.ViewHolder getItemViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(MoneyListActivity.this).inflate(R.layout.item_money, null);
                return new DetailViewHolder(view);
            }

            @Override
            public void onBindData(BaseViewHolder hoderView, int position) {
                DetailViewHolder holder1 = (DetailViewHolder) hoderView;
                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(MoneyDetailctivity.class, "账单");
                    }
                });
            }
        });
        holder.recyclerView.setDataSource(array);
    }


    private class ViewHolder extends BaseViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    private class DetailViewHolder extends BaseViewHolder {

        public DetailViewHolder(View itemView) {
            super(itemView);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                String type = bundle.getString("type");
                int year = bundle.getInt("year");
                int month = bundle.getInt("month");
                if ("month".equals(type)) {
                    beginTime = year + "-" + (month > 9 ? month : "0" + month) + "-01";
                    endTime = getDayByDate(year, month);
                } else if ("day".equals(type)) {
                    int day = bundle.getInt("day");
                    beginTime = year + "-" + (month > 9 ? month : "0" + month) + "-" + day;
                    endTime = year + "-" + (month > 9 ? month : "0" + month) + "-" + day;
                }
                pageNum = 1;
                array.clear();
                initData();
            }
        }
    }

    private String getDayByDate(int year, int month) {
        String time = "";
        if (month == 2) {
            //判断闰年
            if ((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) {
                time = year + "-" + (month > 9 ? month : "0" + month) + "-29";
            } else {
                time = year + "-" + (month > 9 ? month : "0" + month) + "-28";
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            time = year + "-" + (month > 9 ? month : "0" + month) + "-30";

        } else {
            time = year + "-" + (month > 9 ? month : "0" + month) + "-31";
        }
        return time;
    }
}
