package com.example.appfun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.appfun.R;
import com.example.appfun.adapter.ItemAdapter;
import com.example.appfun.base.BaseActivity;
import com.example.appfun.bean.ItemDetailsInfo;
import com.example.appfun.dao.DataDetailsService;
import com.example.appfun.event.ItemListener;

import java.util.List;

/**
 * Created by zx on 2016/2/18.
 */
public class ItemActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private ItemAdapter mItemAdapter;

    @Override
    protected int getContentView() {
        return R.layout.recycle_view;
    }

    @Override
    protected void initView() {
        setBarTitleText("物品");
        mDrawerLayout = findView(R.id.drawer_layout);
        mRecyclerView = findView(R.id.recycleview);
        //设置布局的样式及动画
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        getItem();
    }

    private void getItem() {
        DataDetailsService dataDetailsService = new DataDetailsService();
        List<ItemDetailsInfo> itemDetailsInfo = dataDetailsService.getAllItemInfo(getApplicationContext());
        mItemAdapter = new ItemAdapter(getApplicationContext(), itemDetailsInfo);
        mRecyclerView.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickLitener(new ItemListener() {
            @Override
            public void onItemClick(final View view, final int position) {
                Intent mIntent = new Intent(getApplicationContext(), ItemDetailsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("itemInfo",
                        itemDetailsInfo.get(position));
                mIntent.putExtras(mBundle);
                startActivity(mIntent);

            }

            @Override
            public void onItemLongClick(final View view, final int position) {

            }
        });
    }

}
