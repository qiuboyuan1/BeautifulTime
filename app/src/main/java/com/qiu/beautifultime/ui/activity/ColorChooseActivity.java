package com.qiu.beautifultime.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.qiu.beautifultime.R;

/**
 * 颜色选择界面
 * Created by dllo on 16/8/17.
 */
public class ColorChooseActivity extends AbsBaseActivity implements View.OnClickListener {
    private ColorPicker colorPicker;
    private SVBar svBar;
    private OpacityBar opacityBar;//透明度
    private SaturationBar saturationBar;//饱和度
    private ValueBar valueBar;
    private ImageButton backIBtn;//返回
    private ImageButton okIBtn;//确定

    @Override
    protected int setLayout() {
        return R.layout.activity_choose_color;
    }

    @Override
    protected void initView() {
        svBar = byView(R.id.choose_color_sv_bar);
        valueBar = byView(R.id.choose_color_value_bar);
        opacityBar = byView(R.id.choose_color_opacity_bar);
        colorPicker = byView(R.id.choose_color_picker);
        saturationBar = byView(R.id.choose_color_saturation_bar);
        backIBtn = byView(R.id.choose_color_back_iv_btn);
        okIBtn = byView(R.id.choose_color_ok_iv_btn);
    }

    @Override
    protected void initData() {

        colorPicker.addSVBar(svBar);
        colorPicker.addOpacityBar(opacityBar);
        colorPicker.addSaturationBar(saturationBar);
        colorPicker.addValueBar(valueBar);
        //设置监听
        backIBtn.setOnClickListener(this);
        okIBtn.setOnClickListener(this);
        //颜色的改变监听
        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_color_back_iv_btn:
                finish();
                break;
            case R.id.choose_color_ok_iv_btn:
                //发送颜色到chooseactivity界面
                Intent intent = new Intent("com.qiu.beautifulTime.get.color");
                intent.putExtra("color", colorPicker.getColor());
                sendBroadcast(intent);
                finish();
                break;
        }
    }
}
