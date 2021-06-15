package xyz.sachil.essence.model.net.response

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<T>() {
    @SerializedName("status")
    var status: Int = 0

    @SerializedName("data")
    var data: List<T> = emptyList()
}