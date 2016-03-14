package com.sachil.essence.model;

import com.sachil.essence.model.inter.GankRequest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseClient {

    public static final String CATEGORY_ANDROID = "Android";
    public static final String CATEGORY_IOS = "iOS";
    public static final String CATEGORY_RES = "拓展资源";
    public static final String CATEGORY_FRONT_END = "前端";
    public static final String CATEGORY_RECOMMOND = "瞎推荐";
    public static final String CATEGORY_PHOTO = "福利";
    public static final String CATEGORY_VIDEO = "休息视频";
    public static final String CATEGORY_APP = "App";

    private static final String TAG = BaseClient.class.getSimpleName();
    private static final String BASE_URL = "http://gank.io/api/";

    protected Retrofit mRetrofit = null;
    protected GankRequest mGankRequest = null;
    protected RequestFinishedListener mListener = null;

    public BaseClient(RequestFinishedListener listener) {
        if (mRetrofit == null)
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        if (mGankRequest == null)
            mGankRequest = mRetrofit.create(GankRequest.class);
        mListener = listener;
    }

    public enum REQUEST_TYPE {
        LIST_HISTORY, GET_DATA_BY_CATEGORY,
        GET_DATA_BY_DATE, GET_DATA_BY_RANDOM
    }

}
