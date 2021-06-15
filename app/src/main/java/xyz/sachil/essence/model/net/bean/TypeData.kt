package xyz.sachil.essence.model.net.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import xyz.sachil.essence.model.cache.converter.ListConverter

@Entity(
    tableName = "type_data_table",
    primaryKeys = ["id"],
    indices = [Index(value = ["category", "type"])],
)
@TypeConverters(ListConverter::class)
data class TypeData(
    @ColumnInfo(name = "category") @SerializedName("category") val category: String,
    @ColumnInfo(name = "id") @SerializedName("_id") val id: String,
    @ColumnInfo(name = "author") @SerializedName("author") val author: String,
    @ColumnInfo(name = "created_date") @SerializedName("createdAt") val createdDate: String,
    @ColumnInfo(name = "description") @SerializedName("desc") val description: String,
    @ColumnInfo(name = "images") @SerializedName("images") val images: List<String>,
    @ColumnInfo(name = "like_counts") @SerializedName("likeCounts") val likeCounts: Int,
    @ColumnInfo(name = "published_date") @SerializedName("publishedAt") val publishedDate: String,
    @ColumnInfo(name = "stars") @SerializedName("stars") val starCount: Int,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "type") @SerializedName("type") val type: String,
    @ColumnInfo(name = "url") @SerializedName("url") val url: String,
    @ColumnInfo(name = "views") @SerializedName("views") val viewCounts: Int
) {
    @ColumnInfo(name = "owner")
    var owner: String = ""
    @ColumnInfo(name = "popular_type")
    var popularType: String? = null
}
