package com.sachil.essence.model;

import com.sachil.essence.model.inter.GankRequest;

import retrofit2.Retrofit;

public abstract class BaseClient {

    private static final String TAG = BaseClient.class.getSimpleName();
    private static final String BASE_URL = "http://gank.io/api/";

    protected Retrofit mRetrofit = null;
    protected GankRequest mGankRequest = null;

    public BaseClient() {
        if (mRetrofit == null)
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        if (mGankRequest == null)
            mGankRequest = mRetrofit.create(GankRequest.class);
    }

}
