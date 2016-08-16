package com.qiu.beautifultime.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.item.TimeItemTouchHelperCallback;
import com.qiu.beautifultime.ui.activity.BeautifulTimeChooseActivity;
import com.qiu.beautifultime.ui.activity.SettingActivity;
import com.qiu.beautifultime.ui.adapter.TimeItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 进入的第一个界面
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeNotesFragment extends AbsBaseFragment implements View.OnClickListener, TimeItemRecycleViewAdapter.ItemClickListener {
    private ImageView setIv;//设置按钮
    private TextView recordTv;////点击记录按钮
    private TextView notesTv;//通知标题
    private ImageView backIv;//背景图片
    private FloatingActionButton faBtn;
    private RecyclerView recordItem;//记录条目
    private TimeItemRecycleViewAdapter itemRecycleViewAdapter;
    private List<ItemTimeData> timeDatas = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.fragment_beautiful_time_notes;
    }

    @Override
    protected void initView() {
        setIv = byView(R.id.days_icon_set_normal);
        recordTv = byView(R.id.home_record_mine_life_time_tv);
        faBtn = byView(R.id.beautiful_time_notes_float_btn);
        recordItem = byView(R.id.beautiful_time_notes_rv);
        notesTv = byView(R.id.home_see_text_notes);
        backIv = byView(R.id.home_back_empty_iv);

    }

    @Override
    protected void initData() {
        setIv.setOnClickListener(this);
        recordTv.setOnClickListener(this);
        faBtn.setBackgroundColor(Color.rgb(39, 183, 215));
        faBtn.setOnClickListener(this);

        itemRecycleViewAdapter = new TimeItemRecycleViewAdapter(sContext);
        LinearLayoutManager manager = new LinearLayoutManager(sContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recordItem.setLayoutManager(manager);
        //添加数据
        for (int i = 0; i < 20; i++) {
            timeDatas.add(new ItemTimeData("第" + i));
        }
        itemRecycleViewAdapter.setTimeDatas(timeDatas);
        recordItem.setAdapter(itemRecycleViewAdapter);

        //创建我们的ItemTouchHelper并调用attachToRecyclerView(RecyclerView)
        ItemTouchHelper.Callback callback = new TimeItemTouchHelperCallback(itemRecycleViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recordItem);

        //设置监听
        itemRecycleViewAdapter.setClickListener(this);
        //判断当前是否有记录
        isRecord();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.days_icon_set_normal:
                //设置图标
                startActivity(new Intent(sContext, SettingActivity.class));
                break;
            case R.id.home_record_mine_life_time_tv:
                //点击记录


                break;
            case R.id.beautiful_time_notes_float_btn:
                //跳转
                startActivity(new Intent(sContext, BeautifulTimeChooseActivity.class));
                break;
        }
    }

    @Override
    public void itemClick(int pos) {
        Toast.makeText(sContext, "第" + pos, Toast.LENGTH_SHORT).show();
    }

    public void isRecord() {
        if (timeDatas.size() == 0) {
            recordTv.setVisibility(View.VISIBLE);
            notesTv.setVisibility(View.VISIBLE);
            backIv.setVisibility(View.VISIBLE);
        } else if (timeDatas.size() > 0) {
            recordTv.setVisibility(View.GONE);
            notesTv.setVisibility(View.GONE);
            backIv.setVisibility(View.GONE);
        }
    }
}
