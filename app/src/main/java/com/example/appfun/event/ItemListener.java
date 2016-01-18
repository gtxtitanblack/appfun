package com.example.appfun.event;

import android.view.View;

/**
 * Created by zx on 2016/1/18.
 */
public interface ItemListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
