package xyz.sachil.essence

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandler {

    val errorMessage = MutableLiveData<ErrorMessage<Int>>()

    //使用CoroutineExceptionHandler来集中处理可能出现的异常
    fun getHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            val message = when (throwable) {
                is SocketTimeoutException -> {
                    R.string.exception_timeout
                }
                is HttpException -> {
                    R.string.exception_http
                }
                is UnknownHostException -> {
                    R.string.exception_no_network
                }
                else -> {
                    R.string.exception_unknown
                }
            }
            errorMessage.postValue(ErrorMessage(message))
        }

    //用于规避livedata的粘性事件
    class ErrorMessage<out T>(private val message: T) {
        var hasBeenHandled = false
            private set

        fun getMessageIfNotHandled(): T? {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                message
            }
        }

        fun peekMessage(): T = message
    }
}