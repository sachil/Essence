package com.sachil.essence.model;

import com.sachil.essence.model.gank.GankCategoryData;
import com.sachil.essence.model.gank.GankData;
import com.sachil.essence.model.gank.GankDateData;
import com.sachil.essence.model.gank.GankHistoryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GankImp extends BaseImp implements IGank {
    private static final String TAG = GankImp.class.getSimpleName();

    public GankImp(RequestFinishedListener listener) {
        super(listener);
    }

    @Override
    public void listHistory() {

        Call<GankData<List<String>>> call = mGankInterface.listHistory();

        call.enqueue(new Callback<GankData<List<String>>>() {
            @Override
            public void onResponse(Call<GankData<List<String>>> call, Response<GankData<List<String>>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(RequestFinishedListener.LIST_HISTORY,response.body());
            }

            @Override
            public void onFailure(Call<GankData<List<String>>> call, Throwable t) {
                mListener.onFailed(RequestFinishedListener.LIST_HISTORY,t);
            }
        });
    }

    @Override
    public void getDataByCategory(String category, int count, int pageIndex) {

        Call<GankData<List<GankCategoryData>>> call = mGankInterface.getDataByCategory(category, count, pageIndex);
        call.enqueue(new Callback<GankData<List<GankCategoryData>>>() {
            @Override
            public void onResponse(Call<GankData<List<GankCategoryData>>> call, Response<GankData<List<GankCategoryData>>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(RequestFinishedListener.GET_CATEGORY_DATA,response.body().getData());
            }

            @Override
            public void onFailure(Call<GankData<List<GankCategoryData>>> call, Throwable t) {
                mListener.onFailed(RequestFinishedListener.GET_CATEGORY_DATA,t);
            }
        });
    }

    @Override
    public void getDataByDate(final String year, final String month, final String day) {

        Call<GankData<GankDateData>> call = mGankInterface.getDataByDate(year, month, day);

        call.enqueue(new Callback<GankData<GankDateData>>() {
            @Override
            public void onResponse(Call<GankData<GankDateData>> call, Response<GankData<GankDateData>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(RequestFinishedListener.GET_DATE_DATA,new GankHistoryData(year,month,day,response.body().getData()));
            }

            @Override
            public void onFailure(Call<GankData<GankDateData>> call, Throwable t) {
                mListener.onFailed(RequestFinishedListener.GET_DATE_DATA,t);
            }
        });


    }

    @Override
    public void getDataByRandom(String category, int count) {

        Call<GankData<List<GankCategoryData>>> call = mGankInterface.getRandomData(category, count);

        call.enqueue(new Callback<GankData<List<GankCategoryData>>>() {
            @Override
            public void onResponse(Call<GankData<List<GankCategoryData>>> call, Response<GankData<List<GankCategoryData>>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(RequestFinishedListener.GET_CATEGORY_DATA,response.body().getData());
            }

            @Override
            public void onFailure(Call<GankData<List<GankCategoryData>>> call, Throwable t) {
                mListener.onFailed(RequestFinishedListener.GET_CATEGORY_DATA,t);
            }
        });
    }
}
