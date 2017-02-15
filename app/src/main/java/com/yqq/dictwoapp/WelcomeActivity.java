package com.yqq.dictwoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    List<Fragment> fragments = new ArrayList<>(2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //欢迎界面有两种情况， 二选其一
        SharedPreferences sp = getPreferences(0);
        boolean first = sp.getBoolean("first",true);
        if (first){
            setContentView(R.layout.activity_welcome);
            //        android.os.Build.VERSION_CODES.BASE;
            fragments.add(new Welcome1Fragment());
            fragments.add(new Welcome2Fragment());
            /////获得ViewPager,类似于ListView
            ViewPager wel_viewpager = (ViewPager) findViewById(R.id.wel_viewpager);
            wel_viewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
            sp.edit().putBoolean("first",false).commit();//有异常发生
        }else{
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(R.drawable.wel_into2);
            Animation alaAnimation = new AlphaAnimation(1.0f,0.3f);
            alaAnimation.setDuration(1000);
            alaAnimation.setFillAfter(true);//保持动画最终的状态
            alaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            imageView.startAnimation(alaAnimation);
            setContentView(imageView);
        }
    }
    /** adapter */
    class MyFragmentAdapter extends FragmentPagerAdapter{


        public MyFragmentAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
