package com.yuanhang.easyandroid.easyloopviewpager;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class EasyViewPagerScroller extends Scroller {

    private int mDuration = 800;// 滑动时间,大于等于0时有效,值越大越慢

    public EasyViewPagerScroller(Context context) {
        super(context);
    }

    public EasyViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public EasyViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (mDuration >= 0 ? mDuration : duration));
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public static EasyViewPagerScroller initViewPagerScroll(ViewPager viewPager) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            EasyViewPagerScroller mScroller = new EasyViewPagerScroller(viewPager.getContext());
            mField.set(viewPager, mScroller);
            return mScroller;
        } catch (Exception e) {}
        return null;
    }
}
