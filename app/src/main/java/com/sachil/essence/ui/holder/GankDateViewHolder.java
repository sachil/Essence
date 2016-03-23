package com.sachil.essence.ui.holder;


import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sachil.essence.R;
import com.sachil.essence.model.gank.GankCategoryData;
import com.sachil.essence.model.gank.GankDateData;
import com.sachil.essence.model.gank.GankHistoryData;

import java.util.List;

public class GankDateViewHolder extends BaseViewHolder<GankHistoryData> {

    private ImageView mBookmark = null;
    private TextView mDate = null;
    private TextView mTitle = null;
    private ImageView mPhoto = null;

    public GankDateViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_gank_date);
        mBookmark = bindView(R.id.category_bookmark);
        mDate = bindView(R.id.gank_date_time);
        mTitle = bindView(R.id.gank_date_title);
        mPhoto = bindView(R.id.gank_date_photo);
    }

    @Override
    public void setData(GankHistoryData data) {
        mDate.setText(data.getDate());
        GankDateData dateData = data.getDateData();
        if (dateData != null) {
            List<GankCategoryData> photoData = dateData.getPhoto();
            List<GankCategoryData> videoData = dateData.getVideo();
            if (photoData != null && photoData.size() > 0)
                Glide.with(getContext()).load(photoData.get(0).getUrl()).crossFade().into(mPhoto);
            if (videoData != null && videoData.size() > 0)
                mTitle.setText(videoData.get(0).getDetail());
        } else {
            //清除glide缓存的图片数据，否则新的item中会显示旧的image。
            Glide.clear(mPhoto);
            mPhoto.setImageDrawable(null);
            mTitle.setText(new String());
        }
    }
}
