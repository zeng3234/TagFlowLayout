package com.zen.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TagAdapter<T> {
    private List<T> mDatas;

    public TagAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public TagAdapter(T[] datas) {
        this.mDatas = new ArrayList<>(Arrays.asList(datas));
    }

    private OnDataChangedListener mOnDataChangedListener;

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null)
            mOnDataChangedListener.onChanged();
    }

    public abstract View getView(int position, T t);
}
