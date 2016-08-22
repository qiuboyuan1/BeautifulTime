package com.qiu.beautifultime.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import com.qiu.beautifultime.R;

/**
 * Created by dllo on 16/8/17.
 * 引导页
 */
public class GuideActivity extends Activity implements View.OnClickListener {
    private VideoView videoView;
    private TextView tvJump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化我们的editor
        SharedPreferences.Editor editor = getSharedPreferences("guide",MODE_PRIVATE).edit();
        editor.putBoolean("isFirst",false);
        editor.commit();
        //取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        tvJump = (TextView) findViewById(R.id.tv_jump);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.guide));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
            }
        });
        tvJump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(GuideActivity.this,MainActivity.class));
        finish();
    }
}
