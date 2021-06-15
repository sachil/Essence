package xyz.sachil.essence.model.net.bean

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("_id") val id: String,
                        @SerializedName("author") val author: String,
                        @SerializedName("category") val category: String,
                        @SerializedName("content") val content: String,
                        @SerializedName("createdAt") val createdDate: String,
                        @SerializedName("desc") val description: String,
                        @SerializedName("images") val images: List<String>,
                        @SerializedName("likeCount") val likeCount: Int,
                        @SerializedName("likeCounts") val likeCounts: Int,
                        @SerializedName("markdown") val markdown: String,
                        @SerializedName("publishedAt") val publishedDate: String,
                        @SerializedName("stars") val starCount: Int,
                        @SerializedName("status") val status: Int,
                        @SerializedName("title") val title: String,
                        @SerializedName("type") val type: String,
                        @SerializedName("updatedAt") val updatedDate: String,
                        @SerializedName("url") val url: String,
                        @SerializedName("views") val viewCount: Int)
