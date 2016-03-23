package com.sachil.essence.ui.holder;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachil.essence.R;
import com.sachil.essence.model.gank.GankCategoryData;

public class GankCategoryViewHolder extends BaseViewHolder<GankCategoryData> {

    private TextView mTitle = null;
    private RelativeSizeSpan mSizeSpan = null;
    private ForegroundColorSpan mColorSpan = null;

    public GankCategoryViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_gank_category);
        mTitle = bindView(R.id.gank_category_title);
        mSizeSpan = new RelativeSizeSpan(0.8f);
        mColorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.deep_orange_400));
    }

    @Override
    public void setData(GankCategoryData data) {
        String title = data.getProvider() == null ? data.getDetail() : String.format("%s(%s)", data.getDetail(), data.getProvider());
        Spannable span = new SpannableString(title);
        if (data.getProvider() != null)
            span.setSpan(mSizeSpan, title.length() - data.getProvider().length() - 2, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(mColorSpan, 0, data.getDetail().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTitle.setText(span);
    }
}
