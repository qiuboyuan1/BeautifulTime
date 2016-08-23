package com.qiu.beautifultime.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * 设置记录的类
 * Created by dllo on 16/8/15.
 */
public class ItemTimeData {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private String title;
    private int date;
    private int color;
    private String pictureName;
    private Long recordTime;

    public ItemTimeData() {
    }

    public ItemTimeData(String title, int date, int color, String pictureName,Long recordTime) {
        this.title = title;
        this.date = date;
        this.color = color;
        this.pictureName = pictureName;
        this.recordTime=recordTime;
    }

    public ItemTimeData(int id, String title, int date, int color, String pictureName) {
        id = id;
        this.title = title;
        this.date = date;
        this.color = color;
        this.pictureName = pictureName;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
