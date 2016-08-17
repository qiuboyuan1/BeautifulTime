package com.qiu.beautifultime.ui.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.tools.DataCleanManager;
import com.qiu.beautifultime.ui.fragment.BeautifulTimeNotesFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingActivity extends AbsBaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private RelativeLayout setting_remind, setting_password, setting_playVideo,
            setting_update, setting_clearCache;
    private PopupWindow popupWindow;
    private TextView tvCache;
    private Switch aSwitch;
    private String str;
    private NotificationManager manager;
    private LinearLayout exit;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        ivBack = byView(R.id.iv_setting_back);
        setting_remind = byView(R.id.setting_remind);
        setting_password = byView(R.id.setting_password);
        setting_playVideo = byView(R.id.setting_play_video);
        setting_update = byView(R.id.setting_update);
        aSwitch = byView(R.id.setting_notification);
        setting_clearCache = byView(R.id.setting_clear_cache);
        tvCache = byView(R.id.tv_cache);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(this);
        setting_remind.setOnClickListener(this);
        setting_password.setOnClickListener(this);
        setting_playVideo.setOnClickListener(this);
        setting_update.setOnClickListener(this);
        aSwitch.setOnClickListener(this);
        setting_clearCache.setOnClickListener(this);
        aSwitch.setChecked(false);
        //显示所有缓存
        try {
            tvCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取系统时间
        SimpleDateFormat formats = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        str = formats.format(date);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting_back:
                finish();
                break;
            case R.id.iv_setting_share:
                break;
            case R.id.setting_remind:
                initPop();
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();// 关闭
                } else {
                    popupWindow.showAtLocation(SettingActivity.this.setting_remind, Gravity.BOTTOM, 0, 0);
                    backgroundAlpha(0.6f);
                }
                break;
            case R.id.setting_password:
                startActivity(new Intent(SettingActivity.this, LockActivity.class));
                break;
            case R.id.setting_play_video:
                Intent intent = new Intent(SettingActivity.this, VideoActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_update:
                Toast.makeText(this, "已经是最新版本了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_notification:
                if (aSwitch.isChecked()) {
                    manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(this);
                    //自定义通知布局
                    RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification);
                    views.setTextViewText(R.id.tv_date, str);
                    views.setTextViewText(R.id.tv_content, "今天是个重要的日子");
                    views.setTextColor(R.id.tv_date, Color.GRAY);
                    views.setTextColor(R.id.tv_content, Color.GRAY);

                    builder.setSmallIcon(R.mipmap.password_icon_zuidays);
                    builder.setContent(views);
                    Intent intent1 = new Intent(this, MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    //记录按钮
                    Intent intent2 = new Intent(this, BeautifulTimeChooseActivity.class);
                    PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
                    views.setOnClickPendingIntent(R.id.btn_record, pendingIntent2);

                    Notification notification = builder.build();
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    manager.notify(0, notification);

                } else {
                    manager.cancelAll();
                }

                break;
            case R.id.setting_clear_cache:
                DataCleanManager.clearAllCache(this);
                Toast.makeText(this, "缓存已清除", Toast.LENGTH_SHORT).show();
                tvCache.setText("0KB");
                tvCache.setTextColor(Color.GRAY);
                break;
        }
    }

    private void initPop() {
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = getLayoutInflater().inflate(R.layout.item_pop, null);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.mipmap.ic_launcher));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        exit = (LinearLayout) view.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
