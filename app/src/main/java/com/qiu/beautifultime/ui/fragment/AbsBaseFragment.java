package com.qiu.beautifultime.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dllo on 16/8/15.
 */
public abstract class AbsBaseFragment extends Fragment {
    protected Context sContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.sContext = context;
    }
    protected abstract int setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();

    protected <T extends View> T byView(int id) {
        T t = (T) getView().findViewById(id);
        return t;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 设置数据
     */
    protected abstract void initData();
}
