package com.yqq.dictwoapp;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TextView;

import com.yqq.dictwoapp.myview.MyTabHost;

import org.w3c.dom.Text;

/**
 *
 看图写代码
 设计大于编码
 */
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyTabHost tabHost = (MyTabHost) findViewById(android.R.id.tabhost);

        View tab1ViewLayout = View.inflate(this, R.layout.tab1_layout, null);
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator(tab1ViewLayout).setContent(new Intent(this, Tab1Activity.class)));
        View tab2ViewLayout = View.inflate(this, R.layout.tab1_layout,null);
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator(tab2ViewLayout).setContent(new Intent(this, Tab2Activity.class)));
        View tab3ViewLayout = View.inflate(this, R.layout.tab1_layout,null);
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator(tab3ViewLayout).setContent(new Intent(this, Tab3Activity.class)));
        //改变标题
        final TextView tab_tv = (TextView) findViewById(R.id.tab_tv);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tabIndex = tabHost.getCurrentTab();
                tab_tv.setText(tabIndex==0?"单词":tabIndex==1?"翻译":"更多");
            }
        });
    }
}
