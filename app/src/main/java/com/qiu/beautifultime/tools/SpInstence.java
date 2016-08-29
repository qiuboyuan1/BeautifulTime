package com.qiu.beautifultime.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sp单例类
 * Created by dllo on 16/8/24.
 */
public class SpInstence {
    private static SpInstence spInstence;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;


    public static SpInstence getInstence() {
        if (spInstence == null) {
            synchronized (SpInstence.class) {
                if (spInstence == null) {
                    spInstence = new SpInstence();
                }
            }
        }
        return spInstence;
    }

    /**
     * 初始化sp
     */
    public void initalizeSp(Context context) {
        sharedPreferences = context.getSharedPreferences("life", Context.MODE_APPEND);
        editor = sharedPreferences.edit();
    }

    /**
     * 获取之前选择的颜色
     */
    public int getNextColor() {
        int color = sharedPreferences.getInt("color", 0xff000000);
        return color;
    }

    /**
     * 存储当前选择的颜色
     */
    public void putColor(int color) {
        editor.putInt("color", color);
        editor.commit();
    }
}
