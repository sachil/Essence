package com.sachil.essence.presenter;

import android.util.Log;

import com.sachil.essence.model.BaseClient.REQUEST_TYPE;
import com.sachil.essence.model.GankClient;
import com.sachil.essence.model.RequestFinishedListener;
import com.sachil.essence.model.bean.GankResults;
import com.sachil.essence.ui.view.IBase;

public class GankPresenter implements RequestFinishedListener {

    private static final String TAG = GankPresenter.class.getSimpleName();

    private GankClient mClient = null;
    private IBase mBaseView = null;

    public GankPresenter(IBase baseView) {
        mClient = new GankClient(this);
        mBaseView = baseView;
    }

    @Override
    public void onFailed(REQUEST_TYPE type, Throwable throwable) {
        mBaseView.hideLoadingView();
        Log.e(TAG, "Request failed.");
    }

    @Override
    public void onSucceed(REQUEST_TYPE type, GankResults results) {
        mBaseView.hideLoadingView();
        Log.e(TAG, "Request succeed.");
    }

    public void listHistory() {
        mBaseView.showLoadingView();
        mClient.listHistory();
    }

    public void getDataByCategory(String category, int count, int pageIndex) {
        mClient.getDataByCategory(category, count, pageIndex);
    }

    public void getDataByDate(String year, String month, String day) {
        mClient.getDataByDate(year, month, day);
    }

    public void getDataByRandom(String category, int count) {
        mClient.getDataByRandom(category, count);
    }


}
