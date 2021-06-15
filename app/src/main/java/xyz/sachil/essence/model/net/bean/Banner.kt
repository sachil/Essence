package xyz.sachil.essence.model.net.bean

import com.google.gson.annotations.SerializedName

data class Banner(
        @SerializedName("image") val image: String,
        @SerializedName("url") val url: String,
        @SerializedName("title") val title: String
)