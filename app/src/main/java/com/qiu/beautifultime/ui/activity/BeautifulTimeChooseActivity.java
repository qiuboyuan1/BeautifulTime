package com.qiu.beautifultime.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.data.SetPictureData;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.tools.DateCount;
import com.qiu.beautifultime.tools.SensorImg;
import com.qiu.beautifultime.tools.SpInstence;
import com.qiu.beautifultime.ui.adapter.OneRecyclerViewAdapter;
import com.qiu.beautifultime.ui.adapter.TimeItemRecycleViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 设置,选择照片界面
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeChooseActivity extends AbsBaseActivity implements View.OnClickListener {
    private List<SetPictureData> smailDatas = new ArrayList<>();
    private RecyclerView recyclerView;
    private OneRecyclerViewAdapter adapter;
    private ImageView imageView;
    private ImageButton backIbtn;//返回图标
    private ImageButton okIbtn;//确定图标
    private int dataSize = 0;
    private TextView colorTv;//颜色选择按钮
    private int color;
    private MyBroadCast myBroadCast;
    private DatePicker datePicker;//日期选择器
    private LinearLayout dateLl;//日期
    private LinearLayout repictLl;//
    private LinearLayout colorLl;//颜色
    private boolean isvisibility = true;//
    private int chooseDate = 0;//选择的日期,用于显示在item上,默认为0
    private TextView dateTv;//显示日期
    private String dates;//用于显示在当前的日期按钮上
    private ItemTimeData itemTimeData;
    private String[] picture = new String[]{"baby.jpg", "birthday.jpg", "sport.jpg", "love.jpg", "school.jpg"};
    private int position;
    private RelativeLayout layout;
    private EditText titleEt;
    private TextView titleTv;
    private boolean isVisibility = false;//显示title
    private Animation animationvisible;
    private Animation animationInvisible;
    private String content = "";//输入的内容

    @Override
    protected int setLayout() {
        return R.layout.activity_beautiful_time_choose;
    }

    @Override
    protected void initView() {
        layout = byView(R.id.time_choose_rl);
        titleEt = byView(R.id.time_choose_title_et);
        titleTv = byView(R.id.time_choose_title_tv);
        recyclerView = byView(R.id.one_recucle_view);
        imageView = byView(R.id.three_iv);
        backIbtn = byView(R.id.three_back_iv_btn);
        okIbtn = byView(R.id.three_ok_iv_btn);
        colorTv = byView(R.id.time_choose_color_tv);
        dateLl = byView(R.id.time_choose_date_ll);
        repictLl = byView(R.id.time_choose_repit_ll);
        colorLl = byView(R.id.time_choose_color_ll);
        datePicker = byView(R.id.time_choose_date_pick);
        dateTv = byView(R.id.three_choese_date);
    }

    @Override
    protected void initData() {
        //判断之前是否有选择过颜色和日期
        int color = SpInstence.getInstence().getNextColor();
        if (color != 0xff000000) {
            colorTv.setBackgroundColor(color);
            this.color = color;
        }
        //获取屏幕宽
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        final int width = mDisplayMetrics.widthPixels;
        final int height = mDisplayMetrics.heightPixels;


        //设置当前系统时间,如果dates为空则返回当前系统是时间
        Long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        dates = format.format(new Date(Long.valueOf(time)));


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);


        smailDatas.add(new SetPictureData(R.mipmap.baby_thumb));
        smailDatas.add(new SetPictureData(R.mipmap.birthday_thumb));
        smailDatas.add(new SetPictureData(R.mipmap.sport_thumb));
        smailDatas.add(new SetPictureData(R.mipmap.love_thumb));
        smailDatas.add(new SetPictureData(R.mipmap.school_thumb));

        adapter = new OneRecyclerViewAdapter(this, smailDatas);
        recyclerView.setAdapter(adapter);

        adapter.setListener(new OneRecyclerViewAdapter.MyOnClickListener() {
            @Override
            public void MyOnClick(int pos) {
                position = pos;
                SensorImg.getSensorImg(BeautifulTimeChooseActivity.this, imageView, picture[pos], width, height);
            }
        });

        backIbtn.setOnClickListener(this);
        okIbtn.setOnClickListener(this);
        colorTv.setOnClickListener(this);
        dateLl.setOnClickListener(this);
        layout.setOnClickListener(this);
        titleTv.setOnClickListener(this);
        animationvisible = AnimationUtils.loadAnimation(this, R.anim.scale_visible);
        animationInvisible = AnimationUtils.loadAnimation(this, R.anim.scale_invisible);
        //注册广播
        myBroadCast = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qiu.beautifulTime.get.color");
        registerReceiver(myBroadCast, intentFilter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three_back_iv_btn:
                BeautifulTimeChooseActivity.this.finish();
                break;
            case R.id.three_ok_iv_btn:
                //存储当前color的状态
                SpInstence.getInstence().putColor(color);
                //往适配器里添加一条数据
                TimeItemRecycleViewAdapter adapter = new TimeItemRecycleViewAdapter(this);
                itemTimeData = new ItemTimeData();
                dataSize = TimeItemRecycleViewAdapter.timeDatas.size();
                dataSize++;
                Long recordTime = System.currentTimeMillis();
                itemTimeData.setDate(chooseDate);
                itemTimeData.setColor(color);
                itemTimeData.setPictureName(picture[position]);
                itemTimeData.setRecordTime(recordTime);
                itemTimeData.setPictureName(content);
                adapter.addItem(0, itemTimeData);
                //存储数据
                OrmInstence.getOrmInstence().addOneData(new ItemTimeData(content, chooseDate, color, picture[position], recordTime));

                //发送到notesfragment
                Intent intents = new Intent("aa");
                intents.putExtra("color", color);
                sendBroadcast(intents);


                Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show();
                BeautifulTimeChooseActivity.this.finish();
                break;
            case R.id.time_choose_color_tv:
                Intent intent = new Intent(BeautifulTimeChooseActivity.this, ColorChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.time_choose_date_ll:
                isVisibilty();

                break;
            case R.id.time_choose_rl:
                if (isVisibility) {
                    MyVisibity();
                }
                break;
            case R.id.time_choose_title_tv:
                if (!isVisibility) {
                    MyInVisibity();
                }
                break;
        }
    }

    private void MyVisibity() {
        titleTv.setVisibility(View.VISIBLE);
        titleEt.setVisibility(View.INVISIBLE);
        titleEt.startAnimation(animationInvisible);
        isVisibility = false;
        /**
         * 获取输入的文字
         */
        getInputText();
    }

    private void MyInVisibity() {
        titleTv.setVisibility(View.INVISIBLE);
        titleEt.setVisibility(View.VISIBLE);
        titleEt.startAnimation(animationvisible);
        isVisibility = true;
    }

    /**
     * 判断项的显示和影藏
     */
    private void isVisibilty() {
        if (isvisibility) {

            datePicker.setVisibility(View.VISIBLE);
            repictLl.setVisibility(View.GONE);
            colorLl.setVisibility(View.GONE);
            isvisibility = false;

            //获取选择的时间
            getChooseDate();

        } else {
            datePicker.setVisibility(View.GONE);
            repictLl.setVisibility(View.VISIBLE);
            colorLl.setVisibility(View.VISIBLE);
            isvisibility = true;
            if (dates.length() > 0) {
                dateTv.setText(dates);
            } else {

//                dateTv.setText(format.format(new Date(Long.valueOf(time))));
            }
        }
    }

    private void getChooseDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int y = Math.abs(year - DateCount.getYear());
                int m = Math.abs((monthOfYear + 1) - DateCount.getMonth());
                int d = Math.abs(dayOfMonth - DateCount.getDay());
                dates = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                chooseDate = y * 365 + m * 30 + d;

            }
        });
    }

    public void getInputText() {
        content = titleEt.getText().toString();
        Toast.makeText(this, "" + content, Toast.LENGTH_SHORT).show();
    }

    class MyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            color = intent.getIntExtra("color", 0x00);
            //改变颜色
            if (color != 0x00) {
                colorTv.setBackgroundColor(color);
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myBroadCast);
        super.onDestroy();
    }
}
