package com.example.mydemo;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.baselibrary.base.BaseApplication;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by jack on 2017/5/21.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(this, null);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
