package com.qiu.beautifultime.ui.adapter;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ShowPictureData;
import com.qiu.beautifultime.tools.MyNumberPicker;
import com.qiu.beautifultime.tools.SensorImg;


import java.lang.reflect.Field;
import java.util.List;

/**
 * viewpager适配器,展示照片的fragment
 * Created by dllo on 16/8/16.
 */
public class ShowViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ShowPictureData> pictureDatas;
    private boolean asd = true;

    private int height;
    private int width;
    private OnImageViewClickListener onImageViewClickListener;

    public void setOnImageViewClickListener(OnImageViewClickListener onImageViewClickListener) {
        this.onImageViewClickListener = onImageViewClickListener;
    }

    public ShowViewPagerAdapter(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
    }

    public void setPictureDatas(List<ShowPictureData> pictureDatas) {
        this.pictureDatas = pictureDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pictureDatas == null ? 0 : pictureDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.show_image_item, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.show_image_iv);
        MyNumberPicker myNumberPicker = (MyNumberPicker) view.findViewById(R.id.show_item_np);
        myNumberPicker.setMinValue(10);
        myNumberPicker.setMaxValue(20);
        myNumberPicker.setValue(1);
        myNumberPicker.setWrapSelectorWheel(false);
        setNumberPickerDividerColor(myNumberPicker, 0x1111);
        imageView.setContentDescription(position + "");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageViewClickListener.OnImageViewListener(imageView);

            }
        });
        SensorImg.getSensorImg(context, imageView, pictureDatas.get(position).getImgName(), width,height);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //内部接口
    public interface OnImageViewClickListener {
        void OnImageViewListener(ImageView imageView);
    }
    /**
     * 让选择器分隔线取消
     * @param numberPicker
     * @param color
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field SelectionDividerField : pickerFields) {
            if (SelectionDividerField.getName().equals("mSelectionDivider")) {
                SelectionDividerField.setAccessible(true);
                try {
                    SelectionDividerField.set(numberPicker, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
