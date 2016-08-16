package com.qiu.beautifultime.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.tools.DepthPageTransformer;
import com.qiu.beautifultime.tools.FragmentOageTrans;
import com.qiu.beautifultime.ui.adapter.FragmentAdapter;
import com.qiu.beautifultime.ui.fragment.BeautifulTimeNotesFragment;
import com.qiu.beautifultime.ui.fragment.BeautifulTimeShowFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AbsBaseActivity {
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragments;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = byView(R.id.main_vp);

    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragments.add(new BeautifulTimeNotesFragment());
        fragments.add(new BeautifulTimeShowFragment());
        fragmentAdapter.setFragments(fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setPageTransformer(true, new FragmentOageTrans());
    }

}
