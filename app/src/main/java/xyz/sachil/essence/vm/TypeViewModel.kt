package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject
import xyz.sachil.essence.model.net.bean.Type
import xyz.sachil.essence.repository.TypeRepository
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class TypeViewModel : BaseViewModel() {

    private val typeRepository: TypeRepository by inject()
    private val internalTypes = MutableLiveData<List<Type>>()
    val types: LiveData<List<Type>> = Transformations.map(internalTypes) { it }

    fun getTypes(category: Utils.Category) =
        viewModelScope.launch(Dispatchers.IO + exceptionHandler.getHandler()) {
            val result = typeRepository.getTypes(category)
            internalTypes.postValue(result)
        }

    override fun onExceptionHandled() {

    }

}