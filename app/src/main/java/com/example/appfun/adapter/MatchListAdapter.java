package com.example.appfun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfun.R;
import com.example.appfun.adapter.vh.MatchHolder;
import com.example.appfun.bean.MatchListInfo;
import com.example.appfun.event.ItemListener;

import java.util.List;

/**
 * Created by zx on 2016/3/3.
 */
public class MatchListAdapter extends RecyclerView.Adapter<MatchHolder> {
    private Context mContext;
    private List<MatchListInfo.ResultEntity.LeaguesEntity> mMatchData;

    private ItemListener mItemListener;

    public void setOnItemClickLitener(ItemListener itemClickLitener) {
        this.mItemListener = itemClickLitener;
    }

    public MatchListAdapter(Context context, List<MatchListInfo.ResultEntity.LeaguesEntity> list) {
        this.mContext = context;
        this.mMatchData = list;
    }

    //给ViewHolder设置布局
    @Override
    public MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item, parent, false);
        return new MatchHolder(view, mItemListener);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(final MatchHolder holder, final int position) {
        holder.mMatchTitle.setText(mMatchData.get(position).getName());
        holder.mMatchInfo.setText(mMatchData.get(position).getDescription());
        holder.mMatchTitle.setText("\u3000\u3000" + mMatchData.get(position).getTournament_url());
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
        mMatchData.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return 10;
    }


}
