package xyz.sachil.essence.model.net

import retrofit2.http.GET
import retrofit2.http.Path
import xyz.sachil.essence.model.net.response.*

interface INet {
    companion object {
        private const val API = "/api"
        private const val VERSION = "v2"
        private const val BANNERS = "$API/$VERSION/banners"
        private const val TYPES = "$API/$VERSION/categories/{categoryType}"
        private const val TYPE_DATA = "$API/$VERSION/data/category/{category}" +
                "/type/{type}/page/{page}/count/{count}"
        private const val RANDOM_DATA = "$API/$VERSION/random/category/{cagetory}" +
                "/type/{type}/count/{count}"
        private const val DETAIL = "$API/$VERSION/post/{postId}"
        private const val WEEKLY_POPULAR = "$API/$VERSION/hot/{hotType}/category/{category}" +
                "/count/{count}"
        private const val COMMENTS = "$API/$VERSION/post/comments/{postId}"
        private const val SEARCH = "$API/$VERSION/search/{search}/category/{category}" +
                "/type/{type}/page/{page}/count/{count}"
    }

    @GET(BANNERS)
    suspend fun requestBanners(): BannerResponse

    @GET(TYPES)
    suspend fun getTypes(@Path("categoryType") categoryType: String): CategoryResponse

    @GET(TYPE_DATA)
    suspend fun getTypeData(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("page") page: Int,
        @Path("count") count: Int
    ): TypeDataResponse

    @GET(RANDOM_DATA)
    suspend fun getRandomData(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("count") count: Int
    ): RandomDataResponse

    @GET(DETAIL)
    suspend fun getDetail(@Path("postId") postId: String): DetailResponse

    @GET(WEEKLY_POPULAR)
    suspend fun getWeeklyPopular(
        @Path("hotType") popularType: String,
        @Path("category") category: String,
        @Path("count") count: Int
    ): WeeklyPopularResponse

    @GET(COMMENTS)
    suspend fun getComments(@Path("postId") postId: String): CommentResponse

    @GET(SEARCH)
    suspend fun search(
        @Path("search") keyword: String,
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("page") page: Int,
        @Path("count") count: Int
    ): SearchResponse
}