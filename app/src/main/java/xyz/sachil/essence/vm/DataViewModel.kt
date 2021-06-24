package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.util.LoadState
import xyz.sachil.essence.repository.DataRepository
import xyz.sachil.essence.repository.wrap.DataRequest
import xyz.sachil.essence.repository.wrap.DataResponse

@KoinApiExtension
open class DataViewModel : BaseViewModel(){
    companion object {
        private const val TAG = "DataViewModel"
    }

    private val dataRepository: DataRepository by inject()

    private val dataRequest = MutableLiveData<DataRequest>()
    private val dataResponse: LiveData<DataResponse> = Transformations.map(dataRequest) {
        dataRepository.getTypeData(it)
    }

    val typeData: LiveData<PagedList<TypeData>> = Transformations.switchMap(dataResponse) {
        it.data
    }

    val state: LiveData<LoadState> = Transformations.switchMap(dataResponse) {
        it.loadState
    }

    fun getTypeData(category: String, type: String) {
        val coroutineContext = Dispatchers.IO + exceptionHandler.getHandler()
        dataRequest.value = DataRequest(viewModelScope, coroutineContext, category, type)
    }

    fun refresh() {
        dataResponse.value?.refresh?.invoke()
    }

    override fun onExceptionHandled() {

    }
}