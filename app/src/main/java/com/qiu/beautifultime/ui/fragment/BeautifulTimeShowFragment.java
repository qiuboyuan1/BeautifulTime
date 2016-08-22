package com.qiu.beautifultime.ui.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;


import android.content.Intent;

import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.tools.DepthPageTransformer;
import com.qiu.beautifultime.tools.MyViewPager;
import com.qiu.beautifultime.tools.ShowAnimation;
import com.qiu.beautifultime.ui.activity.BeautifulTimeChooseActivity;
import com.qiu.beautifultime.ui.adapter.ShowViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeShowFragment extends AbsBaseFragment implements View.OnClickListener {

    private MyViewPager viewPager;
    private ShowViewPagerAdapter viewPagerAdapter;
    private List<ShowPictureData> pictureDatas = new ArrayList<>();
    private LinearLayout LlEdit, LlDownload, LlDelete, LlNew;
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
        LlEdit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        myBroadCost = new MyBroadCost();
        IntentFilter filter = new IntentFilter();
        filter.addAction("xxx");
        sContext.registerReceiver(myBroadCost, filter);

        //获取屏幕宽
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;

        viewPagerAdapter = new ShowViewPagerAdapter(sContext, width);
// HEAD
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        //添加图片
        pictureDatas.add(new ShowPictureData("school.jpg"));
        pictureDatas.add(new ShowPictureData("school.jpg"));
        pictureDatas.add(new ShowPictureData("school.jpg"));
        viewPagerAdapter.setPictureDatas(pictureDatas);
        viewPager.getContentDescription();
        viewPager.setAdapter(viewPagerAdapter);
        //feature/修复BUG
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
        showAnimation = new ShowAnimation(LlEdit, LlDownload, LlDelete, LlNew);
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

                break;
            case R.id.LlNew:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        notesDataChange();
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

    public class MyBroadCost extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            notesDataChange();
        }
    }

    private void notesDataChange() {
        pictureDatas.clear();
        List<ItemTimeData> itemTimeDatas = OrmInstence.getOrmInstence().serchAllData(ItemTimeData.class);
        for (int i = 0; i < itemTimeDatas.size(); i++) {
            String name = itemTimeDatas.get(i).getPictureName();
            pictureDatas.add(new ShowPictureData(name));
        }
        viewPagerAdapter.setPictureDatas(pictureDatas);
    }
}
