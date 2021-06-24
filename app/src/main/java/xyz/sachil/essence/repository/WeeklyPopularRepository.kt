package xyz.sachil.essence.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.bean.WeeklyPopularData
import xyz.sachil.essence.model.net.response.WeeklyPopularResponse
import xyz.sachil.essence.repository.callback.WeeklyPopularBoundaryCallback
import xyz.sachil.essence.repository.wrap.DataRequest
import xyz.sachil.essence.repository.wrap.DataResponse
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class WeeklyPopularRepository : KoinComponent {

    companion object {
        private const val PAGE_SIZE = 20
        private const val TAG = "WeeklyPopularRepository"

        //配置pagedList
        private val CONFIG = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(5)
            .build()
    }

    private val database: CacheDatabase by inject()
    private val netClient: NetClient by inject()

    fun getWeeklyPopular(request: DataRequest): DataResponse {
        val boundaryCallback = WeeklyPopularBoundaryCallback(
            request,
            ::loadFromNet,
            ::insertToDatabase
        )

        val dataSourceFactory = if (request.type == Utils.PopularType.VIEWS.type) {
            database.weeklyPopularDataDao().getWeeklyPopularByViews(request.category, request.type)
        } else {
            database.weeklyPopularDataDao().getWeeklyPopularByLikes(request.category, request.type)
        }
        val data: LiveData<PagedList<TypeData>> =
            LivePagedListBuilder(dataSourceFactory, CONFIG)
                .setBoundaryCallback(boundaryCallback).build()
        return DataResponse(data, boundaryCallback.loadState) {
            refresh(request, boundaryCallback)
        }
    }

    private fun refresh(request: DataRequest, boundaryCallback: WeeklyPopularBoundaryCallback) =
        request.coroutineScope.launch(request.coroutineContext) {
            if (database.weeklyPopularDataDao().getCount(request.category, request.type) == 0) {
                boundaryCallback.onZeroItemsLoaded()
            } else {
                //删除所有数据会触发TypeDataBoundaryCallback的onZeroItemsLoaded()方法
                database.weeklyPopularDataDao()
                    .deleteWeeklyPopularData(request.category, request.type)
            }
        }

    //当需要从网络上获取数据时会被调用
    private suspend fun loadFromNet(request: DataRequest): WeeklyPopularResponse =
        netClient.getWeeklyPopular(request.category, request.type, PAGE_SIZE)

    //从网络获取数据成功后会被调用，用于将获取的数据存入本地数据库
    private suspend fun insertToDatabase(weeklyPopularData: List<WeeklyPopularData>) =
        database.weeklyPopularDataDao().insertWeeklyPopularData(weeklyPopularData)

}