package xyz.sachil.essence.repository.callback

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.response.TypeDataResponse
import xyz.sachil.essence.repository.wrap.DataRequest
import xyz.sachil.essence.util.*

class DataBoundaryCallback(
    val request: DataRequest,
    val localTotalCount: suspend (DataRequest) -> Int,
    val loadFromNet: suspend (DataRequest, Int) -> TypeDataResponse,
    val handleTypeData: suspend (List<TypeData>) -> Unit
) : PagedList.BoundaryCallback<TypeData>() {

    companion object {
        private const val TAG = "DataBoundaryCallback"
    }

    val loadState = MutableLiveData<LoadState>()

    //当初次加载时，如果从room返回的pagedList中没有数据时，会被调用
    override fun onZeroItemsLoaded() {
        loadState.postValue(Refreshing)
        loadItemsFromNet()
    }

    //当从room返回的pagedList最后一个数据被加载时，会被调用
    override fun onItemAtEndLoaded(itemAtEnd: TypeData) {
        loadState.postValue(Loading)
        loadItemsFromNet()
    }

    private fun loadItemsFromNet() = request.coroutineScope.launch(request.coroutineContext) {
        try {
            val totalCount = localTotalCount(request)
            val response = loadFromNet(request, totalCount)
            if (response.totalCounts == totalCount) {
                loadState.postValue(NoMoreData)
            } else {
                if (response.data.isNotEmpty()) {
                    handleTypeData(response.data)
                }
                loadState.postValue(Success)
            }
        } catch (exception: Exception) {
            loadState.postValue(Failed)
            throw  exception
        }
    }

}