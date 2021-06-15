package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.model.net.NetClient
import xyz.sachil.essence.model.net.bean.Detail

@KoinApiExtension
class ContentDetailViewModel : BaseViewModel() {
    private val detail = MutableLiveData<Detail>()
    val contentDetail: LiveData<Detail> = Transformations.map(detail) { it }

    fun getContentDetail(id: String) =
        viewModelScope.launch(Dispatchers.Main + exceptionHandler.getHandler()) {
            val result = withContext(Dispatchers.IO) {
                NetClient.newInstance().getDetail(id)
            }
            detail.value = result
        }


    override fun onExceptionHandled() {

    }
}