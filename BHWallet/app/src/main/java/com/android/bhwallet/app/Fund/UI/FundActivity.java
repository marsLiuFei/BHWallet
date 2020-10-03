package com.android.bhwallet.app.Fund.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.bhwallet.app.Card.PayPasswordActivity;
import com.android.bhwallet.utils.HttpHelper;
import com.android.bhwallet.utils.UrlConfig;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.http.EGRequestParams;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.flyco.tablayout.SlidingTabLayout;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Fund.Fragment.FundFragment;
import com.android.bhwallet.app.Fund.Fragment.ProfitFragment;
import com.android.bhwallet.app.Fund.Popwindow.SetOnClickListener;
import com.android.bhwallet.app.Fund.Popwindow.UnbindPopwindow;
import com.android.bhwallet.dialog.CustomAlertDialog;
import com.android.bhwallet.dialog.OnNewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/26
 * 邮箱：825814902@qq.com
 * 描述：
 */
public class FundActivity extends AsukaActivity {
    private SlidingTabLayout tabLayout;
    private ViewPager pager;

    private TextView all_income;
    private TextView jz;

    private final String[] mTitles = {"收益明细", "净值"};
    private List<Fragment> mFragments = new ArrayList<>();

    private UnbindPopwindow popwindow;

    private String merUserId;
    private String eleAcctNo;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_fund);
        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);
        all_income = findViewById(R.id.all_income);
        jz = findViewById(R.id.jz);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            merUserId = bundle.getString("merUserId");
            eleAcctNo = bundle.getString("eleAcctNo");
        }

        mFragments.add(ProfitFragment.getInstance());
        mFragments.add(FundFragment.getInstance());
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(mFragments.size());
        tabLayout.setViewPager(pager);


        getData();

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragments.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("merUserId", merUserId);
            bundle.putString("eleAcctNo", eleAcctNo);
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    private void getData() {
        EGRequestParams para = new EGRequestParams();
        para.addBodyParameter("virlAcctNo", eleAcctNo);
        HttpHelper.postNoProcess(this, UrlConfig.FUND_INCOME, para, new HttpHelper.Ok() {
            @Override
            public void success(String str) {
                JSONObject object = JSON.parseObject(str);
                all_income.setText(object.getString("sumTransAmount"));
                jz.setText(object.getString("sumInterestAmt"));
            }

            @Override
            public void complete(String str) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unbind, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.tb_unbind) {
            if (popwindow == null) {
                popwindow = new UnbindPopwindow(this, new SetOnClickListener() {
                    @Override
                    public void onClick() {
                        showUnbindDialog();
                    }
                });
            }
            if (popwindow.isShowing()) {
                popwindow.dismiss();
            } else {
                popwindow.showAsDropDown(findViewById(R.id.emptyView), 0, 0);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUnbindDialog() {
        CustomAlertDialog dialog = new CustomAlertDialog(this);
        dialog.setTitle("系统提示");
        dialog.setLeftButton("取消");
        dialog.setRightButton("确定");
        TextView textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setText("确定解除添金宝");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.addContentView(textView);
        dialog.setOnNewClickListener(new OnNewClickListener() {
            @Override
            public void onLeftClick() {
                //取消
            }

            @Override
            public void onRightClick() {
                //解约
                unBindFund();

            }
        });
        dialog.show();
    }

    private void unBindFund(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", PayPasswordActivity.CLOSE_FUND);
        bundle.putString("merUserId",merUserId);
        startActivityForResult(PayPasswordActivity.class,"验证密码",bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            finish();
    }
}
