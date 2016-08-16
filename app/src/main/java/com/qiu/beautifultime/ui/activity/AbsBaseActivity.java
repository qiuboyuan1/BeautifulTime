package com.qiu.beautifultime.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.qiu.beautifultime.R;


/**
 * activity基类
 * Created by dllo on 16/8/15.
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int setLayout();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(setLayout());
        initView();
        initData();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();

    protected <T extends View> T byView(int id) {
        T t = (T) findViewById(id);
        return t;
    }

    /**
     * 添加数据
     */
    protected abstract void initData();

}
