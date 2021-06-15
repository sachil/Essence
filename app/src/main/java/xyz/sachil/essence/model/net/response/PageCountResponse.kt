package xyz.sachil.essence.model.net.response

import com.google.gson.annotations.SerializedName

abstract class PageCountResponse<T> : BaseResponse<T>() {
    @SerializedName("page")
    val currentPage: Int = 0

    @SerializedName("page_count")
    val pageCount: Int = 0

    @SerializedName("total_counts")
    val totalCounts: Int = 0
}