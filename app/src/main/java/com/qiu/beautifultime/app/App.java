package com.qiu.beautifultime.app;

import android.app.Application;
import android.content.Context;

import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.tools.LockPatternUtils;
import com.qiu.beautifultime.tools.SpInstence;

/**
 * app类对外提供全局的context
 * Created by dllo on 16/8/15.
 */
public class App extends Application {
    public static Context sContext;

    public static final String GESTURE_KEY="gesture_key";

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        LockPatternUtils.CSHSp(sContext);
        OrmInstence.getOrmInstence().setOrmInstence();
        SpInstence.getInstence().initalizeSp(sContext);
    }


}
