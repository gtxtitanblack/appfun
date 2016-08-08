package com.example.appfun.model;

import com.example.appfun.bean.HeroSimpleInfo;

import rx.Observable;

/**
 * Created by zx on 2016/5/10.
 */
public interface HeroModel {
    /*
    拿到英雄信息
     */
    Observable<HeroSimpleInfo> getHeroName(String steamKey, String parmLanguage);
}
