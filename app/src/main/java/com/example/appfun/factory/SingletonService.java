package com.example.appfun.factory;

import android.os.Environment;

import com.example.appfun.url.URLConfig;
import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


/**
 * Created by zx on 2016/1/15.
 */

public class SingletonService {
    private static Gson gson = new Gson();
    private static Retrofit retrofit;
    private static WeakHashMap<String, Object> cache = new WeakHashMap<>();

    static {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(3, TimeUnit.SECONDS);
        okHttpClient.setCache(new Cache(Environment.getDownloadCacheDirectory(), 20));
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URLConfig.BASE_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static <T> T create(Class<T> service) {
        T t = (T) cache.get(service.getSimpleName());
        if (null == t) {
            t = retrofit.create(service);
            cache.put(service.getSimpleName(), t);
        }
        return t;
    }
}
