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
import xyz.sachil.essence.model.net.bean.Banner

@KoinApiExtension
class MainViewModel : BaseViewModel() {
    private val originalBanners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = Transformations.map(originalBanners) { it }

    fun getBanners() = viewModelScope.launch(Dispatchers.IO+exceptionHandler.getHandler()) {
        val result = NetClient.newInstance().getBanners()
        withContext(Dispatchers.Main) {
            originalBanners.value = result
        }
    }

    override fun onExceptionHandled() {

    }

}