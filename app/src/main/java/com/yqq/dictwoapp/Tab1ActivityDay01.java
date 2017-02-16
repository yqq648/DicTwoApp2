package com.yqq.dictwoapp;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.yqq.dictwoapp.bean.Good;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 单词界面
 */
public class Tab1ActivityDay01 extends AppCompatActivity {
    private static final String TAG = Tab1ActivityDay01.class.getName();
    String responseJson = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        TextView channel_name = (TextView) findViewById(R.id.channel_name);
        //如果你想拿到AndroidManifest中的信息，要通过包管理器 packagename
        try {
            String APK_CHANNEL = getPackageManager().getApplicationInfo("com.yqq.dictwoapp",PackageManager.GET_META_DATA).
                    metaData.getString("APK_CHANNEL");
            channel_name.setText("渠道："+APK_CHANNEL);//那个标记如何拿到？
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     * okHttp联网处理 post,get请求
     * 用于Android和Java应用程序的HTTP和HTTP / 2客户端。
     * https://github.com/square/okhttp
     * http://square.github.io/okhttp/
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
    OkHttpClient client = new OkHttpClient();//1 client
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();//2 newRequest get请求

//        new Thread(){
//            @Override
//            public void run() {
//                //同步
////        Response response = client.newCall(request).execute();
//                runOnUiThread();
//            }
//        }.start();
        //异步
        client.newCall(request).enqueue(new Callback() {//3 call
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"error:"+e.getMessage());
            }

            @Override//4 success
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,responseJson=response.body().string());
            }
        });
//        return response.body().string();
        return null;
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
    }

}
/**
 * 确定XXX登陆
 * 给予YYYY权限
 *
 */