package com.example.appfun;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.appfun.adapter.HeroAdapter;
import com.example.appfun.bean.HeroDetailsInfo;
import com.example.appfun.bean.HeroSimpleInfo;
import com.example.appfun.constant.ConstantParms;
import com.example.appfun.dao.DatabaseHelper;
import com.example.appfun.event.ItemListener;
import com.example.appfun.factory.SingletonService;
import com.example.appfun.ui.BaseActivity;
import com.example.appfun.url.URLConfig;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private HeroAdapter mHeroAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mDrawerLayout = findView(R.id.drawer_layout);
        mNavigationView = findView(R.id.id_nv_menu);
        mRecyclerView = findView(R.id.recycleview);
        //设置布局的样式及动画
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        setupDrawerContent(mNavigationView);
        setBarTitleText(R.string.app_name);
        getHero();
        getDb();
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    menuItem.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                });
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
                            Snackbar.make(view, heroInfo.getResult().getHeroes().get(position).getLocalized_name(), Snackbar.LENGTH_LONG).show();
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


    private List<HeroDetailsInfo> getDb() {
        SQLiteDatabase db = DatabaseHelper.openDatabase(this);
        List<HeroDetailsInfo> heroDetailsInfo = new ArrayList<>();
        HeroDetailsInfo heroDetailsInfo1 = new HeroDetailsInfo();
        Cursor cursor = db.rawQuery("select * from heroinfo", null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                Log.d("1", cursor.getString(0));
//                heroDetailsInfo.setHero_int(cursor.getString(0));
//                heroDetailsInfo.setHeri_agi_up(cursor.getString(1));
//                heroDetailsInfo.setHero_int_up(cursor.getString(2));
            }
        }

        cursor.close();
        db.close();
        return heroDetailsInfo;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}