package com.qiu.beautifultime.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.tools.LockPatternUtils;

/**
 * Created by dllo on 16/8/16.
 * 欢迎页
 */
public class WelcomeActivity extends AbsBaseActivity{
    private CountDownTimer countDownTimer;
    private TextView tvStart;
    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        tvStart = byView(R.id.tv_start);
    }

    @Override
    protected void initData() {
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
//                if (!LockPatternUtils.isLogin()) {
//                    startActivity(new Intent(WelcomeActivity.this, CheckoutGestureLockActivity.class));
//                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
//                }
                finish();
            }
        });
        countDownTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (!LockPatternUtils.isLogin()) {
                    startActivity(new Intent(WelcomeActivity.this, CheckoutGestureLockActivity.class));
                }else{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            }
        }.start();

    }
}
