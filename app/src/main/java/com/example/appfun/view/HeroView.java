package com.example.appfun.view;

import com.example.appfun.base.BaseModel;
import com.example.appfun.base.BaseView;

/**
 * Created by zx on 2016/5/16.
 */
public interface HeroView {

    interface Model extends BaseModel {
//        Observable createComment(String content, Pointer article, Pointer user);
    }


    interface View extends BaseView {
        void showError();

        void showSuccess();
    }

//    abstract class Presenter extends BasePresenter<Model, View> {
//        public abstract void createComment(String content, Image article, _User user);
//
//        @Override
//        public void onStart() {
//        }
//    }

}
