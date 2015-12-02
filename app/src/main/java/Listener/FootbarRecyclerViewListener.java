package Listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by ESIR on 2015/12/2.
 */
public abstract class FootbarRecyclerViewListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy){
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0){//down
            onHide();
        }
        else if (dy < -0){//up
            onShow();
        }
    }

    public abstract void onHide();
    public abstract void onShow();
}
