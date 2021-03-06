package xyz.sachil.essence.model.net.response

import com.google.gson.annotations.SerializedName
import xyz.sachil.essence.model.net.bean.WeeklyPopularData

class WeeklyPopularResponse(
        @SerializedName("category") val category: String,
        @SerializedName("hot") val hot: String,
) : BaseResponse<WeeklyPopularData>()