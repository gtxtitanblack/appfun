package com.example.appfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.appfun.adapter.HeroAdapter;
import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.constant.ConstantParms;
import com.example.appfun.event.ItemListener;
import com.example.appfun.factory.SingletonService;
import com.example.appfun.ui.HeroDetailsActivity;
import com.example.appfun.url.URLConfig;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private HeroAdapter mHeroAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        setupToolBar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //设置布局的样式及动画
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        setupDrawerContent(mNavigationView);
        getHero();
//        getDb(1);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                });
    }

    private void setupToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            mToolbar.setTitle(R.string.app_name);
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(0xffffffff);
            mToolbar.setSubtitleTextColor(0xffffffff);
            mToolbar.setOnClickListener(v -> finish());
            mToolbar.setOnMenuItemClickListener(item -> true);
        }
    }

    private void getHero() {
        HeroService heroService = SingletonService.create(HeroService.class);
        heroService.getHeroData(ConstantParms.steamKey, ConstantParms.parmLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(heroInfo -> {
                    mHeroAdapter = new HeroAdapter(getApplicationContext(), heroInfo.getResult().getHeroes());
                    mHeroAdapter.setOnItemClickLitener(new ItemListener() {
                        @Override
                        public void onItemClick(final View view, final int position) {
                            Intent mIntent = new Intent(getApplicationContext(), HeroDetailsActivity.class);
                            mIntent.putExtra("heroInfo", heroInfo.getResult().getHeroes().get(position).getLocalized_name());
                            startActivity(mIntent);
//                            Snackbar.make(view, heroInfo.getResult().getHeroes().get(position).getLocalized_name(), Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onItemLongClick(final View view, final int position) {
                            Snackbar.make(view, heroInfo.getResult().getHeroes().get(position).getLocalized_name(), Snackbar.LENGTH_LONG).show();
                        }
                    });
                    return Observable.from(heroInfo.getResult().getHeroes());
                }).subscribe(new Subscriber<HeroSimpleInfo.ResultEntity.HeroesEntity>() {
            @Override
            public void onCompleted() {
                mRecyclerView.setAdapter(mHeroAdapter);
            }

            @Override
            public void onError(final Throwable e) {
                Snackbar.make(mRecyclerView, "网络故障，请稍候重试", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(final HeroSimpleInfo.ResultEntity.HeroesEntity heroesEntity) {
                heroesEntity.setUrl(URLConfig.HERO_IMG_URL + heroesEntity.getName().substring(14) + ConstantParms.largePic);
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}