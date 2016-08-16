package com.qiu.beautifultime.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.tools.SensorImg;


import java.util.List;

/**
 * viewpager适配器,展示照片的fragment
 * Created by dllo on 16/8/16.
 */
public class ShowViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ShowPictureData> pictureDatas;
    private ImageView imageView;

    public ShowViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void setPictureDatas(List<ShowPictureData> pictureDatas) {
        this.pictureDatas = pictureDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pictureDatas==null?0:pictureDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= View.inflate(context, R.layout.show_image_item, null);
        imageView = (ImageView) view.findViewById(R.id.show_image_iv);
        SensorImg.getSensorImg(context, imageView, pictureDatas.get(position).getImgName(),920);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
