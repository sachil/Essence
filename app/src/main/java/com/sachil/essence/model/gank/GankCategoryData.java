package com.sachil.essence.model.gank;


import com.google.gson.annotations.SerializedName;

public class GankCategoryData {

    @SerializedName("_id")
    private String mId = null;

    @SerializedName("createdAt")
    private String mCreateTime = null;

    @SerializedName("desc")
    private String mDetail = null;

    @SerializedName("publishedAt")
    private String mPublishTime = null;

    @SerializedName("type")
    private String mType = null;

    @SerializedName("url")
    private String mUrl = null;

    @SerializedName("who")
    private String mProvider = null;

    public String getCreateTime() {
        return mCreateTime;
    }

    public String getDetail() {
        return mDetail;
    }

    public String getId() {
        return mId;
    }

    public String getProvider() {
        return mProvider;
    }

    public String getPublishTime() {
        return mPublishTime;
    }

    public String getType() {
        return mType;
    }

    public String getUrl() {
        return mUrl;
    }
}
