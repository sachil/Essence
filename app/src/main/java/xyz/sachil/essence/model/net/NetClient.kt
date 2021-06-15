package xyz.sachil.essence.model.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.model.net.bean.*
import xyz.sachil.essence.model.net.response.TypeDataResponse
import xyz.sachil.essence.model.net.response.WeeklyPopularResponse
import java.util.concurrent.TimeUnit

class NetClient private constructor() {
    private val netClient: INet

    companion object {
        private const val TAG = "NetClient"
        //private const val HOST =
        private const val BASE_URL = "https://${Utils.HOST}"
        private const val TIME_OUT = 30 * 1000L

        @Volatile
        private var netClient: NetClient? = null

        fun newInstance(): NetClient = netClient ?: synchronized(this) {
            netClient ?: NetClient().also { netClient = it }
        }
    }

    init {
        val trustManager = IgnoreExpiredCertificateManager()
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .callTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .sslSocketFactory(trustManager.getSSLContext()!!.socketFactory, trustManager)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        netClient = retrofit.create(INet::class.java)
    }

    suspend fun getBanners(): List<Banner> = netClient.requestBanners().data

    suspend fun getTypes(category: Utils.Category): List<Type> =
        netClient.getTypes(category.type).data

    suspend fun getTypeData(
        category: Utils.Category,
        type: String,
        page: Int = 1,
        count: Int = 10
    ): TypeDataResponse = netClient.getTypeData(category.type, type, page, count)

    suspend fun getRandomData(category: Utils.Category, type: String, count: Int = 1):
            List<TypeData> = netClient.getRandomData(category.type, type, count).data

    suspend fun getDetail(postId: String): Detail = netClient.getDetail(postId).data

    suspend fun getWeeklyPopular(
        category: String,
        popularType: String,
        count: Int
    ): WeeklyPopularResponse = netClient.getWeeklyPopular(popularType, category, count)

    suspend fun getComments(postId: String): List<Comment> = netClient.getComments(postId).data

    suspend fun search(
        keyword: String,
        category: Utils.Category,
        type: String,
        page: Int = 1,
        count: Int = 10
    ): List<SearchResult> = netClient.search(keyword, category.type, type, page, count).data
}