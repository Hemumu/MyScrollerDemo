package com.scroller.helin.myscrollerdemo;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * create by helin
 * 2016/5/26
 */
public class MainActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private Button button;
    private static final String TAG = "MainActivity";
    private Button closeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        button = (Button) findViewById(R.id.btn);
        closeButton = (Button) findViewById(R.id.btn2);
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
                closeStatusBar(MainActivity.this);
            }
        });
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
