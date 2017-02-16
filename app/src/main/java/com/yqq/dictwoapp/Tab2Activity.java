package com.yqq.dictwoapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Tab2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        youmiAD();
    }

    private void youmiAD() {
        ////////有米嵌入广告
        // 实例化广告条
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        // 获取要嵌入广告条的布局
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
        // 将广告条加入到布局中
        adLayout.addView(adView);
        ///////开发者用来监听广告是否插入成功的
        adView.setAdListener(new AdViewListener() {

            @Override
            public void onSwitchedAd(AdView adView) {
                // 切换广告并展示
            }

            @Override
            public void onReceivedAd(AdView adView) {
                // 请求广告成功
                Log.d("youmi", "请求广告成功");
            }

            @Override
            public void onFailedToReceivedAd(AdView adView) {
                // 请求广告失败
                Log.d("youmi", "请求广告失败");
            }
        });
    }
}