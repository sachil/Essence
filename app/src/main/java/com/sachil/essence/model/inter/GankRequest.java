package com.sachil.essence.model.inter;

import com.sachil.essence.model.bean.GankDatas;
import com.sachil.essence.model.bean.GankItem;
import com.sachil.essence.model.bean.GankResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankRequest {

    /**
     * 获取发过干货的日期
     *
     * @return 返回日期列表
     */
    @GET("day/history")
    Call<GankResults<List<String>>> listHistory();

    /**
     * 根据类型返回干货数据
     *
     * @param category  干货类型，包括(福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
     * @param count     请求个数，大于0
     * @param indexPage 第几页，大于0
     */
    @GET("data/{category}/{count}/{indexPage}")
    Call<GankResults<List<GankItem>>> getDataByCategory(@Path("category") String category, @Path("count") int count, @Path("indexPage") int indexPage);

    /**
     * 根据日期返回干货数据
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    @GET("day/{year}/{month}/{day}")
    Call<GankResults<GankDatas>> getDataByDate(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    /**
     * 根据类型随机返回干货数据
     *
     * @param category 干货类型，包括(福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
     * @param count    请求个数，大于0
     */

    @GET("random/data/{category}/{count}")
    Call<GankResults<List<GankItem>>> getRandomData(@Path("category") String category, @Path("count") int count);


}
