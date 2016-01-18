package com.example.appfun.adapter.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfun.R;
import com.example.appfun.event.ItemListener;


/**
 * Created by zx on 2016/1/18.
 */
public class HeroHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
    private ItemListener mListener;
    public TextView mTextView;
    public ImageView mImageView;

    public HeroHolder(View view, ItemListener itemListener) {
        super(view);
        this.mListener = itemListener;
        mImageView = (ImageView) view.findViewById(R.id.image);
        mTextView = (TextView) view.findViewById(R.id.text);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }


    @Override
    public boolean onLongClick(final View v) {
        if (mListener != null) {
            mListener.onItemLongClick(v, getPosition());
        }
        return true;
    }

    @Override
    public void onClick(final View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getPosition());
        }
    }
}
