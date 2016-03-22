package com.sachil.essence.presenter;

import com.sachil.essence.ui.view.IBase;


public class GankCategoryDataPresenter extends GankPresenter {

    private static final String TAG = GankCategoryDataPresenter.class.getSimpleName();

    public GankCategoryDataPresenter(IBase baseView) {
        super(baseView);
    }

    @Override
    public void onFailed(int request, Throwable throwable) {

    }

    @Override
    public void onSucceed(int request, Object results) {

    }

    public void getDataByCategory(String category) {
        mBaseView.showLoadingView();
        mGankImp.getDataByCategory(category, Integer.MAX_VALUE, 1);
    }

    public void getDataByRandom(String category, int count) {
        mBaseView.showLoadingView();
        mGankImp.getDataByRandom(category, count);
    }
}
