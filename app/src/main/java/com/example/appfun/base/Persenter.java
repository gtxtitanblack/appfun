package com.example.appfun.base;

import android.os.Bundle;

/**
 * Created by zx on 2016/8/4.
 */

public interface Persenter {
    void saveState(Bundle outState);

    void create(Bundle savedInstanceState);

    void start();

    void resume();

    void pause();

    void stop();

    void destroy();
}
