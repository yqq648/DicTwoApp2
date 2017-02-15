package com.yqq.dictwoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Tab3Activity extends AppCompatActivity {
    ListView tab3_lv;
    String[] arrays;
    String TAG = Tab3Activity.class.getName();
    int[] arraysIcon = {R.drawable.ic_launcher,
            R.drawable.ic_launcher_ing,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher_ing,
            R.drawable.ic_launcher_ing,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher_ing,
            R.drawable.ic_launcher,
            R.drawable.ic_launcher_ing};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        tab3_lv = (ListView) findViewById(R.id.tab3_lv);
        arrays = getResources().getStringArray(R.array.tab3_list);
        tab3_lv.setAdapter(new BaseAdapter() {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.d("_App", "-----------------" + convertView);
                View view = null;
                if (convertView == null) {
                    view = View.inflate(Tab3Activity.this, R.layout.tab3_lv_item, null);//IO操作
                } else {
                    /* 别人已经使用过的被子 */
                    view = convertView;//第一次初始化时候的杯子， 可以看见的Item中的一个
                }
                TextView tab3_lv_item_tv = (TextView) view.findViewById(R.id.tab3_lv_item_tv);
                tab3_lv_item_tv.setText(getItem(position));
                ImageView tab3_lv_item_iv = (ImageView) view.findViewById(R.id.tab3_lv_item_iv);
                tab3_lv_item_iv.setImageResource(arraysIcon[position]);
                return view;
            }

            @Override
            public int getCount() {
                return arrays.length;
            }

            @Override
            public String getItem(int position) {
                return arrays[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
        });

        //设置items
        tab3_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String jingZi = "com.yqq.dictwoapp.Tab3Activity_" + (position + 1);//水中倒影
                try {
                    startActivity(new Intent(Tab3Activity.this, Class.forName(jingZi)));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
