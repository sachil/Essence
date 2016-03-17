package com.sachil.essence.presenter;

import android.util.Log;

import com.sachil.essence.model.BaseImp.REQUEST_TYPE;
import com.sachil.essence.model.GankImp;
import com.sachil.essence.model.RequestFinishedListener;
import com.sachil.essence.model.gank.GankData;
import com.sachil.essence.ui.view.IBase;

public class GankPresenter implements RequestFinishedListener {
    public static final String CATEGORY_ANDROID = "Android";
    public static final String CATEGORY_IOS = "iOS";
    public static final String CATEGORY_RES = "拓展资源";
    public static final String CATEGORY_FRONT_END = "前端";
    public static final String CATEGORY_RECOMMOND = "瞎推荐";
    public static final String CATEGORY_PHOTO = "福利";
    public static final String CATEGORY_VIDEO = "休息视频";
    public static final String CATEGORY_APP = "App";
    private static final String TAG = GankPresenter.class.getSimpleName();
    private GankImp mClient = null;
    private IBase mBaseView = null;

    public GankPresenter(IBase baseView) {
        mClient = new GankImp(this);
        mBaseView = baseView;
    }

    @Override
    public void onFailed(REQUEST_TYPE type, Throwable throwable) {
        mBaseView.hideLoadingView();
        Log.e(TAG, "Request failed.");
    }

    @Override
    public void onSucceed(REQUEST_TYPE type, GankData results) {
        mBaseView.hideLoadingView();
        Log.e(TAG, "Request succeed.");
        mBaseView.updateData(type, results.getData());
    }

    public void listHistory() {
        mBaseView.showLoadingView();
        mClient.listHistory();
    }

    public void getDataByCategory(String category) {
        mBaseView.showLoadingView();
        mClient.getDataByCategory(category, Integer.MAX_VALUE, 1);
    }

    public void getDataByDate(String year, String month, String day) {
        mClient.getDataByDate(year, month, day);
    }

    public void getDataByRandom(String category, int count) {
        mClient.getDataByRandom(category, count);
    }


}
