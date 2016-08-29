package com.qiu.beautifultime.ui.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;


import android.content.Intent;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.tools.DepthPageTransformer;
import com.qiu.beautifultime.tools.MyNumberPicker;
import com.qiu.beautifultime.tools.MyViewPager;
import com.qiu.beautifultime.tools.ShowAnimation;
import com.qiu.beautifultime.ui.activity.BeautifulTimeChooseActivity;
import com.qiu.beautifultime.ui.adapter.ShowViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeShowFragment extends AbsBaseFragment implements View.OnClickListener {

    private MyViewPager viewPager;
    private ShowViewPagerAdapter viewPagerAdapter;
    private List<ShowPictureData> pictureDatas = new ArrayList<>();
    private LinearLayout LlEdit, LlDownload, LlDelete, LlNew, llReturn;
    private ShowAnimation showAnimation;
    private MyBroadCost myBroadCost;


    @Override
    protected int setLayout() {
        return R.layout.fragment_beautiful_time_show;
    }

    @Override
    protected void initView() {

        viewPager = byView(R.id.beautiful_time_show_vp);
        LlEdit = byView(R.id.LlEdit);
        LlDownload = byView(R.id.LlDownload);
        LlDelete = byView(R.id.LlDelete);
        LlNew = byView(R.id.LlNew);
        llReturn = byView(R.id.llReturn);
        llReturn.setOnClickListener(this);
        LlEdit.setOnClickListener(this);
        LlDelete.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        myBroadCost = new MyBroadCost();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.qiu.beautifultime.showFragment");
        sContext.registerReceiver(myBroadCost, filter);

        //获取屏幕宽
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        final int width = mDisplayMetrics.widthPixels;
        final int height = mDisplayMetrics.heightPixels;
        viewPagerAdapter = new ShowViewPagerAdapter(sContext, width, height);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        //设置切换动画
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BeautifulTimeShowFragment.this.position = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置弹出编辑动画
        showAnimation = new ShowAnimation(LlEdit, LlDownload, LlDelete, LlNew, width);
        showAnimation.setMyViewPager(viewPager);
        viewPagerAdapter.setOnImageViewClickListener(new ShowViewPagerAdapter.OnImageViewClickListener() {

            @Override
            public void OnImageViewListener(final ImageView imageView) {

                if (imageView.getContentDescription().equals(position + "")) {

                    showAnimation.setImageView(imageView);

                    showAnimation.ShowView();
                }
            }
        });

    }


    private int position;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LlEdit:
                Intent intent = new Intent(sContext, BeautifulTimeChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.LlDownload:
                break;
            case R.id.LlDelete:
                List<ItemTimeData> itemTimeDatas = OrmInstence.getOrmInstence().serchAllData(ItemTimeData.class);
                long data = itemTimeDatas.get(position).getRecordTime();
                Log.d("BeautifulTimeShowFragme", "" + data + "-----" + position);
                OrmInstence.getOrmInstence().delValueData(ItemTimeData.class, "recordTime", data);
                //通知show界面数据变化
                Intent intent2 = new Intent("com.qiu.beautifultime.showFragment");
                sContext.sendBroadcast(intent2);
                //通知notes界面数据变化
                Intent notesIntent = new Intent("com.qiu.beautifultime.notesFragment");
                sContext.sendBroadcast(notesIntent);

                showAnimation.ShowView();
                break;
            case R.id.LlNew:
                break;
            case R.id.llReturn:
                Intent intent1 = new Intent("com.qiu.beautifultime.mainActivity");
                intent1.putExtra("skipNotesFragment", 2);
                sContext.sendBroadcast(intent1);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //通知show界面数据变化
        Intent intent = new Intent("com.qiu.beautifultime.showFragment");
        sContext.sendBroadcast(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        pictureDatas.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sContext.unregisterReceiver(myBroadCost);
    }

    /**
     * 接收设置图片的设置
     */
    public class MyBroadCost extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            notesDataChange(intent);
        }
    }

    private void notesDataChange(Intent intent) {
        int pos = intent.getIntExtra("itemPos", -1);
        pictureDatas.clear();
        List<ItemTimeData> itemTimeDatas = OrmInstence.getOrmInstence().serchAllData(ItemTimeData.class);
        int dataSize = itemTimeDatas.size();
        for (int i = 0; i < dataSize; i++) {
            String name = itemTimeDatas.get(i).getPictureName();
            pictureDatas.add(0, new ShowPictureData(name));
        }
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.setPictureDatas(pictureDatas);
        if (pos >= 0) {
            viewPager.setCurrentItem(pos);
        }
        viewPagerAdapter.notifyDataSetChanged();
    }
}
