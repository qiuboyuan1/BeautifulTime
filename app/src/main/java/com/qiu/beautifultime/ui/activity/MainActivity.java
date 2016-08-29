package com.qiu.beautifultime.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
        MySendBroadTransactin sendBroadTransactin = new MySendBroadTransactin();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.qiu.beautifultime.mainActivity");
        registerReceiver(sendBroadTransactin, filter);

        fragments = new ArrayList<>();
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        fragments.add(new BeautifulTimeNotesFragment());
        fragments.add(new BeautifulTimeShowFragment());
        fragmentAdapter.setFragments(fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setPageTransformer(true, new FragmentOageTrans());
    }
    public class MySendBroadTransactin extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("skipNotesFragment", 0);

            switch (a) {
                case 0:
                    break;
                case 1:
                    viewPager.setCurrentItem(1);
                    viewPager.setPageTransformer(true, new FragmentOageTrans());
                    break;
                case 2:
                    viewPager.setCurrentItem(0);
                    viewPager.setPageTransformer(true, new FragmentOageTrans());
                    break;
            }

        }
    }
}
