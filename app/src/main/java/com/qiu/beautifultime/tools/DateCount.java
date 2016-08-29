package com.qiu.beautifultime.tools;

/**
 * 计算设置时间距当前的总天数(粗略计算)
 * Created by dllo on 16/8/19.
 */
public class DateCount {

    private static Long nowTime;

    private static Long dateCounts() {
        if (nowTime == null) {
            nowTime = System.currentTimeMillis();
        }
        return nowTime;
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getYear() {
        return (int) (dateCounts() / 31536000000L) + 1970;
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getMonth() {
        return (int) ((dateCounts() % 31536000000L) / 2628000000L) + 1;
    }

    /**
     * 获取当前日
     *
     * @return
     */
    public static int getDay() {
        return (int) ((dateCounts() % 31536000000L % 2628000000L) / 86400000) - 10;
    }
}
