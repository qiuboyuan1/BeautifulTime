package com.qiu.beautifultime.tools;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dllo on 16/8/16.
 */
public class ShowAnimation {
    private boolean asd = true;
    private TimeCount timeCount;
    private ImageView imageView;
    private LinearLayout LlEdit, LlDownload, LlDelete, LlNew;
    private MyViewPager myViewPager;

    public ShowAnimation(LinearLayout LlEdit, LinearLayout LlDownload, LinearLayout LlDelete, LinearLayout LlNew) {
        this.LlEdit = LlEdit;
        this.LlDownload = LlDownload;
        this.LlDelete = LlDelete;
        this.LlNew = LlNew;
        timeCount = new TimeCount(1800, 1000);
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;

    }

    public void setMyViewPager(MyViewPager myViewPager) {
        this.myViewPager = myViewPager;
    }


    public void ShowView() {


        if (asd) {
            asd = false;
            timeCount.start();

            setAnimator(LlEdit, 1000);

            setAnimator(LlDownload, 1200);

            setAnimator(LlDelete, 1500);

            setAnimator(LlNew, 1800);
            myViewPager.setScanScroll(false);
        } else {

            setAnimatorBack(LlEdit, 500);

            setAnimatorBack(LlDownload, 600);
            setAnimatorBack(LlDelete, 700);
            setAnimatorBack(LlNew, 800);
            myViewPager.setScanScroll(true);
            asd = true;
        }
    }

    private void setAnimatorBack(Object view, int duration) {
        ObjectAnimator.ofFloat(view, "translationX", 750, 0).setDuration(duration).start();
    }

    private void setAnimator(Object view, int duration) {
        ObjectAnimator.ofFloat(view, "translationX", 0, 800, 700, 750).setDuration(duration).start();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            imageView.setClickable(false);


        }

        @Override
        public void onFinish() {
            imageView.setClickable(true);


        }
    }


}
