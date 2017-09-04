package com.example.baselibrary;

import android.app.Application;

import com.example.baselibrary.utils.LogUtils;
import com.example.baselibrary.utils.SPUtils;
import com.example.baselibrary.utils.ToastUtils;


/**
 * Created by jack on 2017/5/21.
 */

public class BaseApplication extends Application {
    public static LogUtils.Builder lBuilder;
    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(this);
        ToastUtils.init(this);
        lBuilder = new LogUtils.Builder(this)
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("CMJ")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.E);// log过滤器，和logcat过滤器同理，默认Verbose
    }
}
