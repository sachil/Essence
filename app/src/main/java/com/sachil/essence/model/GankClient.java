package com.sachil.essence.model;

import android.util.Log;

import com.sachil.essence.model.inter.IGank;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GankClient extends BaseClient implements IGank {
    private static final String TAG = GankClient.class.getSimpleName();

    @Override
    public void listHistory() {
        Call<ResponseBody> call = mGankRequest.listHistory();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccess())
                    Log.e(TAG, "List history success.");
                try {
                    Log.e(TAG, new String(response.body().bytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "List history failed:" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getDataByCategory(String category, int count, int pageIndex) {

    }

    @Override
    public void getDataByDate(String date) {

    }

    @Override
    public void getDataByRandom(String category, int count, int pageIndex) {

    }
}
