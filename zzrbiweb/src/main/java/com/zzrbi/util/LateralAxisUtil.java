package com.zzrbi.util;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Desc： 图标显示  横轴数据
 * User：ZhaoBiao
 * Date：2018-01-29
 * Time：17:16
 */
public class LateralAxisUtil {

    private static Logger logger = Logger.getLogger(LateralAxisUtil.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");



    /**
     * Desc： 图标显示  横轴数据 小时
     * User：ZhaoBiao
     * Date：2018-01-29
     * Time：17:40
     */
    public  static String[] getLateralAxisByTime(){

        String[] arrayStr = {"1:00:00","2:00:00","3:00:00","4:00:00","5:00:00","6:00:00","7:00:00","8:00:00","9:00:00","10:00:00","11:00:00","12:00:00","13:00:00","14:00:00","15:00:00","16:00:00","17:00:00","18:00:00","19:00:00","20:00:00","21:00:00","22:00:00","23:00:00"};

        return arrayStr;
    }


    /**
     * Desc： 图标显示  横轴数据 天
     * User：ZhaoBiao
     * Date：2018-01-29
     * Time：17:30
     */
    public  static String[] getLateralAxisByDays(String startTime,String endTime){

        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        String[] arrayStr = null;

        try{

            Calendar cal = Calendar.getInstance();
            Date dBegin = sdf.parse(startTime);
            Date dEnd = sdf.parse(endTime);
            List<Date> lDate = findDates(dBegin, dEnd);
            arrayStr = new String[lDate.size()];
            for(int i=0;i<lDate.size();i++){
                arrayStr[i] = sf.format(lDate.get(i));
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        return arrayStr;

    }

    public static List<Date> findDates(Date dBegin, Date dEnd)
    {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }



    /**
     * Desc： 图标显示  横轴数据 周
     * User：ZhaoBiao
     * Date：2018-01-29
     * Time：17:30
     */
    public static String[] getLateralAxisByWeek(String startTime,String endTime){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String[] arrayStr = null;

        try{

            Date startDate = sdf.parse(startTime);

            Date endDate = sdf.parse(endTime);

            int arrayLen = 0;

            if(startDate.getYear() != endDate.getYear()){

                int yearCha = endDate.getYear() - startDate.getYear();

                int yearlen = 0;
                for(int i=0;i<yearCha;i++){

                    String time = (1900+startDate.getYear() + i) +"-12-31";

                    yearlen += getWeekNum(time);
                }
                arrayLen = yearlen - getWeekNum(startTime) + getWeekNum(endTime);

            }else{
                arrayLen = getWeekNum(endTime) - getWeekNum(startTime);
            }

            System.out.println(arrayLen);

            arrayStr = new String[arrayLen+1];

            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            for(int i=0;i<arrayLen+1;i++){

                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.DATE, 7*i);

                Date monday = getBeginDayOfWeek(cal.getTime());

                Date sunday = getEndDayOfWeek(cal.getTime());

                arrayStr[i] = df.format(monday)+"~" + df.format(sunday);
                System.out.println(df.format(monday)+"~" + df.format(sunday));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return arrayStr;
    }
    //获取 某个时间是当前年的 第几周
    public static int getWeekNum(String  time){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(time);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  calendar.get(Calendar.WEEK_OF_YEAR);

    }


    //获取本周的开始时间
    public static Date getBeginDayOfWeek(Date date) {

        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }
    //获取本周的结束时间
    public static Date getEndDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * Desc： 图标显示  横轴数据 月
     * User：ZhaoBiao
     * Date：2018-01-29
     * Time：17:30
     */
    public static String[] getLateralAxisByMonth(String startTime,String endTime){


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String[] arrayStr = null;

        try {

            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);

            int month = endCalendar.get(Calendar.MONTH)
                    - startCalendar.get(Calendar.MONTH);
            int year = endCalendar.get(Calendar.YEAR)
                    - startCalendar.get(Calendar.YEAR);

            if (month < 0) {
                month += 12;
                year--;
            }
            if(year > 0){
                month = month + year*12;
            }
            System.out.println("两者相差月为：" + month + "个月");

            arrayStr = new String[month+1];

            Calendar cal = null;

            for(int i=0;i<month+1;i++){

                cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.MONTH,i);
                int y = cal.get(Calendar.YEAR);
                int m = cal.get(Calendar.MONTH);
                arrayStr[i] = getFirstDayOfMonth(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)) +"~"+getLastDayOfMonth(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayStr;

    }


    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        cal.add(5, -1);
        return   new   SimpleDateFormat( "yyyy/MM/dd").format(cal.getTime());
    }
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        return   new   SimpleDateFormat( "yyyy/MM/dd").format(cal.getTime());
    }


    /**
     * Desc： 获取 某个周 周一的时间 字符串类型
     * User：ZhaoBiao
     * Date：2018-01-31
     * Time：17:30
     */
    public  static String getFirstWeek(String time){

        Date startTime = null;

        try{
            startTime = getBeginDayOfWeek(sdf.parse(time));

        }catch (Exception e){
            e.printStackTrace();
        }
        return sdf.format(startTime);
    }

    /**
     * Desc： 获取 某个周 周日的时间 字符串类型
     * User：ZhaoBiao
     * Date：2018-01-31
     * Time：17:40
     */
    public  static String getLastWeek(String time){

        Date endTime = null;

        try{
            endTime = getEndDayOfWeek(sdf.parse(time));

        }catch (Exception e){
            e.printStackTrace();
        }
        return sdf.format(endTime);
    }

    /**
     * Desc： 格式化时间  为 年 月
     * User：ZhaoBiao
     * Date：2018-01-31
     * Time：16:20
     */
    public static String formatTimeToYm(String time){

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");

        String endTime = "";
        try{

            endTime = sd.format(sd.parse(time));

        }catch (Exception e){
            e.printStackTrace();
        }
        return endTime;
    }



    /**
     * Desc： 格式化时间 生成不同的 时间点
     * User：ZhaoBiao
     * Date：2018-02-01
     * Time：14:40
     */
    public static String formatTime(String time,int type){


        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        String  timeStr = "";

         try{

             switch (type){

                 case 1:

                     timeStr = formatTimeToHH(time);

                     break;

                 case 2:

                     timeStr = time;

                     break;

                 case 3:

                     Date monday = getBeginDayOfWeek(sdf.parse(time));

                     Date sunday = getEndDayOfWeek(sdf.parse(time));

                     timeStr = df.format(monday)+"~" + df.format(sunday);

                     break;
                 case 4:

                     Calendar cal = Calendar.getInstance();
                     cal.setTime(sdf.parse(time+"-01"));
                     int y = cal.get(Calendar.YEAR);
                     int m = cal.get(Calendar.MONTH);
                     timeStr = getFirstDayOfMonth(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH))+"~"+getLastDayOfMonth(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1);

                     break;

             }
         }catch (Exception e){
             e.printStackTrace();
        }

        return timeStr;
    }


    /**
     * Desc： 格式化时间  为 小时
     * User：ZhaoBiao
     * Date：2018-02-01
     * Time：14:20
     */
    public  static String formatTimeToHH(String time){

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制

        String timeStr = "";

        try{

            Date date = sdformat.parse(time);

            timeStr = date.getHours() +":00:00";

        }catch (Exception e){
            e.printStackTrace();
        }
       return timeStr;

    }







}
