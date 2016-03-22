package com.sachil.essence.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseImp {

    private static final String TAG = BaseImp.class.getSimpleName();
    private static final String BASE_URL = "http://gank.io/api/";

    protected Retrofit mRetrofit = null;
    protected GankInterface mGankInterface = null;
    protected RequestFinishedListener mListener = null;

    public BaseImp(RequestFinishedListener listener) {
        if (mRetrofit == null)
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        if (mGankInterface == null)
            mGankInterface = mRetrofit.create(GankInterface.class);
        mListener = listener;
    }
}
