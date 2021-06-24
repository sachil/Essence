package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import xyz.sachil.essence.model.net.bean.Detail
import xyz.sachil.essence.repository.DetailRepository

@KoinApiExtension
class DetailViewModel : BaseViewModel() {
    private val internalDetail = MutableLiveData<Detail>()
    val detail: LiveData<Detail> = Transformations.map(internalDetail) { it }

    private val detailRepository: DetailRepository by inject()

    fun getContentDetail(id: String) = viewModelScope.launch(coroutineContext) {
        internalDetail.postValue(detailRepository.getDetail(id))
    }


    override fun onExceptionHandled() {

    }
}