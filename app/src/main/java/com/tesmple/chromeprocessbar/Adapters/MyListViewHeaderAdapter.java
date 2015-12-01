package com.tesmple.chromeprocessbar.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tesmple.chromeprocessbar.R;

/**
 * Created by ESIR on 2015/12/1.
 */
public class MyListViewHeaderAdapter extends MyHeaderAdapter<String> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemofheaderview, parent, false);
        return new MyHolder(layout);
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, String data) {
        if(viewHolder instanceof MyHolder) {
            ((MyHolder) viewHolder).text.setText(data);
        }
    }

    class MyHolder extends MyHeaderAdapter.Holder {
        TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);;
        }
    }
}
