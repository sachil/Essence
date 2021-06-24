package xyz.sachil.essence.model.net.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import xyz.sachil.essence.model.cache.converter.ListConverter

@Entity(
    tableName = "detail_table",
    primaryKeys = ["id"]
)
@TypeConverters(ListConverter::class)
data class Detail(
    @SerializedName("_id") val id: String,
    @SerializedName("author") val author: String?,
    @SerializedName("category") val category: String,
    @SerializedName("content") val content: String?,
    @ColumnInfo(name = "created_date")
    @SerializedName("createdAt") val createdDate: String?,
    @SerializedName("desc") val description: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("images") val images: List<String>?,
    @SerializedName("index") val index: Int?,
    @ColumnInfo(name = "is_original")
    @SerializedName("isOriginal") val isOriginal: Boolean?,
    @SerializedName("license") val license: String?,
    @ColumnInfo(name = "like_count")
    @SerializedName("likeCount") val likeCount: Int?,
    @ColumnInfo(name = "like_counts")
    @SerializedName("likeCounts") val likeCounts: Int?,
    @SerializedName("likes") val likes: List<String>?,
    @SerializedName("markdown") val markdown: String?,
    @ColumnInfo(name = "original_author")
    @SerializedName("originalAuthor") val originalAuthor: String?,
    @ColumnInfo(name = "published_date")
    @SerializedName("publishedAt") val publishedDate: String?,
    @ColumnInfo(name = "start_count")
    @SerializedName("stars") val starCount: Int?,
    @SerializedName("status") val status: Int?,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
    @ColumnInfo(name = "updated_date")
    @SerializedName("updatedAt") val updatedDate: String?,
    @SerializedName("url") val url: String?,
    @ColumnInfo(name = "view_counts")
    @SerializedName("views") val viewCounts: Int?
)
