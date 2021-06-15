package xyz.sachil.essence.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.model.cache.CacheDatabase
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.response.BaseResponse
import xyz.sachil.essence.util.Utils
import java.lang.Exception

@KoinApiExtension
class TypeDataRepository : KoinComponent {
    companion object {
        private const val TAG = "TypeDataRepository"
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

    fun getTypeData(request: TypeDataRequest): TypeDataResponse {
        val boundaryCallback = TypeDataBoundaryCallback(
            request,
            ::totalCount,
            ::loadFromNet,
            ::insertToDatabase
        )

        val dao = database.typeDataDao()
        val dataSourceFactory =
            if (request.category == Utils.Category.WEEKLY_POPULAR) {
                request.coroutineScope.launch(request.coroutineContext) {
                    val count = dao.test(request.category.type, request.type, request.popularType.type)
                    Log.e(TAG,"----------:$count")
                }
                Log.e(TAG,"++++++:${request.type},${request.popularType.type}")
                dao.getWeeklyPopular(request.category.type, request.type, request.popularType.type)
            } else
                dao.getTypeData(request.category.type, request.category.type, request.type)

        val data: LiveData<PagedList<TypeData>> = LivePagedListBuilder(dataSourceFactory, CONFIG)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return TypeDataResponse(data, boundaryCallback.loadState) {
            refresh(request, boundaryCallback.loadState)
        }
    }

    private fun refresh(request: TypeDataRequest, loadState: MutableLiveData<LoadState>) =
        request.coroutineScope.launch(request.coroutineContext) {
            //删除所有数据会触发TypeDataBoundaryCallback的onZeroItemsLoaded()方法
            if (request.category == Utils.Category.WEEKLY_POPULAR) {
                database.typeDataDao().deleteWeeklyPopular(request.category.type, request.type)
            } else {
                database.typeDataDao()
                    .deleteTypeData(request.category.type, request.category.type, request.type)
            }
        }

    //当需要从网络上获取数据时会被调用
    private suspend fun loadFromNet(
        request: TypeDataRequest,
        localTotalCount: Int
    ): BaseResponse<TypeData> {
        return when (request.category) {
            //获取本周最热
            Utils.Category.WEEKLY_POPULAR -> {
                netClient.getWeeklyPopular(request.type, request.popularType.type, PAGE_SIZE)
            }
            //获取分类数据
            else -> {
                val page = localTotalCount / PAGE_SIZE + 1
                netClient.getTypeData(request.category, request.type, page, PAGE_SIZE)
            }
        }
    }

    //从网络获取数据成功后会被调用，用于将获取的数据存入本地数据库
    private suspend fun insertToDatabase(request: TypeDataRequest, typeData: List<TypeData>) =
        database.typeDataDao().insertTypeData(typeData.map {
            it.owner = request.category.type
            if (request.category == Utils.Category.WEEKLY_POPULAR) {
                it.popularType = request.popularType.type
            }
            it
        })

    private suspend fun totalCount(request: TypeDataRequest): Int = database.typeDataDao().getCount(
        request.category.type,
        request.category.type,
        request.type
    )

}