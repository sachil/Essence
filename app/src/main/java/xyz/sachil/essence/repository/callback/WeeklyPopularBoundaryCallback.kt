package xyz.sachil.essence.repository.callback

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.bean.WeeklyPopularData
import xyz.sachil.essence.model.net.response.WeeklyPopularResponse
import xyz.sachil.essence.repository.wrap.DataRequest
import xyz.sachil.essence.util.Failed
import xyz.sachil.essence.util.LoadState
import xyz.sachil.essence.util.NoMoreData
import xyz.sachil.essence.util.Refreshing

class WeeklyPopularBoundaryCallback(
    val request: DataRequest,
    val loadFromNet: suspend (DataRequest) -> WeeklyPopularResponse,
    val handleData: suspend (List<WeeklyPopularData>) -> Unit
) : PagedList.BoundaryCallback<TypeData>() {

    val loadState = MutableLiveData<LoadState>()

    override fun onZeroItemsLoaded() {
        request.coroutineScope.launch(request.coroutineContext) {
            try {
                loadState.postValue(Refreshing)
                val response = loadFromNet(request)
                if (response.data.isNotEmpty()) {
                    handleData(response.data.map {
                        it.popularType = request.type
                        it
                    })
                }
                loadState.postValue(NoMoreData)
            } catch (exception: Exception) {
                loadState.postValue(Failed)
                throw exception
            }
        }
    }
}