package com.yqq.dictwoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

/**
 * 下他通知小词
 * @since 2015-10-21
 */
public class Tab3Activity_1 extends AppCompatActivity {
    String imgUrl = "http://img.fulishe.com//images/201612/thumb_img/14102_thumb_G_1482399411944.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getName());
        ImageView iv = new ImageView(this);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
       //https://github.com/square/picasso
        Picasso.with(this)
                .load(imgUrl)
                .resize(50, 50)//自定义设置大小
                .centerCrop()//图片摆放,正在加载显示图片
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)//加载错误显示图片
                .into(iv);
        setContentView(iv);
    }
}
