package com.yuanhang.easyandroid.easyloopviewpager;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class EasyViewPagerAdapter<T> extends PagerAdapter {

    private LayoutInflater inflater;
    private List<T> list;
    private int layoutId;
    private boolean isLoop;

    public EasyViewPagerAdapter(EasyViewPager viewPager, int layoutId) {
        this(viewPager, layoutId, true, null);
    }

    public EasyViewPagerAdapter(EasyViewPager viewPager, int layoutId, boolean isLoop) {
        this(viewPager, layoutId, isLoop, null);
    }

    public EasyViewPagerAdapter(EasyViewPager viewPager, int layoutId, boolean isLoop, List<T> list) {
        this.inflater = LayoutInflater.from(viewPager.getContext());
        this.layoutId = layoutId;
        this.isLoop = isLoop;
        this.list = new ArrayList<T>();
        setList(list);
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

    @Override
    public int getCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public int getRealCount() {
        return this.isLoop ? (this.list == null || this.list.size() < 3 ? 0 : this.list.size() - 2) : getCount();// 减去1头部和1尾部
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

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflateView();
        convert(view, getItem(position), position);
        container.addView(view);
        return view;
    }

    public View inflateView() {
        return this.inflater.inflate(this.layoutId, null);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public abstract void convert(View view, T item, int position);
}
