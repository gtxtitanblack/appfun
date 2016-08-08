package com.example.appfun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appfun.R;
import com.example.appfun.adapter.HeroAdapter;
import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.constant.ConstantParms;
import com.example.appfun.event.ItemListener;
import com.example.appfun.factory.SingletonService;
import com.example.appfun.services.HeroService;
import com.example.appfun.url.URLConfig;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HeroActivity extends AppCompatActivity {
    @BindView(R.id.recycleview)
    private RecyclerView mRecyclerView;
    @BindView(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;
    @BindView(R.id.id_nv_menu)
    private NavigationView mNavigationView;
    private HeroAdapter mHeroAdapter;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setupToolBar();
        //设置布局的样式及动画
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        setupDrawerContent(mNavigationView);
        getHero();
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
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(0xffffffff);
            toolbar.setSubtitleTextColor(0xffffffff);
            toolbar.setOnClickListener(v -> finish());
            toolbar.setOnMenuItemClickListener(item -> true);
        }
    }

    //拿到英雄数据
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
                            ArrayList<String> heroInfoList = new ArrayList<>();
                            heroInfoList.add(heroInfo.getResult().getHeroes().get(position).getLocalized_name());
                            heroInfoList.add(heroInfo.getResult().getHeroes().get(position).getUrl());
                            Intent mIntent = new Intent(getApplicationContext(), HeroDetailsActivity.class);
                            mIntent.putStringArrayListExtra("heroInfo", heroInfoList);

                            ActivityOptionsCompat options =
                                    ActivityOptionsCompat.makeScaleUpAnimation(view,
                                            view.getWidth() / 2, view.getHeight() / 2,
                                            0, 0);
//                            startActivity(new Intent(getApplicationContext(),HeroActivity.class));
                            ActivityCompat.startActivity(HeroActivity.this, mIntent, options.toBundle());
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
                heroesEntity.setUrl(URLConfig.HERO_IMG_URL + heroesEntity.getName().substring(14) + ConstantParms.fullPic);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(getApplicationContext(), "safsf", Toast.LENGTH_LONG).show();
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}