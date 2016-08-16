package com.qiu.beautifultime.item;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 第一个页面的条目帮助类
 * 这个接口可以让你监听“move”与 “swipe”事件。这里还是控制view被选中的状态以及重写默认动画的地方。
 * Created by dllo on 16/8/15.
 */
public class TimeItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private final ItemTouchHelperAdapter mAdapter;

    /**
     * 添加一个构造函数以及一个引用adapter的成员变量：
     * @param mAdapter
     */
    public TimeItemTouchHelperCallback(ItemTouchHelperAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    /**
     * 指定可以支持的拖放和滑动的方向
     * 我们启用了上下左右两种方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //左右滑动
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 重写剩下的事件同时通知adapter
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    /**
     * 重写剩下的事件同时通知adapter
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * ItemTouchHelper可以用于没有滑动的拖动操作
     * 要支持长按RecyclerView item进入拖动操作，你必须在isLongPressDragEnabled()方法中返回true。
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * view任意位置触摸事件发生时启用滑动操作
     * 直接在sItemViewSwipeEnabled()中返回true
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * onMove()和onSwiped()，用于通知底层数据的更新
     * 创建一个可以将这些回调方法传递出去的接口。
     */
    public interface ItemTouchHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

}
