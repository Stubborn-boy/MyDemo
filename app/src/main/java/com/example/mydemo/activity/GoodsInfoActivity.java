package com.example.mydemo.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.utils.MyUtils;
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
    private boolean isExpandDescripe;

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

        testSpannableString();
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

    private void testSpannableString() {
        TextView tv_ForegroundColorSpan = (TextView) findViewById(R.id.tv_ForegroundColorSpan);
        SpannableString spannableString = new SpannableString("我已同意抖音使用协议");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary));
        spannableString.setSpan(foregroundColorSpan,4,spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_ForegroundColorSpan.setText(spannableString);

        TextView tv_BackgroundColorSpan = (TextView) findViewById(R.id.tv_BackgroundColorSpan);
        spannableString = new SpannableString("你看我头像牛逼不？");
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(context.getResources().getColor(R.color.colorAccent));
        spannableString.setSpan(backgroundColorSpan,3,5,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_BackgroundColorSpan.setText(spannableString);

        TextView tv_StrikethroughSpan = (TextView) findViewById(R.id.tv_StrikethroughSpan);
        spannableString = new SpannableString("龙的传人");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_StrikethroughSpan.setText(spannableString);

        TextView tv_UnderlineSpan = (TextView) findViewById(R.id.tv_UnderlineSpan);
        spannableString = new SpannableString("这里是下划线");
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan,3,6,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_UnderlineSpan.setText(spannableString);

        TextView tv_ScaleXSpan = (TextView) findViewById(R.id.tv_ScaleXSpan);
        spannableString = new SpannableString("哈哈你长胖了");
        ScaleXSpan scaleXSpan = new ScaleXSpan(2);
        spannableString.setSpan(scaleXSpan,0,spannableString.length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_ScaleXSpan.setText(spannableString);

        TextView tv_RelativeSizeSpan = (TextView) findViewById(R.id.tv_RelativeSizeSpan);
        spannableString = new SpannableString("我心情忐忑不安七上八下");
        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan3 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan4 = new RelativeSizeSpan(1.8f);
        spannableString.setSpan(sizeSpan1,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan2,1,2,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan3,2,3,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan4,3,4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan3,4,5,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan2,5,6,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan1,6,7,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan2,7,8,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan3,8,9,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan4,9,10,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_RelativeSizeSpan.setText(spannableString);

        TextView tv_SuperscriptSpan = (TextView) findViewById(R.id.tv_SuperscriptSpan);
        spannableString = new SpannableString("刚在北京望京买了套1202m的房子");
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        spannableString.setSpan(superscriptSpan,12,13,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan,12,13,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_SuperscriptSpan.setText(spannableString);

        TextView tv_SubscriptSpan = (TextView) findViewById(R.id.tv_SubscriptSpan);
        spannableString = new SpannableString("水分子化学式为H20");
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        sizeSpan = new RelativeSizeSpan(0.7f);
        spannableString.setSpan(subscriptSpan,8,9,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan,8,9,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_SubscriptSpan.setText(spannableString);

        TextView tv_StyleSpan = (TextView) findViewById(R.id.tv_StyleSpan);
        spannableString = new SpannableString("身正不怕影子歪");
        StyleSpan styleSpanBold = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpanitalic = new StyleSpan(Typeface.ITALIC);
        spannableString.setSpan(styleSpanBold,0,4,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(styleSpanitalic,4,6,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_StyleSpan.setText(spannableString);

        TextView tv_ImageSpan = (TextView) findViewById(R.id.tv_ImageSpan);
        spannableString = new SpannableString("芭芭拉小魔仙 魔法棒");
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_clear);
        drawable.setBounds(0,0,70,70);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan,6,7,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_ImageSpan.setText(spannableString);

        TextView tv_ClickableSpan = (TextView) findViewById(R.id.tv_ClickableSpan);
        spannableString = new SpannableString("哪里不会点哪里，so easy！");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(context,"你戳中我了",Toast.LENGTH_SHORT).show();
            }
        };
        spannableString.setSpan(clickableSpan,5,7,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv_ClickableSpan.setMovementMethod(LinkMovementMethod.getInstance());  //否则点击没反应
        tv_ClickableSpan.setText(spannableString);

        TextView tv_URLSpan = (TextView) findViewById(R.id.tv_URLSpan);
        spannableString = new SpannableString("打电话，发短信，发邮件，打开网页");
        spannableString.setSpan(new URLSpan("tel:10086"), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new URLSpan("smsto:10086"), 4, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new URLSpan("mailto:88888888@qq.com"), 8, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new URLSpan("http://www.jianshu.com"), 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_URLSpan.setMovementMethod(LinkMovementMethod.getInstance());
        tv_URLSpan.setText(spannableString);

        final TextView tv_Expand = (TextView) findViewById(R.id.tv_Expand);
        final String strExpand = "索引 简单的说，索引就像书本的目录，目录可以快速找到所在页数，数据库中索引可以帮助快速找到数据，而不用全表扫描，合适的索引可以大大提高数据库查询的效率。";
        MyUtils.toggleEllipsize(context,
                tv_Expand, 2,
                strExpand,
                "展开全部  ",
                R.color.colorAccent, isExpandDescripe);
        tv_Expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpandDescripe) {
                    isExpandDescripe = false;
                    tv_Expand.setMaxLines(2);// 收起
                } else {
                    isExpandDescripe = true;
                    tv_Expand.setMaxLines(Integer.MAX_VALUE);// 展开
                }
                MyUtils.toggleEllipsize(context,
                        tv_Expand, 2,
                        strExpand,
                        "展开全部  ",
                        R.color.colorAccent, isExpandDescripe);
            }
        });
    }
}
