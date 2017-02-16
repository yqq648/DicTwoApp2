package com.yqq.dictwoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yqq.dictwoapp.bean.Good;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 单词界面
 */
public class Tab1Activity extends AppCompatActivity {
    private static final String TAG = Tab1Activity.class.getName();
    String responseJson = null;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        setPullToRefresh();//TODO day02
//        okHttp(null);
    }
    /**
     * okHttp联网处理 post,get请求
     * 用于Android和Java应用程序的HTTP和HTTP / 2客户端。
     * https://github.com/square/okhttp
     * http://square.github.io/okhttp/
     * */
    public void okHttp(View view) {
        try {
            run("http://atp.fulishe.com/ClientApi/category.php?returnformat=json&act=search_category_goods_list&c_id=35&page_num=10&page="+page+"&encoding=utf8&api_version=1.0&debug=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * .toString(): This returns your object in string format.
     .string(): This returns your response.
     * */
    OkHttpClient client = new OkHttpClient();//1 client
    void run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();//2 newRequest get请求
        //异步
        client.newCall(request).enqueue(new Callback() {//3 call
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"error:"+e.getMessage());
            }

            @Override//4 success
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,responseJson=response.body().string());
                //加载商品列表到下拉刷新列表当中
                fastJson(null);
            }
        });
    }
    /**
     * fastJson框架的使用
     * 将json字符串转换为java对象   1  JSON.parseObject(jsonStr,Class)
     * 将java对象转换为json字符串   2
     * https://github.com/alibaba/fastjson
     * https://github.com/alibaba/fastjson/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98
     * JSON
     *
     * */
    public void fastJson(View view) {
List<Good> goods = null;
        //将json字符串转换为java对象
com.alibaba.fastjson.JSONObject jsonObject =
        JSON.parseObject(responseJson, com.alibaba.fastjson.JSONObject.class);
JSONArray jsonArray = jsonObject.getJSONObject("info").getJSONArray("goods");
goods = JSON.parseObject(jsonArray.toJSONString(),new TypeReference<List<Good>>() {});
Log.i(TAG,goods.toString());
        //TODO day02
        for (Good good:goods){
            mListItems.add(good.toString());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                // Call onRefreshComplete when the list has been refreshed.
                mPullRefreshGridView.onRefreshComplete();
            }
        });

    }
    PullToRefreshGridView mPullRefreshGridView;
    GridView mGridView;
    LinkedList mListItems;
    ArrayAdapter mAdapter;
    private void setPullToRefresh() {
        mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
        mGridView = mPullRefreshGridView.getRefreshableView();

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

            @Override//uptoDown
            public void refresh(PullToRefreshBase<GridView> refreshView) {
                Toast.makeText(Tab1Activity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
                page = 1;
                mListItems.clear();
                okHttp(null);
            }

            @Override//downToUP
            public void more(PullToRefreshBase<GridView> refreshView) {
                Toast.makeText(Tab1Activity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
                page = page+1;
                okHttp(null);
            }
        });


        mListItems = new LinkedList<String>();

        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("Empty View, Pull Down/Up to Add Items");
        mPullRefreshGridView.setEmptyView(tv);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        mGridView.setAdapter(mAdapter);
    }
}