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
    EditText tab2_et;
    TextView tab2_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        tab2_et = (EditText) findViewById(R.id.tab2_et);
        tab2_tv = (TextView) findViewById(R.id.tab2_tv);
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

    public void search(View view) {
        String url = "http://fanyi.youdao.com/openapi.do?keyfrom=gaomaite&key=1704124207&type=data&doctype=xml&version=1.1&q=";
        String word = tab2_et.getText().toString();
        final ProgressDialog pd = ProgressDialog.show(this, "查询中","请稍等");
        pd.show();
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {
                try {
                    return parserXML(params[0]+URLEncoder.encode(params[1],"utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                pd.dismiss();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(Tab2Activity.this, "翻译出错",Toast.LENGTH_LONG).show();
                    return;
                }
                tab2_tv.setText(s);
            }
        }.execute(url,word);
    }

    private String parserXML(String wordUrlStr) throws Exception {
        int errorCode = -1;
        String translation = null;
        URL url = new URL(wordUrlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn.getResponseCode()!=HttpURLConnection.HTTP_OK){
            throw new RuntimeException("服务器错误，错误代码为："+conn.getResponseCode());
        }
        /////////////////////////////////////////////////////
        InputStream in = conn.getInputStream();
        org.xmlpull.v1.XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(in,null);//设置数据源
        int event = parser.getEventType();//文档开始
        while (event!=XmlPullParser.END_DOCUMENT){
            if (event==XmlPullParser.START_TAG){
                String tag = parser.getName();//null
                if ("errorCode".equals(tag)){
                    event = parser.next();
                    errorCode = Integer.valueOf(parser.getText());
                }
            }else if (event == XmlPullParser.CDSECT){
                translation = parser.getText();
            }
//            event = parser.next();//此方法只能拿到五种事件标记，
            event = parser.nextToken();//可以获取注释、CDATA类型的数据
        }
        if (errorCode!=0){
            throw new RuntimeException("翻译出错，错误代码："+errorCode);
        }
        return translation;
    }
}