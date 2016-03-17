package com.sachil.essence.model;

import com.sachil.essence.model.BaseImp.REQUEST_TYPE;
import com.sachil.essence.model.gank.GankData;

public interface RequestFinishedListener {

    void onSucceed(REQUEST_TYPE type,GankData results);

    void onFailed(REQUEST_TYPE type,Throwable throwable);

}
