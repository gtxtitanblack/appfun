package com.example.appfun.services;

import com.example.appfun.bean.MatchListInfo;
import com.example.appfun.url.URLConfig;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by zx on 2016/3/3.
 */
public interface MatchService {
    @GET(URLConfig.MATCH_INFO_URL)
    Observable<MatchListInfo> getMatchInfo(@Query("key") String key, @Query("language") String language);
}
