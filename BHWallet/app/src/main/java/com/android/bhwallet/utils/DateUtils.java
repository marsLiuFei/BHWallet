package com.android.bhwallet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/1.
 */
public class DateUtils {
    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date StrToDate1(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            date = StrToDate(str);
            e.printStackTrace();
        }
        return date;
    }

    public static String ParseLongDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }

    public static String ParseDate(long date) {
        String date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date * 1000));
        return date1;
    }

    public static String ParseLongDate2(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date mdate = new Date(date* 1000);
        String tim = sdf.format(mdate);
        return tim;
    }

    public static String ParseLongDate3(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }

    public static String ParseLongDate4(long date) {
        if (date==0){
            return "";
        }else {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date mdate = new Date(date);
            String tim = sdf.format(mdate);
            return tim;
        }

    }

    public static String ParseLongDate5(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }

    public static String ParseLongDate6(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }
    public static String ParseLongDate7(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date mdate = new Date(date);
        String tim = sdf.format(mdate);
        return tim;
    }
    public static String ParseLongDate8(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm:ss");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }
    public static String ParseLongDate9(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }
    public static String ParseLongDate10(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Date mdate = new Date(date * 1000);
        String tim = sdf.format(mdate);
        return tim;
    }


    //得到当前某月的第几周
    public static String getWeekOFMouth(long date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月");
        Date mdate1 = new Date(date);
        String tim1 = sdf1.format(mdate1);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date mdate = new Date(date);
        String tim = sdf.format(mdate);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(mdate);
        int weekOfMouth = calendar.get(Calendar.WEEK_OF_MONTH);
        return tim1 + "第" + weekOfMouth + "周";
    }

    public static String getStringDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//设置日期格式
        return df.format(date);// new Date()为获取当前系统时间
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    public static int getCurrDay(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date mdate = new Date(date);
        String tim = sdf.format(mdate);
        return Integer.parseInt(tim);
    }


    public static int dayForWeek(Long pTime)  {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

}
