package com.example.appfun.model.impl;

import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.factory.SingletonService;
import com.example.appfun.model.HeroModel;
import com.example.appfun.services.HeroService;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by zx on 2016/5/10.
 */
public class HeroModelImpl implements HeroModel {
//
//    private HeroOnListenr mHeroOnListenr;
//
//    public HeroModelImpl(HeroOnListenr heroOnListenr) {
//        this.mHeroOnListenr = heroOnListenr;
//    }

    @Override
    public Observable<HeroSimpleInfo> getHeroName(final String steamKey, final String parmLanguage) {
        return Observable.create(subscriber -> {
            HeroService heroService = SingletonService.create(HeroService.class);
            Call<HeroSimpleInfo> call = heroService.getHeroData(steamKey, parmLanguage);
            call.enqueue(new Callback<HeroSimpleInfo>() {
                @Override
                public void onResponse(final Response<HeroSimpleInfo> response, final Retrofit retrofit) {
                    HeroSimpleInfo heroSimpleInfo = response.body();
                    if (heroSimpleInfo != null) {
                        subscriber.onNext(heroSimpleInfo);
                    } else {
                        subscriber.onError(new Exception("数据为空"));
                    }
                    subscriber.onCompleted();
                }

                @Override
                public void onFailure(final Throwable t) {
                    subscriber.onError(t);
                }
            });
        });
    }


//    @Override
//    public Observable<HeroSimpleInfo.ResultEntity> getHeroName(final String steamKey, final String parmLanguage) {
//        HeroService heroService = SingletonService.create(HeroService.class);
//        heroService.getHeroData(steamKey, parmLanguage)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(heroSimpleInfo ->
//                        Observable.from(heroSimpleInfo.getResult().getHeroes()))
//                .subscribe(new Subscriber<HeroSimpleInfo.ResultEntity.HeroesEntity>() {
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onNext(final HeroSimpleInfo.ResultEntity.HeroesEntity heroesEntity) {
//
//                    }
//
//                    @Override
//                    public void onError(final Throwable e) {
//                        e.fillInStackTrace();
//                    }
//                });
//    }
//
//    public interface HeroOnListenr {
//        void onFlatData(HeroSimpleInfo heroSimpleInfo);
//
//        void onSuccess(HeroSimpleInfo.ResultEntity.HeroesEntity s);
//
//        void OnError(Throwable e);
//    }
}
