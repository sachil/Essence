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
import xyz.sachil.essence.model.net.response.TypeDataResponse
import xyz.sachil.essence.repository.callback.DataBoundaryCallback
import xyz.sachil.essence.repository.wrap.DataRequest
import xyz.sachil.essence.repository.wrap.DataResponse

@KoinApiExtension
class DataRepository : KoinComponent {
    companion object {
        private const val TAG = "DataRepository"
        private const val PAGE_SIZE = 20

        //配置pagedList
        private val CONFIG = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(5)
            .build()
    }

    private val database: CacheDatabase by inject()
    private val netClient: NetClient by inject()

    fun getTypeData(request: DataRequest): DataResponse {
        val boundaryCallback = DataBoundaryCallback(
            request,
            ::totalCount,
            ::loadFromNet,
            ::insertToDatabase
        )
        val dataSourceFactory =
            database.typeDataDao().getTypeData(request.category, request.type)
        val data: LiveData<PagedList<TypeData>> = LivePagedListBuilder(dataSourceFactory, CONFIG)
            .setBoundaryCallback(boundaryCallback)
            .build()
        return DataResponse(data, boundaryCallback.loadState) {
            refresh(request, boundaryCallback)
        }
    }

    private fun refresh(request: DataRequest, callback: DataBoundaryCallback) =

        request.coroutineScope.launch(request.coroutineContext) {
            if (totalCount(request) == 0) {
                callback.onZeroItemsLoaded()
            } else {
                //删除所有数据会触发TypeDataBoundaryCallback的onZeroItemsLoaded()方法
                database.typeDataDao().deleteTypeData(request.category, request.type)
            }
        }

    //当需要从网络上获取数据时会被调用
    private suspend fun loadFromNet(
        request: DataRequest,
        localTotalCount: Int
    ): TypeDataResponse {
        val page = localTotalCount / PAGE_SIZE + 1
        return netClient.getTypeData(request.category, request.type, page, PAGE_SIZE)
    }

    //从网络获取数据成功后会被调用，用于将获取的数据存入本地数据库
    private suspend fun insertToDatabase(typeData: List<TypeData>) =
        database.typeDataDao().insertTypeData(typeData)

    private suspend fun totalCount(request: DataRequest): Int = database.typeDataDao().getCount(
        request.category,
        request.type
    )

}