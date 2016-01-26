package com.example.appfun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.appfun.R;
import com.example.appfun.adapter.vh.HeroHolder;
import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.event.ItemListener;

import java.util.List;

/**
 * Created by zx on 2016/1/18.
 */
public class HeroAdapter extends RecyclerView.Adapter<HeroHolder> {
    private Context mContext;
    private List<HeroSimpleInfo.ResultEntity.HeroesEntity> mHeroData;

    private ItemListener mItemListener;

    public void setOnItemClickLitener(ItemListener itemClickLitener) {
        this.mItemListener = itemClickLitener;
    }

    public HeroAdapter(Context context, List<HeroSimpleInfo.ResultEntity.HeroesEntity> list) {
        this.mContext = context;
        this.mHeroData = list;
    }

    //给ViewHolder设置布局
    @Override
    public HeroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mHeroView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new HeroHolder(mHeroView, mItemListener);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(final HeroHolder holder, final int position) {
        holder.mTextView.setText(mHeroData.get(position).getLocalized_name());
        Glide.with(mContext).load(mHeroData.get(position).getUrl()).into(holder.mImageView);
        // 如果设置了回调，则设置点击事件
        if (mItemListener != null) {
            holder.itemView.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mItemListener.onItemClick(holder.itemView, pos);
            });
            holder.itemView.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mItemListener.onItemLongClick(holder.itemView, pos);
                removeData(pos);
                return false;
            });
        }
    }

    public void removeData(int position) {
        mHeroData.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return mHeroData == null ? 0 : mHeroData.size();
    }


}
