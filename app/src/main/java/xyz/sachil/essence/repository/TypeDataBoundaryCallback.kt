package xyz.sachil.essence.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.response.BaseResponse
import xyz.sachil.essence.model.net.response.TypeDataResponse
import xyz.sachil.essence.util.Utils

class TypeDataBoundaryCallback(
    val request: TypeDataRequest,
    val localTotalCount: suspend (TypeDataRequest) -> Int,
    val loadFromNet: suspend (TypeDataRequest, Int) -> BaseResponse<TypeData>,
    val handleTypeData: suspend (TypeDataRequest, List<TypeData>) -> Unit
) : PagedList.BoundaryCallback<TypeData>() {

    companion object {
        private const val TAG = "BoundaryCallback"
    }

    val loadState = MutableLiveData<LoadState>()

    //当初次加载时，如果从room返回的pagedList中没有数据时，会被调用
    override fun onZeroItemsLoaded() {
        Log.e(TAG,"***:${request.category.type},${request.type}")
        loadState.postValue(Refreshing)
        loadItemsFromNet()
    }


    //当从room返回的pagedList最后一个数据被加载时，会被调用
    override fun onItemAtEndLoaded(itemAtEnd: TypeData) {
        if (request.category != Utils.Category.WEEKLY_POPULAR) {
            loadState.postValue(Loading)
            loadItemsFromNet()
        }
    }

    private fun loadItemsFromNet() = request.coroutineScope.launch(request.coroutineContext) {
        try {
            when (request.category) {
                Utils.Category.WEEKLY_POPULAR -> {
                    val response = loadFromNet(request, 0)
                    if (response.data.isNotEmpty()) {
                        handleTypeData(request, response.data)
                    }
                    loadState.postValue(Success)
                }
                else -> {
                    val totalCount = localTotalCount(request)
                    val response = loadFromNet(request, totalCount) as TypeDataResponse
                    if (response.totalCounts == totalCount) {
                        loadState.postValue(NoMoreData)
                    } else {
                        if (response.data.isNotEmpty()) {
                            handleTypeData(request, response.data)
                        }
                        loadState.postValue(Success)
                    }
                }
            }
        } catch (exception: Exception) {
            loadState.postValue(Failed)
            throw  exception
        }
    }

}