package com.qiu.beautifultime.ui.fragment;

import android.support.v4.view.ViewPager;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.tools.DepthPageTransformer;
import com.qiu.beautifultime.ui.adapter.ShowViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/8/15.
 */
public class BeautifulTimeShowFragment extends AbsBaseFragment {
    private ViewPager viewPager;
    private ShowViewPagerAdapter viewPagerAdapter;
    private List<ShowPictureData> pictureDatas = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.fragment_beautiful_time_show;
    }

    @Override
    protected void initView() {
        viewPager = byView(R.id.beautiful_time_show_vp);
    }

    @Override
    protected void initData() {
        viewPagerAdapter = new ShowViewPagerAdapter(sContext);
        //添加图片
        pictureDatas.add(new ShowPictureData("school.jpg"));
        pictureDatas.add(new ShowPictureData("school.jpg"));
        viewPagerAdapter.setPictureDatas(pictureDatas);
        viewPager.setAdapter(viewPagerAdapter);
        //设置切换动画
        viewPager.setPageTransformer(true,new DepthPageTransformer());
    }
}
