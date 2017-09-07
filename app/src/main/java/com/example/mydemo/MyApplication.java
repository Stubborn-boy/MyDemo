package com.example.mydemo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.baselibrary.base.BaseApplication;

/**
 * Created by jack on 2017/5/21.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
