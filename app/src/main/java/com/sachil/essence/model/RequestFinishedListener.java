package com.sachil.essence.model;

public interface RequestFinishedListener {
    int LIST_HISTORY = 0X01;
    int GET_DATE_DATA = 0X02;
    int GET_CATEGORY_DATA = 0X03;

    void onSucceed(int request, Object results);

    void onFailed(int request, Throwable throwable);

}
