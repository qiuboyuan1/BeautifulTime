package com.qiu.beautifultime.ui.fragment;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ShowPictureData;
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
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        viewPagerAdapter = new ShowViewPagerAdapter(sContext, width);
        //添加图片
        pictureDatas.add(new ShowPictureData("school.jpg"));
        pictureDatas.add(new ShowPictureData("school.jpg"));
        pictureDatas.add(new ShowPictureData("school.jpg"));
        viewPagerAdapter.setPictureDatas(pictureDatas);
        viewPager.getContentDescription();
        viewPager.setAdapter(viewPagerAdapter);

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
}
