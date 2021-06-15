package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import xyz.sachil.essence.ExceptionHandler

@KoinApiExtension
abstract class BaseViewModel : ViewModel(), KoinComponent {
    companion object {
        private const val TAG = "BaseViewModel"
    }

    protected val exceptionHandler: ExceptionHandler by inject()
    val error: LiveData<String> = Transformations.map(exceptionHandler.errorMessage) {
        onExceptionHandled()
        it
    }

    abstract fun onExceptionHandled()
}