package com.qiu.beautifultime.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * 重力感应类
 * Created by dllo on 16/8/13.
 */
public class SensorImg implements SensorEventListener {
    private static Matrix matrix;
    private static SensorManager sensorManager;
    private static Context context;
    //以速度1向左移动
    private static final int LEFT_1 = 1;

    //以速度2向左移动
    private static final int LEFT_2 = 2;

    //以速度1向右移动
    private static final int RIGHT_1 = 3;

    //以速度2向右移动
    private static final int RIGHT_2 = 4;

    private static int orientation;
    private static Bitmap bitmap;

    private static SensorImg sensorImg;
    private int width;

    public SensorImg(Context context, ImageView imageView, String s, int width) {
        this.width = width;
        // 获取系统传感器服务
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        //设置监听器监听加速度传感器（重力感应器）的状态，精度为普通（SENSOR_DELAY_NORMAL）
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//        Bitmap bitmap1 = ((BitmapDrawable) imageView.getBackground()).getBitmap();
//        if (!bitmap1.isRecycled()) {
//            bitmap1.recycle();
//        }
        bitmap = ReadAssets.ReadAssets(context, s);
        imageView.setImageBitmap(bitmap);


        matrix = new Matrix();
        matrix.postScale(1f, 1f, 0.5f, 0.5f);
        matrix.postTranslate(-540, 540);
        imageView.setImageMatrix(matrix);
        imageView.invalidate();
        imageView.startAnimation(getAnimation(imageView));
    }

    public static void getSensorImg(Context context, ImageView imageView, String s, int width) {

        sensorImg = new SensorImg(context, imageView, s, width);
    }


    private Animation getAnimation(ImageView imageView) {
        MAnimation animation = new MAnimation();
        animation.setImageView(imageView);
        animation.setDuration(10000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //若传感器类型为加速度传感器（重力感应器）
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //获取X坐标
            int x = (int) event.values[0];

            if (x == 0) orientation = RIGHT_1;//默认向左移动

            if (x < 2 && x > 0) orientation = RIGHT_1;

            if (x > 2) orientation = RIGHT_2;

            if (x < 0 && x > -2) orientation = LEFT_1;

            if (x < -2) orientation = LEFT_2;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class MAnimation extends Animation {
        private ImageView imageView;

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            //运行方法，获取左上点的坐标，用于设置边界
            getLeftPointF();

            if (orientation == LEFT_1) {
                matrix.postTranslate(interpolatedTime * 2, 0);
            }
            if (orientation == RIGHT_1) {
                matrix.postTranslate(-interpolatedTime * 2, 0);
            }
            if (orientation == LEFT_2) {
                matrix.postTranslate(interpolatedTime * 5, 0);
            }
            if (orientation == RIGHT_2) {
                matrix.postTranslate(-interpolatedTime * 5, 0);
            }

            imageView.setImageMatrix(matrix);
            imageView.invalidate();
        }
    }

    private PointF getLeftPointF() {
        float[] values = new float[9];
        float[] rightValues = {1f, 0, -width, 0, 1f, -0.25f, 0, 0, 1.0f};
        float[] leftValues = {1f, 0, 0, 0, 1f, -0.25f, 0, 0, 1.0f};

        matrix.getValues(values);

        //若超出边界，为其设置自定义的位置
        if (values[2] < -width) {
            matrix.setValues(rightValues);
        }
        if (values[2] > 0) {
            matrix.setValues(leftValues);
        }

        float leftX = values[2];
        float leftY = values[5];
        return new PointF(leftX, leftY);
    }
}
