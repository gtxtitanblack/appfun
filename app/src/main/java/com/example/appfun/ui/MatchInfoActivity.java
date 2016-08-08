package com.example.appfun.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.appfun.R;
import com.example.appfun.adapter.MatchListAdapter;
import com.example.appfun.base.BaseActivity;
import com.example.appfun.bean.MatchListInfo;
import com.example.appfun.constant.ConstantParms;
import com.example.appfun.factory.SingletonService;
import com.example.appfun.services.MatchService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zx on 2016/3/3.
 */
public class MatchInfoActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private MatchListAdapter mMatchListAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setBarTitleText("最新赛事");
        mRecyclerView = findView(R.id.recycleview);
        //设置布局的样式及动画
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        getMatchInfo();
    }

    private void getMatchInfo() {
        MatchService matchService = SingletonService.create(MatchService.class);
        matchService.getMatchInfo(ConstantParms.steamKey, ConstantParms.parmLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MatchListInfo>() {
                    @Override
                    public void onCompleted() {
                        mRecyclerView.setAdapter(mMatchListAdapter);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final MatchListInfo matchListInfo) {
                        mMatchListAdapter = new MatchListAdapter(getApplicationContext(), matchListInfo.getResult().getLeagues());
                    }
                });
    }


}
