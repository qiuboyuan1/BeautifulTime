package com.qiu.beautifultime.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.AllData;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.data.SetPictureData;
import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.tools.SensorImg;
import com.qiu.beautifultime.ui.adapter.OneRecyclerViewAdapter;
import com.qiu.beautifultime.ui.adapter.TimeItemRecycleViewAdapter;

import java.util.ArrayList;
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

    @Override
    protected int setLayout() {
        return R.layout.activity_beautiful_time_choose;
    }

    @Override
    protected void initView() {
        recyclerView = byView(R.id.one_recucle_view);
        imageView = byView(R.id.three_iv);
        backIbtn = byView(R.id.three_back_iv_btn);
        okIbtn = byView(R.id.three_ok_iv_btn);
        colorTv = byView(R.id.time_choose_color_tv);
    }

    @Override
    protected void initData() {
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
                imageView.setImageResource(smailDatas.get(pos).getId());
            }
        });

        backIbtn.setOnClickListener(this);
        okIbtn.setOnClickListener(this);
        colorTv.setOnClickListener(this);
        //默认背景色
        colorTv.setBackgroundColor(Color.rgb(64, 180, 214));
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
                TimeItemRecycleViewAdapter adapter = new TimeItemRecycleViewAdapter(this);
                ItemTimeData timeData = new ItemTimeData();
                dataSize = TimeItemRecycleViewAdapter.timeDatas.size();
                dataSize++;
                timeData.setDays("sss");
                timeData.setColor(color);
                adapter.addItem(0, timeData);
                //存储数据
                OrmInstence.getOrmInstence().addOneData(new AllData("asdasd", System.currentTimeMillis(), color, "asdasdccc"));

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
        }
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
