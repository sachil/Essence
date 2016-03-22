package com.sachil.essence.presenter;


import com.sachil.essence.model.GankImp;
import com.sachil.essence.model.RequestFinishedListener;
import com.sachil.essence.ui.view.IBase;

public abstract class GankPresenter implements RequestFinishedListener {

    public static final String CATEGORY_ANDROID = "Android";
    public static final String CATEGORY_IOS = "iOS";
    public static final String CATEGORY_RES = "拓展资源";
    public static final String CATEGORY_FRONT_END = "前端";
    public static final String CATEGORY_RECOMMOND = "瞎推荐";
    public static final String CATEGORY_PHOTO = "福利";
    public static final String CATEGORY_VIDEO = "休息视频";
    public static final String CATEGORY_APP = "App";

    protected GankImp mGankImp = null;
    protected IBase mBaseView = null;

    public GankPresenter(IBase baseView) {
        mBaseView = baseView;
        mGankImp = new GankImp(this);
    }
}
