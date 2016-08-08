package com.example.appfun.ui;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appfun.R;
import com.example.appfun.base.BaseActivity;
import com.example.appfun.bean.HeroDetailsInfo;
import com.example.appfun.dao.DataDetailsService;

import java.util.ArrayList;

/**
 * Created by zx on 2016/1/25.
 */

public class HeroDetailsActivity extends BaseActivity {
    private TextView mHeroStoryTv, mHeroAtkTv, mHeroAgiTv, mHeroIntTv, mHeroStrTv, mHeroSpdTv, mHeroDefTv,
            mHeroAtkSpdTv, mHeroMagicDefTv, mHeroBallTv, mHeroAtkRangeTv, mHeroAliasTv, mHeroViewTv;
    private ImageView mHeroImg;

    @Override
    protected void initView() {
        mHeroStoryTv = findView(R.id.heroStory);
        mHeroImg = findView(R.id.heroImg);
        mHeroAtkTv = findView(R.id.heroAtk);
        mHeroAgiTv = findView(R.id.heroAgi);
        mHeroIntTv = findView(R.id.heroInt);
        mHeroStrTv = findView(R.id.heroStr);
        mHeroSpdTv = findView(R.id.heroSpd);
        mHeroDefTv = findView(R.id.heroDef);
        mHeroMagicDefTv = findView(R.id.heroMagicDef);
        mHeroAliasTv = findView(R.id.heroAlias);
        mHeroAtkRangeTv = findView(R.id.heroAtkRange);
        mHeroBallTv = findView(R.id.heroBall);
        mHeroViewTv = findView(R.id.heroView);
        mHeroAtkSpdTv = findView(R.id.heroAtkSpd);
        ArrayList<String> stringList = getIntent().getStringArrayListExtra("heroInfo");
        setHeroInfo(stringList.get(0));
        setBarTitleText(stringList.get(0));

        Glide.with(getApplicationContext()).load(stringList.get(1)).into(mHeroImg);
        mHeroAtkTv.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), ItemActivity.class)));
    }


    @Override
    protected int getContentView() {
        return R.layout.hero_details_view;
    }


    private void setHeroInfo(String heroName) {
        DataDetailsService heroDetailsService = new DataDetailsService();
        HeroDetailsInfo heroDetailsInfo = heroDetailsService.findHeroInfo(heroName, getApplicationContext());
        mHeroDefTv.setText(heroDetailsInfo.getHero_def() + "");
        mHeroAtkTv.setText(heroDetailsInfo.getHero_atk());
        mHeroStrTv.setText(heroDetailsInfo.getHero_pow() + "+" + heroDetailsInfo.getHero_pow_up());
        mHeroAgiTv.setText(heroDetailsInfo.getHero_agi() + "+" + heroDetailsInfo.getHero_agi_up());
        mHeroIntTv.setText(heroDetailsInfo.getHero_int() + "+" + heroDetailsInfo.getHero_int_up());
        mHeroSpdTv.setText(heroDetailsInfo.getHero_spd() + "");
        mHeroAtkSpdTv.setText(heroDetailsInfo.getHero_atk_spd() + "");
        mHeroAtkRangeTv.setText(heroDetailsInfo.getHero_atk_range() + "");
        if (heroDetailsInfo.getHero_atk_type() == 1) {
            mHeroBallTv.setText("N/A");
        } else
            mHeroBallTv.setText(heroDetailsInfo.getHero_ball() + "");
        mHeroAliasTv.setText(heroDetailsInfo.getHero_alias());
        mHeroMagicDefTv.setText(heroDetailsInfo.getHero_def_res());
        mHeroViewTv.setText(heroDetailsInfo.getHero_view());
        mHeroStoryTv.setText("\u3000\u3000" + heroDetailsInfo.getHero_story());
    }
}
