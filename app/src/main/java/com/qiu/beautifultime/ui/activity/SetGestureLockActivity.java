package com.qiu.beautifultime.ui.activity;

import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.TextView;
import com.qiu.beautifultime.R;
import com.qiu.beautifultime.app.App;
import com.qiu.beautifultime.view.GestureLockView;
import com.qiu.beautifultime.tools.LockPatternUtils;
import com.qiu.beautifultime.ui.adapter.LockAdapter;


/**
 * 设置手势锁Activity
 */
public class SetGestureLockActivity extends AbsBaseActivity {

    private GridView gv_lock;
    private TextView gv_textview;
    private GestureLockView gestureLockView;
    private LockAdapter lockAdapter;
    private boolean isSetting;
    private TranslateAnimation animation;
    private int limitNum = 4;

    @Override
    protected int setLayout() {
        return R.layout.activity_set_gesturelock;
    }

    protected void initView() {
        gv_lock = byView(R.id.gv_lock);
        gv_textview = byView(R.id.gv_textview);
        gestureLockView = byView(R.id.gestureLockView);
    }

    @Override
    protected void initData() {
        lockAdapter = new LockAdapter(this);
        gv_lock.setAdapter(lockAdapter);
        gv_textview.setText("绘制解锁图案");
        gv_textview.setVisibility(View.VISIBLE);
        gv_textview.setTextColor(getResources().getColor(R.color.white));
        gestureLockView.setLimitNum(limitNum);
        initView();
        addListener();
    }

    private void addListener() {
        animation = new TranslateAnimation(-20, 20, 0, 0);
        animation.setDuration(50);
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.REVERSE);
        gestureLockView.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
            @Override
            public void OnGestureFinish(boolean success, String key) {
                if (success) {
                    lockAdapter.setKey(key);
                    if (!isSetting) {
                        gv_textview.setText("再次绘制图案进行确认");
                        gestureLockView.setKey(key);
                        isSetting = true;
                    } else {
                        gv_textview.setTextColor(Color.WHITE);
                        gv_textview.setText("设置成功");
                        LockPatternUtils.saveLockPattern(App.GESTURE_KEY, key);
//                        startActivity(new Intent(SetGestureLockActivity.this, CheckoutGestureLockActivity.class));
                        finish();
                    }

                } else {
                    if (key.length() >= limitNum)
                        gv_textview.setText("请重新输入");
                    else
                        gv_textview.setText("至少需要连接" + limitNum + "个点,请重试");
                    gv_textview.startAnimation(animation);
                    gv_textview.setTextColor(Color.RED);
                }

            }
        });

    }

}
