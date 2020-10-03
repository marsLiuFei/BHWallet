package com.android.bhwallet.app.Money.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaFragment;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.Event;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.android.bhwallet.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Date;

import jsc.kit.wheel.base.WheelItemView;
import jsc.kit.wheel.base.WheelView;
import jsc.kit.wheel.dialog.DateItem;

/**
 * @author 蒋冬冬
 * 创建日期：2020/7/30
 * 邮箱：825814902@qq.com
 * 描述：
 */

public class MonthScreenFragment extends AsukaFragment {
    private static MonthScreenFragment fragment;

    public static MonthScreenFragment getInstance() {
        if (fragment == null) {
            fragment = new MonthScreenFragment();
        }
        return fragment;
    }

    private LinearLayout lyPickerContainer;
    private Button commit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_month_screen, null, false);
        initView(view);
        initEvent();
        return view;
    }

    private final String TAG = "DateTimeWheelDialog";
    private final int MIN_MONTH = 1;
    private final int MAX_MONTH = 12;
    private final int MIN_DAY = 1;
    private final int MIN_HOUR = 0;
    private final int MAX_HOUR = 23;
    private final int MIN_MINUTE = 0;
    private final int MAX_MINUTE = 59;
    private TextView tvTitle;
    private TextView tvCancel;
    private TextView tvOK;
    private CharSequence clickTipsWhenIsScrolling = "Scrolling, wait a minute.";

    private WheelItemView yearWheelItemView;
    private WheelItemView monthWheelItemView;


    private DateItem[] yearItems;
    private DateItem[] monthItems;
    private DateItem[] dayItems;


    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    private Calendar selectedCalendar = Calendar.getInstance();

    private int showCount = 5;
    private int itemVerticalSpace = 32;
    private boolean isViewInitialized = false;
    private boolean keepLastSelected = false;

    @Override
    protected void initView(View view) {
        lyPickerContainer = view.findViewById(R.id.wheel_id_picker_container);
        commit = view.findViewById(R.id.commit);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date startDate = calendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        Date endDate = endCalendar.getTime();

        isViewInitialized = true;
        //year
        yearWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        yearWheelItemView.setItemVerticalSpace(itemVerticalSpace);
        yearWheelItemView.setShowCount(showCount);
        lyPickerContainer.addView(yearWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        //month
        monthWheelItemView = new WheelItemView(lyPickerContainer.getContext());
        monthWheelItemView.setItemVerticalSpace(itemVerticalSpace);
        monthWheelItemView.setShowCount(showCount);
        lyPickerContainer.addView(monthWheelItemView, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        configShowUI();
        setDateArea(startDate, endDate, true);
        updateSelectedDate(endDate);
    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String backTime = selectedCalendar.get(Calendar.YEAR) + "-" + (selectedCalendar.get(Calendar.MONTH) + 1);
                Bundle bundle = new Bundle();
                bundle.putString("type", "month");
                bundle.putInt("year", selectedCalendar.get(Calendar.YEAR));
                bundle.putInt("month", selectedCalendar.get(Calendar.MONTH) + 1);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    public void configShowUI() {
        configShowUI(-1);
    }

    public void configShowUI(int totalOffsetX) {
        ensureIsViewInitialized();
        if (totalOffsetX == -1) {
            totalOffsetX = getContext().getResources().getDimensionPixelSize(jsc.kit.wheel.R.dimen.wheel_picker_total_offset_x);
        }
        yearWheelItemView.setTotalOffsetX(0);
        monthWheelItemView.setTotalOffsetX(0);
        yearWheelItemView.setVisibility(View.VISIBLE);
        monthWheelItemView.setVisibility(View.VISIBLE);
        yearWheelItemView.setTotalOffsetX(totalOffsetX);

    }

    public void setDateArea(@NonNull Date startDate, @NonNull Date endDate, boolean keepLastSelected) {
        ensureIsViewInitialized();
        if (startDate.after(endDate))
            throw new IllegalArgumentException("start date should be before end date");
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        selectedCalendar.setTimeInMillis(endDate.getTime());
        this.keepLastSelected = keepLastSelected;
        initAreaDate();
    }

    public void updateSelectedDate(@NonNull Date selectedDate) {
        ensureIsViewInitialized();
//        if (selectedDate.before(startCalendar.getTime()) || selectedDate.after(endCalendar.getTime()))
//            throw new IllegalArgumentException("selected date must be between start date and end date");
        selectedCalendar.setTime(selectedDate);
        initSelectedDate();
        initOnScrollListener();
    }

    private void initAreaDate() {
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startCalendar.get(Calendar.MINUTE);

        yearItems = updateItems(DateItem.TYPE_YEAR, startYear, endYear);
        monthItems = updateItems(DateItem.TYPE_MONTH, startMonth, MAX_MONTH);
        int dayActualMaximum = startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayItems = updateItems(DateItem.TYPE_DAY, startDay, dayActualMaximum);

        yearWheelItemView.setItems(yearItems);
        monthWheelItemView.setItems(monthItems);

    }

    private void initOnScrollListener() {
        yearWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.YEAR, yearItems[selectedIndex].getValue());
                onYearChanged();
            }
        });
        monthWheelItemView.setOnSelectedListener(new WheelView.OnSelectedListener() {
            @Override
            public void onSelected(Context context, int selectedIndex) {
                selectedCalendar.set(Calendar.MONTH, monthItems[selectedIndex].getValue() - 1);
                onMonthChanged();
            }
        });

    }

    private void initSelectedDate() {
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        yearWheelItemView.setSelectedIndex(yearItems.length - 1);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear)) {
            startValue = startMonth;
            endValue = MAX_MONTH;
        } else if (isSameValue(selectedYear, endYear)) {
            startValue = MIN_MONTH;
            endValue = endMonth;
        } else {
            startValue = MIN_MONTH;
            endValue = MAX_MONTH;
        }
        monthItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            monthItems[tempIndex] = new DateItem(DateItem.TYPE_MONTH, i);
            if (isSameValue(selectedMonth, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        monthWheelItemView.setItems(monthItems);
        monthWheelItemView.setSelectedIndex(newSelectedIndex);
    }

    private void onYearChanged() {
        //update month list
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear)) {
            startValue = startMonth;
            endValue = MAX_MONTH;
        } else if (isSameValue(selectedYear, endYear)) {
            startValue = MIN_MONTH;
            endValue = endMonth;
        } else {
            startValue = MIN_MONTH;
            endValue = MAX_MONTH;
        }
        monthItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            monthItems[tempIndex] = new DateItem(DateItem.TYPE_MONTH, i);
            if (isSameValue(selectedMonth, i)) {
                lastSelectedIndex = tempIndex;
            }
        }
        int newSelectedIndex = keepLastSelected ? (lastSelectedIndex == -1 ? 0 : lastSelectedIndex) : 0;
        monthWheelItemView.setItems(monthItems);
        monthWheelItemView.setSelectedIndex(newSelectedIndex);
    }

    private void onMonthChanged() {
        //update day list
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        int selectedYear = selectedCalendar.get(Calendar.YEAR);
        int startMonth = startCalendar.get(Calendar.MONTH) + 1;
        int endMonth = endCalendar.get(Calendar.MONTH) + 1;
        int selectedMonth = selectedCalendar.get(Calendar.MONTH) + 1;
        int startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        int endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        int selectedDay = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        int tempIndex = -1;
        int lastSelectedIndex = -1;
        int startValue, endValue;
        if (isSameValue(selectedYear, startYear) && isSameValue(selectedMonth, startMonth)) {
            startValue = startDay;
            endValue = selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        } else if (isSameValue(selectedYear, endYear) && isSameValue(selectedMonth, endMonth)) {
            startValue = MIN_DAY;
            endValue = endDay;
        } else {
            startValue = MIN_DAY;
            endValue = selectedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        dayItems = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            tempIndex++;
            dayItems[tempIndex] = new DateItem(DateItem.TYPE_DAY, i);
            if (isSameValue(selectedDay, i)) {
                lastSelectedIndex = tempIndex;
            }
        }

    }

    private boolean isScrolling() {
        return yearWheelItemView.isScrolling()
                || monthWheelItemView.isScrolling();

    }

    private boolean isSameValue(int value1, int value2) {
        return value1 == value2;
    }

    private void ensureIsViewInitialized() {
        if (!isViewInitialized)
            throw new IllegalStateException("View wasn't initialized, call show() first.");
    }

    private DateItem[] updateItems(@DateItem.DateType int type, int startValue, int endValue) {
        int index = -1;
        DateItem[] items = new DateItem[endValue - startValue + 1];
        for (int i = startValue; i <= endValue; i++) {
            index++;
            items[index] = new DateItem(type, i);
        }
        return items;
    }


}
