package com.tesmple.chromeprocessbar.Adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.tesmple.chromeprocessbar.R;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by ESIR on 2015/12/1.
 */
public abstract class HeaderRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 两个参数
     */
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    /**
     * 存储待显示的数据
     */
    private ArrayList<T> mDatas = new ArrayList<>();

    /**
     * recyclerview头部布局
     */
    private View mHeaderView = null;

    /**
     * 监听者
     */
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    /**
     * 设置头部布局
     * @param headerView 传入头部布局
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 获取头部布局
     * @return 返回头部布局，可能为null
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 获得此item的类型
     * @param position 传入类型
     * @return 如果头部view为空则是普通类型，否则判断如果position==0则是头部，返回头部类型
     */
    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;//未设置头部布局
        if(position == 0) return TYPE_HEADER;//检测是否是第一个数据
        return TYPE_NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        return onCreate(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(viewHolder);
        final T data = mDatas.get(pos);
        onBind(viewHolder, pos, data);

        if(mListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });
        }
    }

    /**
     * 判断是否是gridview，是则头部占满一行
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 对于瀑布流，使用setFullSpan设置为占满一行
     * @param holder 传入holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    /**
     * 获取真实位置
     * @param holder 传入holder
     * @return 如果有头部布局则返回位置减1
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getPosition();
        return mHeaderView == null ? position : position - 1;
    }


    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);
    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);


    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
}
