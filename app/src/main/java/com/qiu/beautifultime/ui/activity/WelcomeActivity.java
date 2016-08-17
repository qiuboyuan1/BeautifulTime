package com.qiu.beautifultime.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.tools.LockPatternUtils;

/**
 * Created by dllo on 16/8/16.
 * 欢迎页
 */
public class WelcomeActivity extends Activity {
    private CountDownTimer countDownTimer;
    private TextView tvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("guide", MODE_PRIVATE);
        if (sp.getBoolean("isFirst", true)) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        tvStart = (TextView) findViewById(R.id.tv_start);
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
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (!LockPatternUtils.isLogin()) {
                    startActivity(new Intent(WelcomeActivity.this, CheckoutGestureLockActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
            }
        }.start();

    }

}





