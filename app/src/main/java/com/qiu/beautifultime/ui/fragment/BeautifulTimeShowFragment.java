package com.qiu.beautifultime.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.tools.DepthPageTransformer;
import com.qiu.beautifultime.tools.ShowAnimation;
import com.qiu.beautifultime.ui.adapter.ShowViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeShowFragment extends AbsBaseFragment {
    private CountDownTimer countDownTimer;
    private ViewPager viewPager;
    private ShowViewPagerAdapter viewPagerAdapter;
    private List<ShowPictureData> pictureDatas = new ArrayList<>();

    private LinearLayout LlEdit,LlDownload,LlDelete,LlNew;
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
        viewPager.setAdapter(viewPagerAdapter);
        //设置切换动画
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        //设置弹出动画
        showAnimation = new ShowAnimation(LlEdit, LlDownload, LlDelete, LlNew);
        viewPagerAdapter.setOnImageViewClickListener(new ShowViewPagerAdapter.OnImageViewClickListener() {

            @Override
            public void OnImageViewListener(final ImageView imageView) {
//                countDownTimer.start();
                showAnimation.ShowView();
                showAnimation.setImageView(imageView);


            }
        });
//        countDownTimer = new CountDownTimer(2000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };
    }




}
