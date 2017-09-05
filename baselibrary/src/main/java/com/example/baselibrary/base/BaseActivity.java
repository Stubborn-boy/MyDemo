package com.example.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.baselibrary.R;
import com.example.baselibrary.utils.AppManager;
import com.example.baselibrary.utils.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by 71541 on 2017/9/5.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected Context context;
    protected Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = this;
        AppManager.getAppManager().addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);//设置标题前的默认icon是否显示
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置actionbar的向上导航键是否显示
            //toolbar.setNavigationIcon();
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_home);
        }
        initStatusBar();
        initView();
        initData();
    }

    /**
     * 布局文件
     * @return 布局文件
     */
    protected abstract int getLayoutId();
    /**
     * 初始化StatusBar
     */
    void initStatusBar() {
        StatusBarUtil.setColorNoTranslucent((Activity) context, getResources().getColor(R.color.colorPrimary));
    }

    protected abstract void initView();

    protected abstract void initData();

    public void setTitle(int resId){
        getSupportActionBar().setTitle(resId);
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    /**
     * 设置标题居中
     * @param resId
     */
    public void setTitleCenter(int resId){
        toolbarTitle.setText(resId);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    public void setTitleCenter(String title){
        if(title.length()>10){
            toolbarTitle.setText(title.substring(0,10)+"...");
        }
        toolbarTitle.setText(title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 向上键
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
}
