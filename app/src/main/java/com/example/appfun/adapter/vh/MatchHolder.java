package com.example.appfun.adapter.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.appfun.R;
import com.example.appfun.event.ItemListener;

/**
 * Created by zx on 2016/3/3.
 */
public class MatchHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private ItemListener mListener;
    public TextView mMatchTitle, mMatchInfo, mMatchUrl;

    public MatchHolder(View view, ItemListener itemListener) {
        super(view);
        this.mListener = itemListener;
        mMatchTitle = (TextView) view.findViewById(R.id.match_title);
        mMatchInfo = (TextView) view.findViewById(R.id.match_info);
        mMatchUrl = (TextView) view.findViewById(R.id.match_url);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }


    @Override
    public boolean onLongClick(final View v) {
        if (mListener != null) {
            mListener.onItemLongClick(v, getAdapterPosition());
        }
        return true;
    }

    @Override
    public void onClick(final View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getAdapterPosition());
        }
    }

}
