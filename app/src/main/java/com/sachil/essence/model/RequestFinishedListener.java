package com.sachil.essence.model;

import com.sachil.essence.model.BaseClient.REQUEST_TYPE;
import com.sachil.essence.model.bean.GankResults;

public interface RequestFinishedListener {

    void onSucceed(REQUEST_TYPE type,GankResults results);

    void onFailed(REQUEST_TYPE type,Throwable throwable);

}
