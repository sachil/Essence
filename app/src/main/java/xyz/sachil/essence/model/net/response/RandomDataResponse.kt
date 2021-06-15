package xyz.sachil.essence.model.net.response

import com.google.gson.annotations.SerializedName
import xyz.sachil.essence.model.net.bean.TypeData

class RandomDataResponse(@SerializedName("counts") val counts: Int)
    : BaseResponse<TypeData>()