package com.sachil.essence.model.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankResults<T> {

    @SerializedName("category")
    private List<String> mCategories = null;

    @SerializedName("error")
    private boolean mError = false;

    @SerializedName("results")
    private T mResults = null;

    public List<String> getCategories() {
        return mCategories;
    }

    public boolean isError() {
        return mError;
    }

    public T getResults() {
        return mResults;
    }
}
