package com.example.appfun.services;

import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.url.URLConfig;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by zx on 2016/1/15.
 */
public interface HeroService {
    @GET(URLConfig.HERO_URL)
    Observable<HeroSimpleInfo> getHeroData(@Query("key") String key, @Query("language") String language);
}
