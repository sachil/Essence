package com.sachil.essence.model.gank;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankData<T> {

    @SerializedName("category")
    private List<String> mCategories = null;

    @SerializedName("error")
    private boolean mError = false;

    @SerializedName("results")
    private T mData = null;

    public List<String> getCategories() {
        return mCategories;
    }

    public boolean isError() {
        return mError;
    }

    public T getData() {
        return mData;
    }
}
