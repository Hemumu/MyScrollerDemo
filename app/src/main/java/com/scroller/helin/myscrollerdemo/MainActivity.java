package com.scroller.helin.myscrollerdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.scroller.helin.myscrollerdemo.bean.PieData;
import com.scroller.helin.myscrollerdemo.service.MyService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Observers;

/**
 * create by helin
 * 2016/5/26
 */
public class MainActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private Button button;
    private static final String TAG = "MainActivity";
    private Button closeButton;
    private Button bezierBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        button = (Button) findViewById(R.id.btn);
        closeButton = (Button) findViewById(R.id.btn2);
        bezierBtn=(Button)findViewById(R.id.btn3);
        scrollView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        Reflect.on(scrollView).set("mOverflingDistance", 100);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatusBar(MainActivity.this);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                closeStatusBar(MainActivity.this);
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
        bezierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BezierViewActivity.class));
            }
        });
        testRxjava();
    }


    private String tag= "Rxjava";

    private String[] datas ={"a","b","c"};

    public void testRxjava() {





//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
//                .baseUrl("http://gank.io/api/history/content/2/1")
//                .build();
//        MyService service = retrofit.create(MyService.class);


        Observable.just("/images/sss.png").map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {

                return null;
            }
        }).subscribe(new Action1<Bitmap>() {
            public void call(Bitmap bitmap) {

            }
        });

        final PieData[] pie = {};

        Observable.from(pie).flatMap(new Func1<PieData, Observable<String >>() {
            @Override
            public Observable<String> call(PieData pieData) {
                return Observable.from(pieData.getDatas());
            }
        }).subscribe(new Action1<String>() {
            @Override


            public void call(String s) {

            }
        });





        final int  drawRes = R.drawable.arrow;
        final ImageView mImageView = null;

        //显示一张 图片
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable draw = getTheme().getDrawable(drawRes);
                subscriber.onNext(draw);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                mImageView.setImageDrawable(drawable);
            }
        });

    //打印一段话
       Observable.from(datas).subscribe(new Action1<String>() {
           @Override
           public void call(String s) {
               Log.e(tag, s);
           }
       });


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                Log.d(tag, "Item: " + s);
            }

            @Override
            public void onCompleted() {
                Log.d(tag, "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(tag, "Error!");
            }
        };

        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable = Observable.from(words);
        Observable observable2 = Observable.just("Hello", "Hi", "Aloha");

        observable.subscribe(subscriber);

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };
    }

    /**
     * 显示消息中心
     */
    public static void openStatusBar(Context mContext) {
        // 判断系统版本号
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        doInStatusBar(mContext, methodName);
    }

    /**
     * 关闭消息中心
     */
    public static void closeStatusBar(Context mContext) {
        // 判断系统版本号
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        doInStatusBar(mContext, methodName);
    }

    private static void doInStatusBar(Context mContext, String methodName) {
        Object service = mContext.getSystemService("statusbar");
        Reflect.on(service).call(methodName);
    }
}
