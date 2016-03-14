package com.sachil.essence.model;

import com.sachil.essence.model.bean.GankDatas;
import com.sachil.essence.model.bean.GankItem;
import com.sachil.essence.model.bean.GankResults;
import com.sachil.essence.model.inter.IGank;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GankClient extends BaseClient implements IGank {
    private static final String TAG = GankClient.class.getSimpleName();

    public GankClient(RequestFinishedListener listener) {
        super(listener);
    }

    @Override
    public void listHistory() {

        Call<GankResults<List<String>>> call = mGankRequest.listHistory();

        call.enqueue(new Callback<GankResults<List<String>>>() {
            @Override
            public void onResponse(Call<GankResults<List<String>>> call, Response<GankResults<List<String>>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(REQUEST_TYPE.LIST_HISTORY, response.body());
            }

            @Override
            public void onFailure(Call<GankResults<List<String>>> call, Throwable t) {
                mListener.onFailed(REQUEST_TYPE.LIST_HISTORY, t);
            }
        });
    }

    @Override
    public void getDataByCategory(String category, int count, int pageIndex) {

        Call<GankResults<List<GankItem>>> call = mGankRequest.getDataByCategory(category, count, pageIndex);
        call.enqueue(new Callback<GankResults<List<GankItem>>>() {
            @Override
            public void onResponse(Call<GankResults<List<GankItem>>> call, Response<GankResults<List<GankItem>>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(REQUEST_TYPE.GET_DATA_BY_CATEGORY, response.body());
            }

            @Override
            public void onFailure(Call<GankResults<List<GankItem>>> call, Throwable t) {
                mListener.onFailed(REQUEST_TYPE.GET_DATA_BY_CATEGORY, t);
            }
        });
    }

    @Override
    public void getDataByDate(String year, String month, String day) {

        Call<GankResults<GankDatas>> call = mGankRequest.getDataByDate(year, month, day);

        call.enqueue(new Callback<GankResults<GankDatas>>() {
            @Override
            public void onResponse(Call<GankResults<GankDatas>> call, Response<GankResults<GankDatas>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(REQUEST_TYPE.GET_DATA_BY_DATE, response.body());
            }

            @Override
            public void onFailure(Call<GankResults<GankDatas>> call, Throwable t) {
                mListener.onFailed(REQUEST_TYPE.GET_DATA_BY_DATE, t);
            }
        });


    }

    @Override
    public void getDataByRandom(String category, int count) {

        Call<GankResults<List<GankItem>>> call = mGankRequest.getRandomData(category, count);

        call.enqueue(new Callback<GankResults<List<GankItem>>>() {
            @Override
            public void onResponse(Call<GankResults<List<GankItem>>> call, Response<GankResults<List<GankItem>>> response) {
                if (response.isSuccess())
                    mListener.onSucceed(REQUEST_TYPE.GET_DATA_BY_RANDOM, response.body());
            }

            @Override
            public void onFailure(Call<GankResults<List<GankItem>>> call, Throwable t) {
                mListener.onFailed(REQUEST_TYPE.GET_DATA_BY_RANDOM, t);
            }
        });
    }
}
