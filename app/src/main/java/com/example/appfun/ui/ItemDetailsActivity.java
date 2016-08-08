package com.example.appfun.ui;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appfun.R;
import com.example.appfun.base.BaseActivity;
import com.example.appfun.bean.ItemDetailsInfo;
import com.example.appfun.url.URLConfig;

/**
 * Created by zx on 2016/2/23.
 */
public class ItemDetailsActivity extends BaseActivity {

    private ItemDetailsInfo mItemDetailsInfo;
    private ImageView mItemView, mItemCostImg, mItemCdImg;
    private TextView mItemName, mItemInfo, mItemCost, mItemEffect, mItemIntros, mItemCd;

    @Override
    protected int getContentView() {
        return R.layout.item_details_view;
    }

    @Override
    protected void initView() {
        setBarTitleText("物品详情");
        mItemDetailsInfo = (ItemDetailsInfo) getIntent().getSerializableExtra("itemInfo");
        mItemView = findView(R.id.item_img);
        mItemName = findView(R.id.item_name);
        mItemCost = findView(R.id.item_cost);
        mItemEffect = findView(R.id.item_effect);
        mItemIntros = findView(R.id.item_intros);
        mItemInfo = findView(R.id.item_info);
        mItemCostImg = findView(R.id.item_cost_img);
        mItemCd = findView(R.id.item_cd);
        mItemCdImg = findView(R.id.item_cd_img);
        setItemInfo();
        mItemCd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MatchInfoActivity.class));
            finish();
        });
    }

    private void setItemInfo() {
        Glide.with(getApplicationContext()).load(URLConfig.ITEM_COST_IMG_URL).into(mItemCostImg);
        mItemCost.setText(mItemDetailsInfo.getItem_cost());
        mItemIntros.setText(mItemDetailsInfo.getItem_intros());
        mItemEffect.setText(mItemDetailsInfo.getItem_effect());
        mItemName.setText(mItemDetailsInfo.getItem_name());
        mItemInfo.setText(mItemDetailsInfo.getItem_info());
        mItemCd.setText(mItemDetailsInfo.getItem_cd() + "");
        Glide.with(getApplicationContext()).load(URLConfig.CD_IMG_URL).into(mItemCdImg);
        Glide.with(getApplicationContext()).load(mItemDetailsInfo.getUrl()).into(mItemView);
    }
}
