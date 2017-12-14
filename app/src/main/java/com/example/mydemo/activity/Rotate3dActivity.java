package com.example.mydemo.activity;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.base.BaseActivity;
import com.example.mydemo.R;
import com.example.mydemo.utils.Rotate3D;

/**
 * Created by 71541 on 2017/12/14.
 */

public class Rotate3dActivity extends BaseActivity{

    private RelativeLayout rlContainer;
    private TextView tv_page1,tv_page2;
    private final int PAGE_LOGIN = 0;
    private final int PAGE_REGISTER = 1;
    private final int DEPTHZ = 500;
    private final int DURATION = 300;
    private int curPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rotate3d;
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rlContainer = (RelativeLayout) findViewById(R.id.rl);
        tv_page1 = (TextView) findViewById(R.id.tv_page1);
        tv_page2 = (TextView) findViewById(R.id.tv_page2);
        rlContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(curPage == PAGE_LOGIN){
                    rotateAnim();
                    curPage = PAGE_REGISTER;
                }else{
                    rotateAnim();
                    curPage = PAGE_LOGIN;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    private void rotateAnim(){
        Rotate3D rotate3D = new Rotate3D(0,90,rlContainer.getWidth()/2,rlContainer.getHeight()/2,DEPTHZ,true);
        rotate3D.setDuration(DURATION);
        rotate3D.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(curPage == PAGE_LOGIN){
                    tv_page1.setVisibility(View.GONE);
                    tv_page2.setVisibility(View.VISIBLE);
                }else{
                    tv_page1.setVisibility(View.VISIBLE);
                    tv_page2.setVisibility(View.GONE);
                }

                Rotate3D rotate3D = new Rotate3D(270,360,rlContainer.getWidth()/2,rlContainer.getHeight()/2,DEPTHZ,false);
                rotate3D.setDuration(DURATION);
                rotate3D.setInterpolator(new DecelerateInterpolator());
                rlContainer.startAnimation(rotate3D);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlContainer.startAnimation(rotate3D);
    }
}
