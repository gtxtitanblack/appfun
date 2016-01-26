package com.example.appfun.ui;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appfun.R;
import com.example.appfun.bean.HeroDetailsInfo;
import com.example.appfun.dao.HeroDetailsService;

import java.util.ArrayList;

/**
 * Created by zx on 2016/1/25.
 */

public class HeroDetailsActivity extends BaseActivity {
    private TextView heroInfo;
    private ImageView mHeroImg;

    @Override
    protected void initView() {
        heroInfo = findView(R.id.fun);
        mHeroImg = findView(R.id.heroImg);
        ArrayList<String> stringList = getIntent().getStringArrayListExtra("heroInfo");
        setHeroInfo(stringList.get(0));
        setBarTitleText(stringList.get(0));
        Glide.with(getApplicationContext()).load(stringList.get(1)).into(mHeroImg);
    }


    @Override
    protected int getContentView() {
        return R.layout.fun;
    }


    private void setHeroInfo(String heroName) {
        HeroDetailsService heroDetailsService = new HeroDetailsService();
        HeroDetailsInfo heroDetailsInfo = heroDetailsService.findHeroInfo(heroName, getApplicationContext());
        System.out.println(heroDetailsInfo.toString());
    }

}
