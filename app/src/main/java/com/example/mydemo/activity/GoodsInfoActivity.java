package com.example.mydemo.activity;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.baselibrary.base.BaseActivity;
import com.example.mydemo.R;
import com.example.mydemo.view.SlideDetailsLayout;

/**
 * Created by 71541 on 2017/12/15.
 * https://juejin.im/entry/587ec0985c497d0058ba05f5
 */

public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {

    private SlideDetailsLayout svSwitch;
    private ScrollView sv_goods_info;
    private LinearLayout llPullUp;
    private FloatingActionButton fabUpSlide;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected void initView() {
        svSwitch = (SlideDetailsLayout) findViewById(R.id.sv_switch);
        sv_goods_info = (ScrollView) findViewById(R.id.sv_goods_info);
        llPullUp = (LinearLayout) findViewById(R.id.ll_pull_up);
        fabUpSlide = (FloatingActionButton) findViewById(R.id.fab_up_slide);

        llPullUp.setOnClickListener(this);
        fabUpSlide.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_pull_up:
                //上拉查看图文详情
                svSwitch.smoothOpen(true);
                break;
            case R.id.fab_up_slide:
                //点击滑动到顶部
                sv_goods_info.smoothScrollTo(0, 0);
                svSwitch.smoothClose(true);
                break;
        }
    }
}
