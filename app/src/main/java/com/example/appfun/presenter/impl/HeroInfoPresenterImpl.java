package com.example.appfun.presenter.impl;

import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.presenter.HeroInfoPresenter;
import com.example.appfun.view.HeroView;

/**
 * Created by zx on 2016/5/16.
 */
public class HeroInfoPresenterImpl implements HeroInfoPresenter<HeroView> {
    private HeroSimpleInfo mHeroSimpleInfo;

    public HeroInfoPresenterImpl() {
        this.mHeroSimpleInfo = new HeroSimpleInfo();
    }

    @Override
    public void getHeroInfo(final String steamKey, final String language) {

    }

//
//    @Override
//    public void getHeroInfo(final String steamKey, final String language) {
//        mHeroModel.getHeroName(steamKey, language);
//    }
}
