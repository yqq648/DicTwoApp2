package com.yqq.dictwoapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
    }
    /**
     * okHttp联网处理 post,get请求
     * 用于Android和Java应用程序的HTTP和HTTP / 2客户端。
     * https://github.com/square/okhttp
     * */
    public void okHttp(View view) {
        try {
            run("http://atp.fulishe.com/ClientApi/category.php?returnformat=json&act=search_category_goods_list&c_id=35&page_num=10&page=1&encoding=utf8&api_version=1.0&debug=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * .toString(): This returns your object in string format.
     .string(): This returns your response.
     * */
    OkHttpClient client = new OkHttpClient();
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //同步
//        Response response = client.newCall(request).execute();
        //异步
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"error:"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,responseJson=response.body().string());
            }
        });
//        return response.body().string();
        return null;
    }
    /**
     * fastJson框架的使用
     * 将json字符串转换为java对象
     * 将java对象转换为json字符串
     * https://github.com/alibaba/fastjson
     * */
    public void fastJson(View view) {
        List<Good> goods = null;
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(responseJson, com.alibaba.fastjson.JSONObject.class);
        JSONArray jsonArray = jsonObject.getJSONObject("info").getJSONArray("goods");
        goods = JSON.parseObject(jsonArray.toJSONString(),new TypeReference<List<Good>>() {});
        Log.i(TAG,goods.toString());
    }

}
