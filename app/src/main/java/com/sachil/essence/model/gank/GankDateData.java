package com.sachil.essence.model.gank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankDateData {

    @SerializedName("iOS")
    private List<GankCategoryData> mIOS = null;

    @SerializedName("Android")
    private List<GankCategoryData> mAndroid = null;

    @SerializedName("App")
    private List<GankCategoryData> mApp = null;

    @SerializedName("拓展资源")
    private List<GankCategoryData> mExtraResource = null;

    @SerializedName("瞎推荐")
    private List<GankCategoryData> mRecommendation = null;

    @SerializedName("前端")
    private List<GankCategoryData> mFrontEnd = null;

    @SerializedName("休息视频")
    private List<GankCategoryData> mVideo = null;

    @SerializedName("福利")
    private List<GankCategoryData> mPhoto = null;

    public List<GankCategoryData> getAndroid() {
        return mAndroid;
    }

    public List<GankCategoryData> getApp() {
        return mApp;
    }

    public List<GankCategoryData> getIOS() {
        return mIOS;
    }

    public List<GankCategoryData> getExtraResource() {
        return mExtraResource;
    }

    public List<GankCategoryData> getFrontEnd() {
        return mFrontEnd;
    }

    public List<GankCategoryData> getPhoto() {
        return mPhoto;
    }

    public List<GankCategoryData> getRecommendation() {
        return mRecommendation;
    }

    public List<GankCategoryData> getVideo() {
        return mVideo;
    }
}
