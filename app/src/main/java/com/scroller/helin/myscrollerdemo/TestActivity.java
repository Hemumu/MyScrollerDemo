package com.scroller.helin.myscrollerdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.scroller.helin.myscrollerdemo.bean.PieData;
import com.scroller.helin.myscrollerdemo.view.TestMeasureView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ImageView mCoinIv;
    private ImageView mLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mCoinIv = (ImageView) findViewById(R.id.coin_iv);
        mLogoIv = (ImageView) findViewById(R.id.wallet_iv);
        ((Button) findViewById(R.id.start_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCoin();
                setWallet();
                Log.e("padding top", mLogoIv.getTop() + "");
            }
        });
        TestMeasureView mview = (TestMeasureView) findViewById(R.id.tes_path);

        ArrayList<PieData> date = new ArrayList<PieData>();

        for (int i = 0; i < 5; i++) {
            PieData da = new PieData("aa", 10 + i*3);
            date.add(da);
        }


        mview.setData(date);

    }


    private void startCoin() {
// 掉落
        Animation animationTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_top_in);

// 旋转
        ThreeDRotateAnimation animation3D = new ThreeDRotateAnimation();
        animation3D.setRepeatCount(10);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(800);
        animationSet.addAnimation(animation3D);
        animationSet.addAnimation(animationTranslate);
        mCoinIv.startAnimation(animationSet);


    }


    private void setWallet() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                // 大概掉落到钱包的上边缘位置的时候，取消ValueAnimator动画，并执行钱包反弹效果
                if (fraction >= 0.75) {
                    valueAnimator.cancel();
                    startWallet();
                }
            }
        });
        valueAnimator.start();
    }


    private void startWallet() {
        // x轴缩放
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mLogoIv, "scaleX", 1, 1.1f, 0.9f, 1);
        objectAnimator1.setDuration(600);
        // y轴缩放
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mLogoIv, "scaleY", 1, 0.75f, 1.25f, 1);
        objectAnimator2.setDuration(600);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        // 同时执行x，y轴缩放动画
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.start();
    }

}
