package com.scroller.helin.myscrollerdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by helin on 2016/9/27 10:50.
 *
 * 太阳Loding动画
 */

public class SunLodingView extends View {

    /**
     * 太阳画笔
     */
    private Paint mSunPaint;
    /**
     * 太阳半径
     */
    private float radius;
    /**
     * 太阳光芒长度
     */
    private static  int SUNWITH = 30;
    /**
     * 太阳光芒距圆距离
     */
    private static  int SUNMARG = 10;
    /**
     * 太阳光芒旋转角度
     */
    private double offsetSpin = 0;
    /**
     * 偏移量
     */
    private float currentValue;
    private int mWidth;
    private int mHeight;

    public SunLodingView(Context context) {
        super(context);
    }

    public SunLodingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SunLodingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        widthMeasureSpec = heightMeasureSpec = w > h ? MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY)
                : MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        radius = w / 2 * 0.2f;
        SUNWITH=(int) (radius/2);
        SUNMARG=SUNWITH/2;
    }

    private void initPaint() {
        mSunPaint = new Paint();
        mSunPaint.setColor(Color.parseColor("#FFA500"));
        // 抗锯齿
        mSunPaint.setAntiAlias(true);
        mSunPaint.setStrokeWidth(3);
        // 描边模式
        mSunPaint.setStyle(Paint.Style.STROKE);
        setBackgroundColor(Color.parseColor("#ee4433"));
        pos = new float[2];
        tan = new float[2];
    }

    float[] pos;
    float[] tan;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        // 将画布移至中心
        canvas.translate(mWidth/2, mWidth/2);
        Path path = new Path();

        RectF oval = new RectF(-mWidth/2+5,0,mWidth/2-5,mWidth);
        path.addArc(oval,180,90);

        PathMeasure measure = new PathMeasure(path, false);
        boolean isSu = measure.getPosTan(measure.getLength() * currentValue, pos, tan);




//		canvas.drawPath(path,mSunPaint);

        // 绘制太阳光环
        canvas.drawCircle(pos[0], pos[1], radius, mSunPaint);
        // 绘制太阳光芒
        for (int i = 0; i < 360; i += 30) {
            double startX = Math.cos(Math.toRadians(offsetSpin + i)) * (radius + SUNMARG);
            double startY = Math.sin(Math.toRadians(offsetSpin + i)) * (radius + SUNMARG);
            double endX = Math.cos(Math.toRadians(offsetSpin + i)) * (radius + SUNMARG + SUNWITH);
            double endY = Math.sin(Math.toRadians(offsetSpin + i)) * (radius + SUNMARG + SUNWITH);
            canvas.drawLine((float) startX+pos[0], (float) startY+pos[1], (float) endX+pos[0], (float) endY+pos[1], mSunPaint);
//			canvas.drawLine((float) startX, (float) startY, (float) endX, (float) endY, mSunPaint);
        }
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        StartAnim();
    }

    private void StartAnim() {
        final ValueAnimator sunAnim  = ValueAnimator.ofFloat(0,360f);
        sunAnim.setDuration(10 * 1000);
        sunAnim.setRepeatCount(-1);
        sunAnim.setInterpolator(new LinearInterpolator());
        sunAnim.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator va) {
                offsetSpin = Float.parseFloat(va.getAnimatedValue().toString());
                invalidate();
            }
        });


        final ValueAnimator upAnim  = ValueAnimator.ofFloat(0,1);
        upAnim.setDuration(2 * 1000);
        upAnim.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator va) {
                currentValue = Float.parseFloat(va.getAnimatedValue().toString());
                invalidate();
            }
        });
        upAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        sunAnim.start();
        upAnim.start();
    }

}
