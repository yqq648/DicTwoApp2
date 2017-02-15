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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * 单词界面
 */
public class Tab1Activity extends AppCompatActivity {
    private static final String TAG = Tab1Activity.class.getName();
    AutoCompleteTextView act_dic;
    SQLiteDatabase db;
    TextView tv_dic;
    EditText et_dic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        File file = getFileStreamPath("dictionary.db");
        db = openOrCreateDatabase(file.getAbsolutePath(), 0, null);
        act_dic = (AutoCompleteTextView) findViewById(R.id.act_dic);
        tv_dic = (TextView) findViewById(R.id.tv_dic);
        et_dic = (EditText) findViewById(R.id.et_dic);
        //设置词典的个数
        setDicCount();
        act_dic.setThreshold(1);//设置输入字符长度的后的提示
        act_dic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            /** 当输入框内容改变的时候，触发此事件 */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                Toast.makeText(DicActivity.this, s.toString(),Toast.LENGTH_SHORT).show();
//                //数据来自于Sqlite数据库 select * from t_words where english like 'm%';
//                //表t_words,字段english ,chinese
                if (TextUtils.isEmpty(s.toString())){
                    return;
                }
//                String[] words = selectSqlite(s.toString());
//                List<String> words = selectSqlite1(s.toString());
                String[] words = selectSqlite(s.toString());
                act_dic.setAdapter(new ArrayAdapter<String>(Tab1Activity.this,
                        android.R.layout.simple_list_item_1
                        , words));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


//        org.xmlpull.v1.XmlPullParser
        org.json.JSONObject jsonObject = new JSONObject();
    }


    private void setDicCount() {
        Cursor cursor = db.rawQuery("select * from t_words", null);
        if (cursor.isBeforeFirst()){
            tv_dic.setText("简约英汉词典包含"+cursor.getCount()+"单词");
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {//关闭数据库
        db.close();
        super.onDestroy();
    }

    private String[] selectSqlite(String word) {
        Cursor cursor = db.rawQuery("select * from t_words where english like '"+word+"%' limit 7", null);
        boolean isBeforeFirst = cursor.isBeforeFirst();
        String[] querys = null;
        if (isBeforeFirst) {//cursor开始
            querys = new String[cursor.getCount()];
            for (int i = 0; cursor.moveToNext(); i++) {
                querys[i] = cursor.getString(0)+"-"+cursor.getString(1);
            }
        }
        if (cursor.isAfterLast()){//cursor结束{
            cursor.close();//关闭cursor
        }
        return querys;
    }
    private List<String> selectSqlite1(String word) {
        Cursor cursor = db.rawQuery("select * from t_words where english like '"+word+"%' limit 10", null);
        List<String> querys = new ArrayList<>();
        boolean isBeforeFirst = cursor.isBeforeFirst();
        boolean isFirst = cursor.isFirst();

        while (cursor.moveToNext()){
            querys.add(cursor.getString(0));
        }
        boolean isLast = cursor.isLast();

        boolean isAfterLast = cursor.isAfterLast();

        cursor.close();
        return querys;
    }


    public void search_word(View view) {
        new Thread(){
            @Override
            public void run() {
                searchNet();
            }
        }.start();
    }

    private void searchNet() {
        final String word = et_dic.getText().toString();
        String urlStr = "http://dict-co.iciba.com/api/dictionary.php?w="+word+"&key=28D07ADFE6AB9FDF6018ED45E01E1D28";
        String pron = null;//发音文件地址
        String acceptation = null;//翻译
        try {
            URL url=new URL(urlStr);
            InputStream in = url.openStream();//读取I流
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//首先要有一个工厂
            XmlPullParser parser = factory.newPullParser();//解析器
            parser.setInput(in,null);//设置数据
            ////解析
            int event = parser.getEventType();//五中 start_document,start_tag,text,end_tag,end_document;
            while(event!=XmlPullParser.END_DOCUMENT){
                if (event==XmlPullParser.START_TAG){
                    String tag = parser.getName();
                    if (tag.equals("pron")){
                        parser.next();
                        pron = parser.getText();
                    }else if (tag.equals("acceptation")){
                        parser.next();
                        acceptation = parser.getText();
                    }
                }
                event = parser.next();
            }
            final String finalAcceptation = acceptation;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //这设计模式alertDialog
                    new AlertDialog.Builder(Tab1Activity.this).setTitle(word)
                            .setMessage(finalAcceptation)
                            .create().show();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
