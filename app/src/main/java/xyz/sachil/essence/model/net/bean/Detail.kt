package xyz.sachil.essence.model.net.bean

import com.google.gson.annotations.SerializedName

data class Detail(@SerializedName("_id") val id: String,
                  @SerializedName("author") val author: String,
                  @SerializedName("category") val category: String,
                  @SerializedName("content") val content: String,
                  @SerializedName("createdAt") val createdDate: String,
                  @SerializedName("desc") val description: String,
                  @SerializedName("email") val email:String,
                  @SerializedName("images") val images: List<String>,
                  @SerializedName("index") val index: Int,
                  @SerializedName("isOriginal") val isOriginal: Boolean,
                  @SerializedName("license") val license: String,
                  @SerializedName("likeCount") val likeCount: Int,
                  @SerializedName("likeCounts") val likeCounts: Int,
                  @SerializedName("likes") val likes: List<String>,
                  @SerializedName("markdown") val markdown: String,
                  @SerializedName("originalAuthor") val originalAuthor: String,
                  @SerializedName("publishedAt") val publishedDate: String,
                  @SerializedName("stars") val starCount: Int,
                  @SerializedName("status") val status: Int,
                  @SerializedName("tags") val tags: List<String>,
                  @SerializedName("title") val title: String,
                  @SerializedName("type") val type: String,
                  @SerializedName("updatedAt") val updatedDate: String,
                  @SerializedName("url") val url: String,
                  @SerializedName("views") val viewCount: Int)
