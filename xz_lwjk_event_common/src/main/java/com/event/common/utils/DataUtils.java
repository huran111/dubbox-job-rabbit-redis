package com.event.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huran
 * @Date: 2018/8/10 14:01
 * @Description:
 */
public class DataUtils {
    /**
     * 时间戳转换
     * HH:mm:ss
     *
     * @param timeTemp
     * @return
     */
    public static String getHMS(long timeTemp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeTemp);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String time = sdf.format(calendar.getTime());
        return time;
    }

    /**
     * 时间戳相减
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDateSubtracting(Long start, Long end) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date d1 = format.parse(getHMS(start));
            Date d2 = format.parse(getHMS(end));
            //得到的差值是微秒级别
            long diff = d1.getTime() - d2.getTime();
            return diff/1000 + "秒";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
