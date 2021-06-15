package xyz.sachil.essence.model.net.bean

import com.google.gson.annotations.SerializedName

data class Comment(@SerializedName("_id") val id: String,
                   @SerializedName("userId") val userId: String,
                   @SerializedName("postId") val postId: String,
                   @SerializedName("userName") val userName: String,
                   @SerializedName("comment") val comment: String,
                   @SerializedName("headUrl") val headUrl: String,
                   @SerializedName("ups") val ups: Int,
                   @SerializedName("createdAt") val createdDate: String,
                   @SerializedName("secondParentId") val secondParentId: String,
                   @SerializedName("secondParentName") val secondParentName: String)