package com.example.baselibrary.mvp;

/**
 * Created by 71541 on 2017/9/7.
 */

public interface BaseContract {

    interface BaseView {
        /**
         * 请求出错
         */
        void showError(String msg);

        /**
         * 请求完成
         */
        void complete();
    }

    interface BasePresenter<T> {
        /**
         * 绑定
         * @param view view
         */
        void  attachView(T view);

        /**
         * 解绑
         */
        void detachView();
    }
}
