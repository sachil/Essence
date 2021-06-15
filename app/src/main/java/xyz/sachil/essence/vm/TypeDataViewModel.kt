package xyz.sachil.essence.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.repository.TypeDataRepository
import xyz.sachil.essence.repository.TypeDataRequest
import xyz.sachil.essence.repository.TypeDataResponse
import xyz.sachil.essence.repository.LoadState

@KoinApiExtension
open class TypeDataViewModel : BaseViewModel(), KoinComponent {
    companion object {
        private const val TAG = "TypeDataViewModel"
    }

    private val typeDataRepository: TypeDataRepository by inject()

    private val typeDataRequest = MutableLiveData<TypeDataRequest>()
    private val wrappedTypeData: LiveData<TypeDataResponse> = Transformations.map(typeDataRequest) {
        typeDataRepository.getTypeData(it)
    }

    val typeData: LiveData<PagedList<TypeData>> = Transformations.switchMap(wrappedTypeData) {
        it.data
    }

    val state: LiveData<LoadState> = Transformations.switchMap(wrappedTypeData) {
        it.loadState
    }

    fun getTypeData(category: Utils.Category, type: String) {
        val coroutineContext = Dispatchers.IO + exceptionHandler.getHandler()
        typeDataRequest.value = TypeDataRequest(viewModelScope, coroutineContext, category, type)
    }

    fun getWeeklyPopular(category: Utils.Category, type: String,popularType: Utils.PopularType) {
        val coroutineContext = Dispatchers.IO + exceptionHandler.getHandler()
        typeDataRequest.value = TypeDataRequest(
            viewModelScope,
            coroutineContext,
            category,
            type,
            popularType
        )
    }


    fun refresh() {
        wrappedTypeData.value?.refresh?.invoke()
    }

    override fun onExceptionHandled() {

    }
}