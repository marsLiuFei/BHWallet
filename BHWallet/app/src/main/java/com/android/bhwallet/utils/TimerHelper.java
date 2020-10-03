package com.android.bhwallet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/13.
 */

public class TimerHelper {
    /**
     * @param time 2015-8-19
     * @return
     * @author Donny
     */
    public static String getFromatDate(String form, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(form);
        String date = sdf.format(new Date(time));
        return date;
    }

    public static String GetGoodTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
        Date curentDate = new Date();
        Date myDate = new Date(time);
        long subTime = (curentDate.getTime() - myDate.getTime()) / 1000;
        subTime = subTime / 60;
        String ti = df.format(myDate);
        if (subTime < 10) {
            ti = "刚刚";
        } else if (subTime < 60) {
            ti = subTime + "分钟前";
        } else if (subTime < 60 * 24) {
            ti = (subTime / 60) + "小时前";
        }

        return ti;

    }
    //根据时间戳计算显示不同的日期时间
    public static String GetGoodTime2(long time) {
        String ti;
        Date curentDate = new Date();
        Date myDate = new Date(time);
        SimpleDateFormat dfs = new SimpleDateFormat("dd");
        String nowday=dfs.format(curentDate);
        String lastday=dfs.format(myDate);
        long subTime = (curentDate.getTime() - myDate.getTime())/1000;
        if (subTime<60){
            ti="刚刚";
        }else if (subTime<60*60){
            ti=(subTime/60)+"分钟前";
        }else if (subTime <60 * 60*24&&Integer.parseInt(nowday)==Integer.parseInt(lastday)){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            ti = "今天" + df.format(myDate);
        }
        else if (subTime <60 * 60 * 24*2&&Integer.parseInt(nowday)!=Integer.parseInt(lastday)) {
            if (Integer.parseInt(nowday)-Integer.parseInt(lastday)==1||Integer.parseInt(lastday)-Integer.parseInt(nowday)>10||Integer.parseInt(nowday)==1){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                ti = "昨天" + df.format(myDate);
            }else {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
                ti=df.format(myDate);
            }
        }else if (subTime<60*60*24*365){
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            ti=df.format(myDate);
        }else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            ti = df.format(myDate);
        }
        return ti;

    }
    //根据月份计算显示不同的日期时间
    public static String GetGoodTime3(long time) {
        String ti="";
        Date curentDate = new Date();
        Date myDate = new Date(time);
        SimpleDateFormat dfs2 = new SimpleDateFormat("dd");
        int nowDay=Integer.parseInt(dfs2.format(curentDate));
        int lastDay=Integer.parseInt(dfs2.format(myDate));
        SimpleDateFormat dfs = new SimpleDateFormat("MM");
        String nowMonth=dfs.format(curentDate);
        String lastMonth=dfs.format(myDate);
        SimpleDateFormat dfs1 = new SimpleDateFormat("yyyy");
        String nowYear=dfs1.format(curentDate);
        String lastYear=dfs1.format(myDate);
        Calendar calendar=Calendar.getInstance();
        int nowday=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//获取某一个月的左后一天
        int zoomNowMonth=(Integer.parseInt(nowYear))*12+Integer.parseInt(nowMonth);
        int zoomLastMonth=(Integer.parseInt(lastYear))*12+Integer.parseInt(lastMonth);
        long subTime = (curentDate.getTime() - myDate.getTime())/1000;
        if (zoomNowMonth-zoomLastMonth==0){
            if (nowDay-lastDay>=2){
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
                ti=df.format(myDate);
            }else if (nowDay-lastDay==1){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                ti = "昨天" + df.format(myDate);
            }else if (nowDay==lastDay){
                if (subTime>=60*60){
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                    ti = "今天" + df.format(myDate);
                }else if (subTime>=60){
                    ti=(subTime/60)+"分钟前";
                }else {
                    ti="刚刚";
                }

            }
        } else if (zoomNowMonth-zoomLastMonth==1){
            if (nowday==1&&lastDay==nowday){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                ti = "昨天" + df.format(myDate);
            }else {
                SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
                ti=df.format(myDate);
            }
        } else if (zoomNowMonth-zoomLastMonth>1&&subTime<60*60*24*365) {
            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
            ti=df.format(myDate);

        }else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            ti = df.format(myDate);
        }
        return ti;


    }

    public static long getCurentDate() {
        Date date = new Date();


        return date.getTime() / 1000;
    }

    public static String getInformDate(long time) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String min = sdf.format(new Date(time * 1000));
        long hour = time / 3600 / 24;
        int day = (int) hour % 7;
        String week = "";
        switch (day) {
            case 0:
                week = "星期五";
                break;
            case 1:
                week = "星期六";
                break;
            case 2:
                week = "星期日";
                break;
            case 3:
                week = "星期一";
                break;
            case 4:
                week = "星期二";
                break;
            case 5:
                week = "星期三";
                break;
            case 6:
                week = "星期四";
                break;
            default:
                break;
        }
        date = week + " " + min;
        return date;

    }


    /**
     * <pre>
     * 判断date和当前日期是否在同一周内
     * 注:
     * Calendar类提供了一个获取日期在所属年份中是第几周的方法，对于上一年末的某一天
     * 和新年初的某一天在同一周内也一样可以处理，例如2012-12-31和2013-01-01虽然在
     * 不同的年份中，但是使用此方法依然判断二者属于同一周内
     * </pre>
     *
     * @param date
     * @return
     */
    public static boolean isSameWeekWithToday(Date date) {

        if (date == null) {
            return false;
        }

        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);

        // 1.比较当前日期在年份中的周数是否相同
        if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getInformDate2(long time) {

        String ti;
        Date curentDate = new Date();
        Date myDate = new Date(time);
        SimpleDateFormat dfs = new SimpleDateFormat("dd");
        String nowday=dfs.format(curentDate);
        String lastday=dfs.format(myDate);
        long subTime = (curentDate.getTime() - myDate.getTime())/1000;
         if (subTime <60 * 60*24&&Integer.parseInt(nowday)==Integer.parseInt(lastday)){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            ti = df.format(myDate);
        }else {
             if (isSameWeekWithToday(myDate)){
                 ti= getInformDate(myDate.getTime());
             }else {
                 SimpleDateFormat df = new SimpleDateFormat("MM-dd");
                 ti = df.format(myDate);
             }
         }
        return ti;

    }




    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime() / 1000;
    }

    /*将字符串转为时间戳*/
    public static long getStringToDatesed(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

}
