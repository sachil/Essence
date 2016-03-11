package com.sachil.essence.model.inter;

import okhttp3.ResponseBody;
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
    Call<ResponseBody> listHistory();

    /**
     * 根据类型返回干货数据
     *
     * @param category  干货类型，包括(福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
     * @param count     请求个数，大于0
     * @param indexPage 第几页，大于0
     */
    @GET("data/{category}/{count}/{indexPage}")
    Call<ResponseBody> getDataByCategory(@Path("category") String category, @Path("count") int count, @Path("indexPage") int indexPage);

    /**
     * 根据日期返回干货数据
     *
     * @param date 数据日期，格式必须为yyyy/dd/mm
     */
    @GET("day/{date}")
    Call<ResponseBody> getDataByDate(@Path("date") String date);

    /**
     * 根据类型随机返回干货数据
     *
     * @param category 干货类型，包括(福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
     * @param count    请求个数，大于0
     */

    @GET("random/data/{category}/{count}")
    Call<ResponseBody> getRandomData(@Path("category") String category, @Path("count") int count);


}
