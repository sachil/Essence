package xyz.sachil.essence.model.net.response

import com.google.gson.annotations.SerializedName
import xyz.sachil.essence.model.net.bean.Detail

class DetailResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: Detail
)