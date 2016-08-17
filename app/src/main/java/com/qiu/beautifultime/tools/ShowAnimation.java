package com.qiu.beautifultime.tools;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import android.os.CountDownTimer;
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
        private TimeCount  timeCount;
    private ImageView imageView;
    private LinearLayout LlEdit, LlDownload, LlDelete, LlNew;


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

    public void ShowView() {


        if (asd) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(LlEdit, "translationX", 0, 800, 700, 750)
            );
            set.setDuration(1000).start();
            AnimatorSet set1 = new AnimatorSet();
            set1.playTogether(
                    ObjectAnimator.ofFloat(LlDownload, "translationX", 0, 800, 700, 750)
            );
            set1.setDuration(1200).start();
            AnimatorSet set2 = new AnimatorSet();
            set2.playTogether(
                    ObjectAnimator.ofFloat(LlDelete, "translationX", 0, 800, 700, 750)
            );
            set2.setDuration(1500).start();
            AnimatorSet set3 = new AnimatorSet();
            set3.playTogether(
                    ObjectAnimator.ofFloat(LlNew, "translationX", 0, 800, 700, 750)
            );
            set3.setDuration(1800).start();
            asd = false;
            timeCount.start();
        } else {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(LlEdit, "translationX", 750, 0)
            );
            set.setDuration(500).start();
            AnimatorSet set1 = new AnimatorSet();
            set1.playTogether(
                    ObjectAnimator.ofFloat(LlDownload, "translationX", 750, 0)
            );
            set1.setDuration(600).start();
            AnimatorSet set2 = new AnimatorSet();
            set2.playTogether(
                    ObjectAnimator.ofFloat(LlDelete, "translationX", 750, 0)
            );
            set2.setDuration(700).start();
            AnimatorSet set3 = new AnimatorSet();
            set3.playTogether(
                    ObjectAnimator.ofFloat(LlNew, "translationX", 750, 0)
            );
            set3.setDuration(800).start();
            asd = true;
        }
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
