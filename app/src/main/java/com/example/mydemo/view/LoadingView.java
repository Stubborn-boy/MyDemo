package com.example.mydemo.view;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by http://www.jianshu.com/p/80de9137a486
 *
 */

public class LoadingView extends View {
    private Paint mPaint;

    private float left = -100;
    private float top = -100;
    private float right = 100;
    private float bottom = 100;

    private float startAngle = 90;
    private float sweepAngle = -100;

    private int mViewWidthHalf, mViewHeightHalf;        // 获取view的宽高的一半

    private Path arcSrcPath;
    private Path arcDstPath;

    private int startColor = 0xFF10D2DE;
    private int endColor = 0xFF1039DD;
    private int progessColor = startColor;

    private float progessWidth;
    private float startWidth = 70;
    private float endWidth = 5;
    private RectF rectF;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 提供setter方法，使用ObjectAnimator改变对应的属性值
    public void setProgessColor(int progessColor) {
        this.progessColor = progessColor;
        invalidate();
    }
    public void setProgessWidth(float progessWidth) {
        this.progessWidth = progessWidth;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();                       // 当属性值改变时，调用invalidate出发onDraw回调
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidthHalf = w / 2;             // 在onSizeChanged中获取view的宽高值
        mViewHeightHalf = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);              // 绘制背景
        canvas.save();
        canvas.translate(mViewWidthHalf, mViewHeightHalf);      // 将绘制中心坐标(0,0)移动到View中心

        arcSrcPath = new Path();                    // 每次都绘制新的path
        arcDstPath = new Path();

        arcSrcPath.addArc(rectF, startAngle, sweepAngle);
        mPaint.setStrokeWidth(startWidth);
        mPaint.getFillPath(arcSrcPath, arcDstPath);     // 获取实际弧线path的轮廓
        canvas.clipPath(arcDstPath);                    // 只显示轮廓部分，如果不这么做，会使得初始弧线过宽

        mPaint.setColor(progessColor);
        mPaint.setStrokeWidth(progessWidth);            // 动态改变paint的颜色跟线宽
        canvas.drawPath(arcDstPath, mPaint);

        canvas.restore();
    }

    private void init() {
        //关闭硬件加速 https://github.com/GcsSloop/AndroidNote/issues/7
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        initPaint();
        initRect();
        initAnimator();
    }

    private void initAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2200);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取到当前动画的完成度，取值为0~1
                float animatedFraction = animation.getAnimatedFraction();
                // 用来计算线条宽度的变化，前动画的前0.2不改变初始线条宽度，从0.2~0.9，动态的将线条宽度变为最终的宽的endWidth
                // 0.9~1阶段，宽度始终为最终宽度endWidth
                progessWidth = startWidth - ((animatedFraction - 0.2f) / 0.7f) * startWidth;
                // 前0.2的变化会进入这个判断
                if (progessWidth >= startWidth) {
                    progessWidth = startWidth;
                }
                // 0.9~1时会进这个判断
                if (progessWidth <= endWidth) {
                    progessWidth = endWidth;
                }
                // 用来计算弧线长度的变化，前0.7的变化为startAngle从50度逐渐变化为450，sweepAngle固定不变，即为固定的弧长旋转动画
                if (animatedFraction <= 0.7f) {
                    startAngle = 50 + animatedFraction / 0.7f * 400f;
                    sweepAngle = -100;
                }
                // 从0.7~1的动画为startAngle不变，sweepAngle逐渐减小再增大的过程(这个是绘制长度的减小，符号代表绘制圆弧的方向)
                // 即从-100变到20，从而达到长度开始缩小，并在最后延长一部分的动画
                if (animatedFraction >= 0.7f) {
                    startAngle = 450;
                    sweepAngle = -100 + ((animatedFraction - 0.7f) / 0.25f) * 100;
                }
                invalidate();     // 别忘了invalidate触发重绘onDraw
            }
        });

        // 动态改变颜色
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progessColor", startColor, endColor);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.setDuration(2200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator, objectAnimator);
        animatorSet.start();
    }

    private void initRect() {
        rectF = new RectF(left, top, right, bottom);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);      // 线端点处设置会圆头
        mPaint.setStrokeWidth(startWidth);
        mPaint.setColor(startColor);
    }
}
