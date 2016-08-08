package com.example.appfun.base;

/**
 * Created by zx on 2016/8/4.
 */

public interface View {
    void showLoading(int messageRes);

    void showLoading(String message);

    void hideLoading();

    void showActionLabel(boolean success, int messageRes);

    void showActionLabel(boolean success, String message);

    void hideActionLabel();
}
