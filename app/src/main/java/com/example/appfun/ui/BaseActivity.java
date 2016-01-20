package com.example.appfun.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.appfun.R;

/**
 * Created by zx on 2016/1/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    protected abstract int getContentView();

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
    }

    //设置头部标题
    public void setBarTitleText(int titleText) {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            mToolbar.setTitle(titleText);
            setSupportActionBar(mToolbar);
            mToolbar.setTitleTextColor(0xffffffff);
            mToolbar.setSubtitleTextColor(0xffffffff);
            mToolbar.setOnClickListener(v -> finish());
            mToolbar.setOnMenuItemClickListener(item -> true);
        }
    }
}
