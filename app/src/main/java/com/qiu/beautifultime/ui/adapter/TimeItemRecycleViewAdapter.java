package com.qiu.beautifultime.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.item.TimeItemTouchHelperCallback;

import java.util.Collections;
import java.util.List;

/**
 * 第一个界面适配器
 * Created by dllo on 16/8/15.
 */
public class TimeItemRecycleViewAdapter extends RecyclerView.Adapter<TimeItemRecycleViewAdapter.MyViewHolder> implements TimeItemTouchHelperCallback.ItemTouchHelperAdapter {
    private Context context;
    public static List<ItemTimeData> timeDatas;
    private ItemClickListener clickListener;

    public TimeItemRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void setTimeDatas(List<ItemTimeData> timeDatas) {
        this.timeDatas = timeDatas;
        notifyDataSetChanged();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 添加item
     * @param position
     * @param data
     */
    public void addItem(int position,ItemTimeData data){
        timeDatas.add(position,data);
        notifyItemInserted(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_time_item_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textView.setText(timeDatas.get(position).getDays());
        if (clickListener != null) {
            holder.progressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    clickListener.itemClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return timeDatas == null ? 0 : timeDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.home_time_item_tv);
            progressBar = (ProgressBar) itemView.findViewById(R.id.home_time_item_progress_bar);
        }
    }

    public interface ItemClickListener {
        void itemClick(int pos);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(timeDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        timeDatas.remove(position);
        notifyDataSetChanged();
    }
}
