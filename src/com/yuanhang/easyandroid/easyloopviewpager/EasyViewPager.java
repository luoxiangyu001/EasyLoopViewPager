package com.yuanhang.easyandroid.easyloopviewpager;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class EasyViewPager extends ViewPager {

    private boolean scrollable = true;// 是否可以滑动

    public EasyViewPager(Context context) {
        super(context);
        init();
    }

    public EasyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        initPagerScroller(-1);
    }

    public void initPagerScroller(int duration) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            EasyViewPagerScroller scroller = new EasyViewPagerScroller(getContext());
            if (duration > 0) {
                scroller.setDuration(duration);
            }
            mScroller.set(this, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return scrollable && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scrollable && super.onTouchEvent(event);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public EasyViewPagerAdapter getAdapter() {
        return (EasyViewPagerAdapter) super.getAdapter();
    }

    @SuppressWarnings("rawtypes")
    public void setAdapter(EasyViewPagerAdapter arg0) {
        super.setAdapter(arg0);
        if (getAdapter().isLoop()) {
            addOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageScrollStateChanged(int state) {
                    if ((state == 0 || state == 1) && getAdapter().isLoop()) {
                        if (getCurrentItem() == 0) {
                            setCurrentItem(getAdapter().getRealCount(), false);
                        } else if (getCurrentItem() == getAdapter().getRealCount() + 1) {
                            setCurrentItem(1, false);
                        }
                    }
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int arg0) {}

            });
            setCurrentFirstItem();

        }
    }

    public void setCurrentFirstItem() {
        if (getAdapter() != null) {
            if (getAdapter().isLoop() && getAdapter().getCount() >= 3) {
                setCurrentItem(1);// 循环ViewPager下标从1开始
            } else if (!getAdapter().isLoop() && getAdapter().getCount() >= 1) {
                setCurrentItem(0);// 非循环ViewPager下标从0开始
            }
        }
    }
}
