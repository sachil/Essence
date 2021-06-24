package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.ExceptionHandler
import kotlin.coroutines.CoroutineContext
import xyz.sachil.essence.ExceptionHandler.ErrorMessage

@KoinApiExtension
abstract class BaseViewModel : ViewModel(), KoinComponent {
    companion object {
        private const val TAG = "BaseViewModel"
    }

    protected val exceptionHandler: ExceptionHandler by inject()
    protected val coroutineContext: CoroutineContext =
        Dispatchers.IO + exceptionHandler.getHandler()
    val error: LiveData<ErrorMessage<Int>> = Transformations.map(exceptionHandler.errorMessage) {
        onExceptionHandled()
        it
    }

    abstract fun onExceptionHandled()
}