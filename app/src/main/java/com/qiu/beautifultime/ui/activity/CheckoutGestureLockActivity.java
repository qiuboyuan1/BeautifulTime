package com.qiu.beautifultime.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;
import com.qiu.beautifultime.R;
import com.qiu.beautifultime.app.App;
import com.qiu.beautifultime.tools.GestureLockView;
import com.qiu.beautifultime.tools.LockPatternUtils;

/**
 * 验证手势锁Activity
 */
public class CheckoutGestureLockActivity extends AbsBaseActivity {

    private GestureLockView gestureLockView;
    private TextView textview;
    private Animation animation;
    private int errorNum;
    private int limitErrorNum = 5;

    @Override
    protected int setLayout() {
        return R.layout.activity_checkout_gesturelock;
    }

    @Override
    protected void initView() {
        gestureLockView = byView(R.id.gestureLockView);
        textview = byView(R.id.textview);
    }

    @Override
    protected void initData() {
        animation = new TranslateAnimation(-20, 20, 0, 0);
        animation.setDuration(50);
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.REVERSE);
        String key = LockPatternUtils.getLockPattern(App.GESTURE_KEY);
        if (TextUtils.isEmpty(key)) {
            finish();
        } else {
            gestureLockView.setKey(key);
        }
        gestureLockView.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
            @Override
            public void OnGestureFinish(boolean success, String key) {
                if (success) {
                    textview.setTextColor(Color.parseColor("#FFFFFF"));
                    textview.setVisibility(View.VISIBLE);
                    textview.setText("登陆成功");
//                    textview.startAnimation(animation);
                    startActivity(new Intent(CheckoutGestureLockActivity.this, MainActivity.class));
                    finish();
                } else {
//                    errorNum++;
//                    if (errorNum >= limitErrorNum) {
                        Toast.makeText(getApplicationContext(), "密码输入错误" , Toast.LENGTH_SHORT).show();
//                        LockPatternUtils.setLogin(false);
//                        LockPatternUtils.saveLockPattern(App.GESTURE_KEY, "");
//                        startActivity(new Intent(CheckoutGestureLockActivity.this, SetGestureLockActivity.class));
//                        finish();
//                    }
//                    textview.setTextColor(Color.parseColor("#FF2525"));
//                    textview.setVisibility(View.VISIBLE);
//                    textview.setText("密码输入错误" + errorNum + "次");
//                    textview.startAnimation(animation);

                }
            }
        });

    }

}
