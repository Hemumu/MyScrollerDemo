package com.scroller.helin.myscrollerdemo;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by helin on 2016/9/5 16:40.
 */
public class ThreeDRotateAnimation extends Animation{

    private Camera camera = new Camera();
    int centerX, centerY;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //中心点坐标
        centerX=width/2;
        centerY=height/2;
        setDuration(500);
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        camera.save();
        //绕Y轴旋转
        camera.rotateY(360 * interpolatedTime);
        camera.getMatrix(matrix);
        //设置翻转中心点
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();
    }
}
