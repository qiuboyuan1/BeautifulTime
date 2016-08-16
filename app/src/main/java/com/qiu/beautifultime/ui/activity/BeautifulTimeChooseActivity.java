package com.qiu.beautifultime.ui.activity;

import android.content.ClipData;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.data.SetPictureData;
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
    private int dataSize=0;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three_back_iv_btn:
                BeautifulTimeChooseActivity.this.finish();
                break;
            case R.id.three_ok_iv_btn:
                TimeItemRecycleViewAdapter adapter=new TimeItemRecycleViewAdapter(this);
                ItemTimeData timeData=new ItemTimeData();
                dataSize=TimeItemRecycleViewAdapter.timeDatas.size();
                dataSize++;
                timeData.setDays("sss");
                adapter.addItem(0,timeData);
                Toast.makeText(this, "aaaa", Toast.LENGTH_SHORT).show();
                BeautifulTimeChooseActivity.this.finish();
                break;
        }
    }
}
