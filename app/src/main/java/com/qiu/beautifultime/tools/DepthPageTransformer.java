package com.qiu.beautifultime.tools;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * show页面viewpager的切换动画
 * Created by dllo on 16/8/12.
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.8f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        if (position < -1) {
            ViewHelper.setAlpha(page, 0);
        } else if (position <= 0) {
            ViewHelper.setAlpha(page, 1);
            ViewHelper.setTranslationX(page, 0);
            ViewHelper.setScaleX(page, 1);
            ViewHelper.setScaleY(page, 1);
        } else if (position <= 1) {
            ViewHelper.setAlpha(page, 1 - position);
            ViewHelper.setTranslationX(page, pageWidth * -position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            ViewHelper.setScaleX(page, scaleFactor);
            ViewHelper.setScaleY(page, scaleFactor);
        } else {
            ViewHelper.setAlpha(page, 0);
        }
    }
}
