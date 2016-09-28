package com.scroller.helin.myscrollerdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by helin on 2016/9/14 15:31.
 */
public class RingProgressBar extends View {
    private int mWidth;
    private int mHeight;
    //内实心圆
    private Paint mPaint;
    //外圆环
    private Paint mCircPaint;

    //半径
    private int radius=200;

    private float mAngle=0;

    public RingProgressBar(Context context) {
        super(context);
    }

    public RingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        startAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#0082D7"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mCircPaint = new Paint();
        mCircPaint.setColor(Color.parseColor("#6682D7"));
        mCircPaint.setAntiAlias(true);
        mCircPaint.setStrokeWidth(10);
        mCircPaint.setStyle(Paint.Style.STROKE);
    }

    private void startAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAngle=Float.parseFloat(animation.getAnimatedValue().toString());
                invalidate();
            }
        });
        animator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        float r = (float) (radius*0.8);
        RectF ret = new RectF(-r,-r,r,r);

        canvas.drawArc(ret,45,mAngle*360,true,mPaint);
        canvas.drawCircle(0, 0, radius, mCircPaint);


    }
}
