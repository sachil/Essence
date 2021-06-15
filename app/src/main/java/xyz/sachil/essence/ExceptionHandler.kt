package xyz.sachil.essence

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import java.net.UnknownHostException

class ExceptionHandler {

    val errorMessage = MutableLiveData<String>()

    //集中处理出现的异常
    fun getHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            val message = when (throwable) {
                is HttpException -> {
                    "网络异常"
                }
                is UnknownHostException -> {
                    "网络未连接"
                }
                else -> {
                    "未知错误"
                }
            }
            errorMessage.postValue(message)
        }
}