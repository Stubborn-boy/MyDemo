package com.example.baselibrary.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 71541 on 2017/9/7.
 */

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    public T mView= null;
    CompositeDisposable mCompositeDisposable = null;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }

    void unSubscribe() {
        if(mCompositeDisposable!=null) {
            mCompositeDisposable.dispose();
        }
    }

    public  void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


}
