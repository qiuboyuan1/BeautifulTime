package com.qiu.beautifultime.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 自定义进度条
 * Created by dllo on 16/8/17.
 */
public class MProgressView extends View {


    private Paint mBackPaint;//背景画笔
    private Paint mFrontPaint;//进度画笔
    private int frontColor = 16756993;//进度颜色默认
    private RectF mRect;//矩形对象
    private int mWidth;//view的宽
    private int mHeight;//view的高
    private int progress = 100;//进度默认


    public MProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 设置背景色
     */
    private void init() {
        //背景
        mBackPaint = new Paint();
        mBackPaint.setColor(0x00ffffff);
        mBackPaint.setAntiAlias(true);//抗锯齿
        mBackPaint.setStyle(Paint.Style.FILL);
        //初始化
        mFrontPaint = new Paint();
    }


    /**
     * 设置进度
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * 设置进度颜色
     *
     * @param color
     */
    public void setProgressColor(int color) {
        if (color != 0) {
            this.frontColor = color;
            //进度条
            mFrontPaint.setColor(frontColor);
            mFrontPaint.setAntiAlias(true);
            //设置画笔类型
            //Paint.Style.STROKE 表示当前只绘制图形的轮廓，而Paint.Style.FILL表示填充图形。
            mFrontPaint.setStyle(Paint.Style.FILL);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("CirclePgBar", "--measure--");
        mWidth = getWidthRealSize(widthMeasureSpec);//测量view的宽
        mHeight = getHeightRealSize(heightMeasureSpec);//测量view的高
        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 返回高
     *
     * @param heightMeasureSpec
     * @return
     */
    private int getHeightRealSize(int heightMeasureSpec) {
        int result = 1;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算
            result = mHeight;
        } else {
            result = size;
        }
        return result;

    }

    /**
     * 返回宽
     *
     * @param widthMeasureSpec
     * @return
     */
    private int getWidthRealSize(int widthMeasureSpec) {
        int result = 1;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算
            result = mWidth;
        } else {
            result = size;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //注意绘制的顺序
        initRect();
        //画背景
        canvas.drawRect(0, 0, mWidth, mHeight, mBackPaint);
        //画进度
        canvas.drawRect(0, 0, progress, mHeight, mFrontPaint);
        invalidate();
    }

    private void initRect() {
        if (mRect == null) {
            mRect = new RectF();
            mRect.set(0, 0, mWidth, mHeight);
        }
    }


}
