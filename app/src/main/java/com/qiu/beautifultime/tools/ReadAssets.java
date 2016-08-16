package com.qiu.beautifultime.tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 从系统资源文件assets夹获取图片
 * Created by dllo on 16/8/12.
 */
public class ReadAssets {
    public ReadAssets() {
    }
    public static Bitmap ReadAssets(Context context, String s) {
        Bitmap bitmap = null;
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream stream = assetManager.open(s);
            bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
