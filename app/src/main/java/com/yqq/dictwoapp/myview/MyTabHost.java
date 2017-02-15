package com.yqq.dictwoapp.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;

import com.yqq.dictwoapp.R;

public class MyTabHost extends TabHost {

    public MyTabHost(Context context) {
        super(context);
    }

    public MyTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setCurrentTab(int index) {
        View preView = getCurrentView();

        int preIndex = getCurrentTab();
        if (preView==null){
            super.setCurrentTab(index);
            return;
        }
        //当前的tab
        super.setCurrentTab(index);
        ///之后的tab
        View nextView = getCurrentView();
        int nextIndex = getCurrentTab();
        //如果是从1-2的情况下
        if (preIndex<nextIndex){
            preView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.tran_exit));
            nextView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.tran_in));
        }else {
            preView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.tran_exit2));
            nextView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.tran_in2));
        }
    }
}