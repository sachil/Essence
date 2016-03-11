package com.sachil.essence.presenter;

import com.sachil.essence.model.GankClient;

public class GankPresenter {

    private static final String TAG = GankPresenter.class.getSimpleName();

    private GankClient mClient = null;

    public GankPresenter() {
        mClient = new GankClient();
    }

    public void listHistory() {
        mClient.listHistory();
    }

    void getDataByCategory(String category, int count, int pageIndex) {
        mClient.getDataByCategory(category, count, pageIndex);
    }

    void getDataByDate(String date) {
        mClient.getDataByDate(date);
    }

    void getDataByRandom(String category, int count, int pageIndex) {
        mClient.getDataByRandom(category, count, pageIndex);
    }


}
