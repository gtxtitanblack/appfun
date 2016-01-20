package com.example.appfun;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.example.appfun.adapter.HeroAdapter;
import com.example.appfun.bean.Hero;
import com.example.appfun.bean.HeroInfo;
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
                }).subscribe(new Subscriber<HeroInfo.ResultEntity.HeroesEntity>() {
            @Override
            public void onCompleted() {
                mRecyclerView.setAdapter(mHeroAdapter);
            }

            @Override
            public void onError(final Throwable e) {
                Snackbar.make(mRecyclerView, "网络故障，请稍候重试", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNext(final HeroInfo.ResultEntity.HeroesEntity heroesEntity) {
                heroesEntity.setUrl(URLConfig.HERO_IMG_URL + heroesEntity.getName().substring(14) + ConstantParms.largePic);
            }
        });
    }

    //从assets 文件夹中获取文件并读取数据
    private Hero getDb() {
        List<Object> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.openDatabase(this);
        Hero hero = new Hero();
        Cursor cursor = db.rawQuery("select * from heroinfo where id=1",null);
        if (cursor.moveToFirst()) {
            Integer personid = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("hero_name"));
            hero.setHeroId(personid);
            hero.setHeroName(name);
        }
        cursor.close();
        db.close();
        return hero;
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