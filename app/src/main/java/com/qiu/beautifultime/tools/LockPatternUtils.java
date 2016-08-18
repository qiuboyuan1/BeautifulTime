package com.qiu.beautifultime.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 手势密码设置
 *
 * @author zhang
 */
public class LockPatternUtils {
    private static SharedPreferences sp;
    private static Editor editor;

    public static void CSHSp(Context context) {
        sp = context.getSharedPreferences("login", Context.MODE_APPEND);
        editor = sp.edit();
    }

    /**
     * 保存设置的手势密码,使用时应当这里做了加密再保存
     *
     * @param locks
     */
    public static void saveLockPattern(String key, String locks) {
        editor.putString(key, locks);
        editor.commit();
    }

    /**
     * 读取手势密码
     *
     * @param
     * @param key
     * @return
     */
    public static String getLockPattern(String key) {
        return sp.getString(key, null);
    }


    /**
     * 设置登录
     *
     * @param
     * @param isLogin
     */
    public static boolean setLogin(boolean isLogin) {
        editor.putBoolean("login_key", isLogin);
        editor.commit();
        return isLogin;
    }

    /**
     * 获取是否登录
     *
     * @param
     * @return
     */
    public static boolean isLogin() {
        return sp.getBoolean("login_key", false);
    }
}
