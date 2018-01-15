package com.example.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselibrary.mvp.BaseContract;
import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by 71541 on 2017/9/5.
 */

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends RxFragment {

    public T mPresenter;
    protected Context context;
    protected Activity activity;
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(getLayoutId(), null, false);
        view.setClickable(true);// 防止点击穿透，底层的fragment响应上层点击触摸事件
        initView(view);
        if(mPresenter!=null) {
            mPresenter.attachView(this);
        }
        initData();
        isViewPrepared = true;
        return view;
    }

    /**
     * 布局
     * @return int
     */
    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();

    /**
     * Fragment数据的懒加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewPrepared && isVisibleToUser) {//getUserVisibleHint()
            lazyData();
        }
    }

    protected void lazyData(){}

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewPrepared = true;
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }
}
