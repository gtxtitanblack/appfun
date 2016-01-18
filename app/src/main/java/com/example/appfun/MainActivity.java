package com.example.appfun;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.appfun.adapter.HeroAdapter;
import com.example.appfun.bean.HeroInfo;
import com.example.appfun.constant.ConstantParms;
import com.example.appfun.event.ItemListener;
import com.example.appfun.factory.SingletonService;
import com.example.appfun.url.URLConfig;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HeroAdapter mHeroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //设置布局的样式及动画
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        getHero();
    }


    private void getHero() {
        HeroService heroService = SingletonService.create(HeroService.class);
        heroService.getHeroData(ConstantParms.steamKey, ConstantParms.parmLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HeroInfo, Observable<HeroInfo.ResultEntity.HeroesEntity>>() {
                    @Override
                    public Observable<HeroInfo.ResultEntity.HeroesEntity> call(HeroInfo heroInfo) {
                        mHeroAdapter = new HeroAdapter(getApplicationContext(), heroInfo.getResult().getHeroes());
                        mHeroAdapter.setOnItemClickLitener(new ItemListener() {
                            @Override
                            public void onItemClick(final View view, final int position) {
                                Snackbar.make(view, heroInfo.getResult().getHeroes().get(position).getLocalized_name(), Snackbar.LENGTH_LONG).show();
                            }

                            @Override
                            public void onItemLongClick(final View view, final int position) {
                                Snackbar.make(view, heroInfo.getResult().getHeroes().get(position).getLocalized_name(), Snackbar.LENGTH_LONG).show();
                            }
                        });
                        mRecyclerView.setAdapter(mHeroAdapter);
                        return Observable.from(heroInfo.getResult().getHeroes());
                    }
                }).flatMap(new Func1<HeroInfo.ResultEntity.HeroesEntity, Observable<String>>() {
            @Override
            public Observable<String> call(final HeroInfo.ResultEntity.HeroesEntity heroesEntity) {
                return getHeroImg(heroesEntity);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(final String imgUrl) {
                System.out.println("图片地址是:" + imgUrl);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(final Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    private static Observable<String> getHeroImg(HeroInfo.ResultEntity.HeroesEntity heroesEntity) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                if (heroesEntity == null) {
                    subscriber.onError(new NullPointerException("英雄数据为空！"));
                    return;
                }
                subscriber.onNext(URLConfig.HERO_IMG_URL + heroesEntity.getName().substring(14) + ConstantParms.largePic);
                subscriber.onCompleted();
            }
        });
    }
}