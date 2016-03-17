package com.sachil.essence.presenter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sachil.essence.R;
import com.sachil.essence.model.gank.GankDateData;

import kale.adapter.item.AdapterItem;

public class GankDateItem implements AdapterItem<GankDateData> {

    private ImageView mBookmark = null;
    private TextView mTitle = null;
    private ImageView mPhoto = null;

    @Override
    public void bindViews(View view) {
        mBookmark = (ImageView) view.findViewById(R.id.category_all_bookmark);
        mTitle = (TextView) view.findViewById(R.id.category_all_title);
        mPhoto = (ImageView) view.findViewById(R.id.category_all_photo);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_category_all;
    }

    @Override
    public void handleData(GankDateData gankDateData, int position) {

    }

    @Override
    public void setViews() {

    }
}
