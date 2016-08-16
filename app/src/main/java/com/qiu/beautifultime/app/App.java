package com.qiu.beautifultime.app;

import android.app.Application;
import android.content.Context;

/**
 * app类对外提供全局的context
 * Created by dllo on 16/8/15.
 */
public class App extends Application {
    private static Context sContext;

    public static final String GESTURE_KEY="gesture_key";

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getsContext() {
        return sContext;
    }
}
