package com.sachil.essence.presenter.adapter;

import android.view.View;

import com.sachil.essence.R;

import kale.adapter.item.AdapterItem;

/**
 * Created by 20001962 on 2016/3/14.
 */
public class ItemView implements AdapterItem<Object> {

    @Override
    public void bindViews(View view) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_category_all;
    }

    @Override
    public void handleData(Object o, int i) {

    }

    @Override
    public void setViews() {

    }
}
