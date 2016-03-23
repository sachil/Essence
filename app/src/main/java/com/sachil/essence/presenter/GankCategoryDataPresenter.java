package com.sachil.essence.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sachil.essence.model.gank.GankCategoryData;
import com.sachil.essence.ui.holder.GankCategoryViewHolder;
import com.sachil.essence.ui.view.IBase;

import java.util.List;


public class GankCategoryDataPresenter extends GankPresenter {

    private static final String TAG = GankCategoryDataPresenter.class.getSimpleName();
    private List<GankCategoryData> mDatas = null;

    private GankCategoryAdapter mAdapter = null;


    public GankCategoryDataPresenter(IBase baseView) {
        super(baseView);
    }

    @Override
    public void onFailed(int request, Throwable throwable) {
        mBaseView.hideLoadingView();
    }

    @Override
    public void onSucceed(int request, Object results) {
        mBaseView.hideLoadingView();
        mDatas = (List<GankCategoryData>) results;
        if (mAdapter == null)
            mAdapter = new GankCategoryAdapter();
        mBaseView.updateData(GET_CATEGORY_DATA, null);
    }

    public void getDataByCategory(String category) {
        mBaseView.showLoadingView();
        mGankImp.getDataByCategory(category, Integer.MAX_VALUE, 1);
    }

    public void getDataByRandom(String category, int count) {
        mBaseView.showLoadingView();
        mGankImp.getDataByRandom(category, count);
    }

    public GankCategoryAdapter getAdapter() {
        return mAdapter;
    }

    private class GankCategoryAdapter extends RecyclerView.Adapter<GankCategoryViewHolder> {

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        @Override
        public void onBindViewHolder(GankCategoryViewHolder holder, int position) {
            holder.setData(mDatas.get(position));
        }

        @Override
        public GankCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GankCategoryViewHolder(parent);
        }
    }
}
