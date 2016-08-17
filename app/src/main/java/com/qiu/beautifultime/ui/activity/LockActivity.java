package com.qiu.beautifultime.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.qiu.beautifultime.R;

/**
 * Created by dllo on 16/8/17.
 * 设置手势锁Activity
 */
public class LockActivity extends AbsBaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private Switch aSwitch;

    @Override
    protected int setLayout() {
        return R.layout.activity_lock;
    }

    @Override
    protected void initView() {
        aSwitch = byView(R.id.password_lock);
        ivBack = byView(R.id.iv_lock_back);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(this);
        aSwitch.setOnClickListener(this);
        aSwitch.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_lock_back:
                finish();
                break;
            case R.id.password_lock:
                if (aSwitch.isChecked()) {
                    startActivity(new Intent(LockActivity.this, SetGestureLockActivity.class));
                } else {
                    Toast.makeText(this, "已关闭密码锁定", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
