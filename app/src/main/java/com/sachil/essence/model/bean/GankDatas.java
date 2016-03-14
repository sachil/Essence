package com.sachil.essence.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankDatas {

    @SerializedName("iOS")
    private List<GankItem> mIOS = null;

    @SerializedName("Android")
    private List<GankItem> mAndroid = null;

    @SerializedName("App")
    private List<GankItem> mApp = null;

    @SerializedName("\u62d3\u5c55\u8d44\u6e90")
    private List<GankItem> mExtraResource = null;

    @SerializedName("\u778e\u63a8\u8350")
    private List<GankItem> mRecommendation = null;

    @SerializedName("\u524d\u7aef")
    private List<GankItem> mFrontEnd = null;

    @SerializedName("\u4f11\u606f\u89c6\u9891")
    private List<GankItem> mVideo = null;

    @SerializedName("\u798f\u5229")
    private List<GankItem> mPhoto = null;

    public List<GankItem> getAndroid() {
        return mAndroid;
    }

    public List<GankItem> getApp() {
        return mApp;
    }

    public List<GankItem> getIOS() {
        return mIOS;
    }

    public List<GankItem> getExtraResource() {
        return mExtraResource;
    }

    public List<GankItem> getFrontEnd() {
        return mFrontEnd;
    }

    public List<GankItem> getPhoto() {
        return mPhoto;
    }

    public List<GankItem> getRecommendation() {
        return mRecommendation;
    }

    public List<GankItem> getVideo() {
        return mVideo;
    }
}
