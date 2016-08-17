package com.qiu.beautifultime.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.qiu.beautifultime.R;

/**
 * Created by dllo on 16/8/13.
 * 视频播放
 */
public class VideoActivity extends AbsBaseActivity implements View.OnClickListener {
    private VideoView videoView;
    private ImageView ivBack, ivShare;

    @Override
    protected int setLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        ivBack = byView(R.id.iv_video_back);
        ivShare = byView(R.id.iv_setting_share);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.guide));
        videoView.start();
        //播放完成跳转
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(new Intent(VideoActivity.this, SettingActivity.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_video_back:
                finish();
                break;
            case R.id.iv_setting_share:
                break;

        }
    }
}
