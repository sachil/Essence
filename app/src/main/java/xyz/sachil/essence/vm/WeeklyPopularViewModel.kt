package xyz.sachil.essence.vm

import androidx.lifecycle.*
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.repository.wrap.DataRequest
import xyz.sachil.essence.repository.wrap.DataResponse
import xyz.sachil.essence.repository.WeeklyPopularRepository
import xyz.sachil.essence.util.LoadState

@KoinApiExtension
class WeeklyPopularViewModel : BaseViewModel(){

    companion object{
        private const val TAG = "WeeklyPopularViewModel"
    }

    private val weeklyPopularRepository:WeeklyPopularRepository by inject()

    private val dataRequest = MutableLiveData<DataRequest>()
    private val dataResponse: LiveData<DataResponse> = Transformations.map(dataRequest) {
        weeklyPopularRepository.getWeeklyPopular(it)
    }

    val typeData: LiveData<PagedList<TypeData>> = Transformations.switchMap(dataResponse) {
        it.data
    }

    val state: LiveData<LoadState> = Transformations.switchMap(dataResponse) {
        it.loadState
    }

    fun getWeeklyPopularData(category: String, type: String) {
        val coroutineContext = Dispatchers.IO + exceptionHandler.getHandler()
        dataRequest.value = DataRequest(viewModelScope, coroutineContext, category, type)
    }

    fun refresh() {
        dataResponse.value?.refresh?.invoke()
    }

    override fun onExceptionHandled() {

    }
}