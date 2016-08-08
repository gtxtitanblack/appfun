package com.example.appfun.base;

import android.os.Bundle;

/**
 * Created by zx on 2016/8/3.
 */

public class BasePresenter<V extends View> implements Persenter {

    protected final V mView;

    public BasePresenter(V view) {
        mView = view;
    }

    @Override
    public void saveState(final Bundle outState) {

    }

    @Override
    public void create(final Bundle savedInstanceState) {

    }

    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }
}
