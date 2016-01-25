package com.example.appfun.ui;

import android.content.Intent;
import android.widget.TextView;

import com.example.appfun.R;
import com.example.appfun.bean.HeroDetailsInfo;
import com.example.appfun.dao.HeroDetailsService;

/**
 * Created by zx on 2016/1/25.
 */

public class HeroDetailsActivity extends BaseActivity {
    private TextView heroInfo;

    @Override
    protected void initView() {
        heroInfo = findView(R.id.fun);
        Intent intent = getIntent();
        String heroInfo = intent.getStringExtra("heroInfo");
        setHeroInfo(heroInfo);
    }

    @Override
    protected int getContentView() {
        return R.layout.fun;
    }


    private void setHeroInfo(String heroName) {
        HeroDetailsService heroDetailsService = new HeroDetailsService();
        HeroDetailsInfo heroDetailsInfo = heroDetailsService.findHeroInfo(heroName, getApplicationContext());
    }

}
