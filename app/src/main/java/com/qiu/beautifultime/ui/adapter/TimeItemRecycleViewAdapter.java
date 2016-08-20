package com.qiu.beautifultime.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qiu.beautifultime.R;
import com.qiu.beautifultime.data.ItemTimeData;
import com.qiu.beautifultime.db.OrmInstence;
import com.qiu.beautifultime.item.TimeItemTouchHelperCallback;
import com.qiu.beautifultime.tools.MProgressView;
import com.qiu.beautifultime.ui.fragment.BeautifulTimeNotesFragment;

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
    private ItemLongClickListener longClickListener;

    public void setLongClickListener(ItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

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
     *
     * @param position
     * @param data
     */
    public void addItem(int position, ItemTimeData data) {
        timeDatas.add(position, data);
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
        holder.textView.setText(String.valueOf(timeDatas.get(position).getDate()));
        if (timeDatas.get(position).getDate() > 100) {
            holder.progressBar.setProgress(timeDatas.get(position).getDate());
        } else {
            holder.progressBar.setProgress(100);
        }
        holder.progressBar.setProgressColor(timeDatas.get(position).getColor());
        if (clickListener != null) {
            holder.progressBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    clickListener.itemClick(pos);
                }
            });
        }
        if (longClickListener != null) {
            holder.progressBar.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    longClickListener.itemLongClick(pos);
                    //长点击动画
                    ObjectAnimator animator = ObjectAnimator.ofFloat(holder.progressBar, "translationX", 0, -10, 10, -8, 8, -6, 6, -4, 4, -2, 2, 0);
                    animator.setDuration(200);
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                    return true;
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
        private MProgressView progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.home_time_item_tv);
            progressBar = (MProgressView) itemView.findViewById(R.id.home_time_item_progress_bar);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(timeDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        OrmInstence.getOrmInstence().delValueData(ItemTimeData.class, "date", String.valueOf(timeDatas.get(position).getDate()));
        //通知show界面数据变化
        Intent intent=new Intent("xxx");
        context.sendBroadcast(intent);

        timeDatas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 点击事件
     */
    public interface ItemClickListener {
        void itemClick(int pos);
    }

    /**
     * 长点击事件
     */
    public interface ItemLongClickListener {
        void itemLongClick(int pos);
    }
}
