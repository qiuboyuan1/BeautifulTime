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
    private String date;
    private int color;
    private String pictureName;

    public ItemTimeData() {
    }

    public ItemTimeData(String title, String date, int color, String pictureName) {
        this.title = title;
        this.date = date;
        this.color = color;
        this.pictureName = pictureName;
    }

    public ItemTimeData(int id, String title, String date, int color, String pictureName) {
        id = id;
        this.title = title;
        this.date = date;
        this.color = color;
        this.pictureName = pictureName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
