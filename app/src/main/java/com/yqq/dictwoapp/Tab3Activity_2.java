package com.yqq.dictwoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 检查更新
 */
public class Tab3Activity_2 extends AppCompatActivity {
    private static final String TAG = Tab3Activity_2.class.getName();
    public static String baiduApkJpg = "http://shouji.baidu.com/software/item?docid=7998398&from=as";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_activity_2);
        setTitle(getClass().getName());
        WebView tab3_2_wv = (WebView) findViewById(R.id.tab3_2_wv);
        tab3_2_wv.getSettings().setJavaScriptEnabled(true);//脚本可以用
        tab3_2_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(".apk")) {
                    String apkStr = url.split("\\?")[0];
                    download(apkStr);
                    return true;
                }
                view.loadUrl(url);
                Log.d(TAG, url);
                return true;
            }
        });
        tab3_2_wv.loadUrl(baiduApkJpg);
    }

    private void download(String apkStr) {
        //加载一个大文件的话， 最好不要使用AlertDialog等对话框
        startService(new Intent(this, MyDownLoadService.class)
                        .putExtra("urlStr",apkStr));
    }
}
