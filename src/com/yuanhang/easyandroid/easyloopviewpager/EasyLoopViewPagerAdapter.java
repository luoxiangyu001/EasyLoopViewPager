package com.yuanhang.easyandroid.easyloopviewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class EasyLoopViewPagerAdapter<T> extends PagerAdapter {

    private LayoutInflater inflater;
    private List<T> list;
    private int layoutId;
    private boolean isLoop;

    public EasyLoopViewPagerAdapter(Context context, int layoutId) {
        this(context, layoutId, true, null);
    }

    public EasyLoopViewPagerAdapter(Context context, int layoutId, boolean isLoop) {
        this(context, layoutId, isLoop, null);
    }

    public EasyLoopViewPagerAdapter(Context context, int layoutId, boolean isLoop, List<T> list) {
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.isLoop = isLoop;
        this.list = new ArrayList<T>();
        setList(list);
    }

    public void setViewPager(final ViewPager viewPager) {
        viewPager.setAdapter(this);
        if (isLoop()) {
            viewPager.addOnPageChangeListener(new OnPageChangeListener() {

                public void onPageScrollStateChanged(int state) {
                    if ((state == 0 || state == 1) && isLoop()) {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(getRealCount(), false);
                        } else if (viewPager.getCurrentItem() == getRealCount() + 1) {
                            viewPager.setCurrentItem(1, false);
                        }
                    }
                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                public void onPageSelected(int arg0) {}

            });
        }
        setCurrentFirstItem(viewPager);
    }

    public void setCurrentFirstItem(ViewPager viewPager) {
        if (isLoop() && getCount() >= 3) {
            viewPager.setCurrentItem(1);// 循环ViewPager下标从1开始
        } else if (!isLoop() && getCount() >= 1) {
            viewPager.setCurrentItem(0);// 非循环ViewPager下标从0开始
        }
    }

    public void setItems(List<T> list) {
        setList(list);
        notifyDataSetChanged();
    }

    public void setList(List<T> list) {
        this.list.clear();
        if (list != null) {
            if (this.isLoop) {// 多增加1头部和1尾部
                this.list.add(list.get(list.size() - 1));
                this.list.addAll(list);
                this.list.add(list.get(0));
            } else {
                this.list.addAll(list);
            }
        }
    }

    public List<T> getList() {
        return this.list;
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    public T getItem(int position) {
        return this.list == null || this.list.size() <= position ? null : this.list.get(position);
    }

    public int getRealPosition(int position) {
        if (this.isLoop) {
            if (position == this.list.size() - 1) {// 减去1头部和1尾部一共是2
                return position - 2;
            } else if (position > 0) {
                return position - 1;// 其余减去1头部
            }
        }
        return position;
    }

    public int getRealCount() {
        return this.isLoop ? (this.list == null || this.list.size() < 3 ? 0 : this.list.size() - 2) : getCount();// 减去1头部和1尾部
    }

    @Override
    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflateView(container, position);
        convert(view, getItem(position), position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public View inflateView(ViewGroup container, final int position) {
        return this.inflater.inflate(this.layoutId, null);
    }

    public abstract void convert(View view, T item, int position);
}
