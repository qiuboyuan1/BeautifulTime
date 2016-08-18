package com.qiu.beautifultime.data;

/**
 * 设置记录的类
 * Created by dllo on 16/8/15.
 */
public class ItemTimeData {
    private String days;
    private int color;

    public ItemTimeData() {
    }

    public ItemTimeData(String days,int color) {
        this.days = days;
        this.color=color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
