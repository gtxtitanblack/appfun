package com.example.appfun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.appfun.R;
import com.example.appfun.adapter.vh.HeroHolder;
import com.example.appfun.bean.ItemDetailsInfo;
import com.example.appfun.event.ItemListener;

import java.util.List;

/**
 * Created by zx on 2016/2/23.
 */
public class ItemAdapter extends RecyclerView.Adapter<HeroHolder> {
    private Context mContext;
    private List<ItemDetailsInfo> mItemData;

    private ItemListener mItemListener;

    public void setOnItemClickLitener(ItemListener itemClickLitener) {
        this.mItemListener = itemClickLitener;
    }

    public ItemAdapter(Context context, List<ItemDetailsInfo> list) {
        this.mContext = context;
        this.mItemData = list;
    }

    //给ViewHolder设置布局
    @Override
    public HeroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new HeroHolder(view, mItemListener);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(final HeroHolder holder, final int position) {
        holder.mTextView.setText(mItemData.get(position).getItem_name());
        Glide.with(mContext).load(mItemData.get(position).getUrl()).into(holder.mImageView);
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
        mItemData.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return mItemData == null ? 0 : mItemData.size();
    }

}
