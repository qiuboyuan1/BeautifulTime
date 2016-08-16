package com.qiu.beautifultime.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import com.qiu.beautifultime.R;

/**
 * Created by dllo on 16/8/16.
 * 欢迎页
 */
public class WelcomeActivity extends AbsBaseActivity{
    private CountDownTimer countDownTimer;
    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        }.start();

    }
}
