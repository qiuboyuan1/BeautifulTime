package com.qiu.beautifultime.ui.fragment;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.tools.TimeItemTouchHelperCallback;
import com.qiu.beautifultime.ui.activity.BeautifulTimeChooseActivity;
import com.qiu.beautifultime.ui.activity.SettingActivity;
import com.qiu.beautifultime.ui.adapter.TimeItemRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 进入的第一个界面
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeNotesFragment extends AbsBaseFragment implements View.OnClickListener, TimeItemRecycleViewAdapter.ItemClickListener, TimeItemRecycleViewAdapter.ItemLongClickListener {
    private ImageView setIv;//设置按钮
    private TextView recordTv;////点击记录按钮
    private TextView notesTv;//通知标题
    private ImageView backIv;//背景图片
    private FloatingActionButton faBtn;
    private RecyclerView recordItem;//记录条目
    private TimeItemRecycleViewAdapter itemRecycleViewAdapter;
    private List<ItemTimeData> timeDatas = new ArrayList<>();
    private boolean fabOpend = false;
    private MyBroadCast myBroadCast;

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


        myBroadCast = new MyBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.qiu.beautifultime.notesFragment");
        sContext.registerReceiver(myBroadCast, filter);


        itemRecycleViewAdapter = new TimeItemRecycleViewAdapter(sContext);
        LinearLayoutManager manager = new LinearLayoutManager(sContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recordItem.setLayoutManager(manager);

        //在加载notesfragment的时候发送广播设置数据
        Intent intent = new Intent("com.qiu.beautifultime.notesFragment");
        sContext.sendBroadcast(intent);

        //创建我们的ItemTouchHelper并调用attachToRecyclerView(RecyclerView)
        ItemTouchHelper.Callback callback = new TimeItemTouchHelperCallback(itemRecycleViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recordItem);

        //设置监听
        itemRecycleViewAdapter.setClickListener(this);
        itemRecycleViewAdapter.setLongClickListener(this);
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
                if (!fabOpend) {
                    //点击动画
                    opendAnim();
                } else {
                    closeAnim();
                }
                //跳转
                startActivity(new Intent(sContext, BeautifulTimeChooseActivity.class));
                break;
        }
    }

    private void closeAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(faBtn, "rotation", 135, 0);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        fabOpend = false;
    }

    private void opendAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(faBtn, "rotation", 0, 135);
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        fabOpend = true;
    }

    @Override
    public void itemClick(int pos) {
        //跳转到showfragment页面
        Intent intent = new Intent("com.qiu.beautifultime.mainActivity");
//        Intent intent = new Intent("cccc");
        intent.putExtra("skipNotesFragment", 1);
        sContext.sendBroadcast(intent);
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

    @Override
    public void itemLongClick(int pos) {

    }

    //接受chooseactivity发送的广播
    public class MyBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //每次加载的时候都清空一下集合
            timeDatas.clear();
            //添加数据
            List<ItemTimeData> allDatas = OrmInstence.getOrmInstence().serchAllData(ItemTimeData.class);
            for (int i = 0; i < allDatas.size(); i++) {
                int pos = allDatas.size() - 1 - i;
                timeDatas.add(new ItemTimeData(allDatas.get(pos).getTitle(), allDatas.get(pos).getDate(), allDatas.get(pos).getColor(), allDatas.get(pos).getPictureName(), allDatas.get(pos).getRecordTime()));
            }
            itemRecycleViewAdapter.setTimeDatas(timeDatas);
            recordItem.setAdapter(itemRecycleViewAdapter);
            isRecord();
        }
    }

    @Override
    public void onDestroy() {
        sContext.unregisterReceiver(myBroadCast);
        super.onDestroy();
    }
}
