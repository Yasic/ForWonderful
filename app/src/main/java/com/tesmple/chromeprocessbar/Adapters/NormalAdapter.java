package com.tesmple.chromeprocessbar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tesmple.chromeprocessbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESIR on 2015/12/2.
 */
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.MyViewHolder> {
    /**
     * 上下文引用
     */
    private Context context;
    /**
     * 数据列表
     */
    private List<String> dataList;

    /**
     * 构造函数
     * @param context 上下文引用
     * @param dataList 数据列表
     */
    public NormalAdapter(Context context, List<String> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_itemofheaderview, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (dataList.size() != 0){
            holder.tvData.setText(dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvData;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvData = (TextView)itemView.findViewById(R.id.text);
        }
    }
}
