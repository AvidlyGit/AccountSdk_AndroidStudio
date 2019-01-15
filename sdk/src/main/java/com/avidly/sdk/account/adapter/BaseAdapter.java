package com.avidly.sdk.account.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by sam on 2018/11/4.
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private LayoutInflater mInflater;
    protected onRecyclerViewItemClickListener onItemClick;

    public BaseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public void setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener listener) {
        onItemClick = listener;
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
}
