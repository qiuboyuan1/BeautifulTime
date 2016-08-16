package com.qiu.beautifultime.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.SetPictureData;

import java.util.List;

/**
 * 设置,选择照片界面的选择照片列表
 * Created by dllo on 16/8/13.
 */
public class OneRecyclerViewAdapter extends RecyclerView.Adapter<OneRecyclerViewAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<SetPictureData> Datas;
    private MyOnClickListener listener;

    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    public OneRecyclerViewAdapter(Context context, List<SetPictureData> datas) {
        inflater = LayoutInflater.from(context);
        Datas = datas;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.set_picture_recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SetPictureData smailData = Datas.get(position);
        holder.imageView.setImageResource(smailData.getId());
        if (listener != null) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.MyOnClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return Datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.one_item_iv);
        }
    }

    public interface MyOnClickListener {
        void MyOnClick(int pos);
    }
}
