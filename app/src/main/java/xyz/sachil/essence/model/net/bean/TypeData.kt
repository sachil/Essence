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
open class TypeData {
    @ColumnInfo(name = "category")
    @SerializedName("category")
    var category: String = ""

    @ColumnInfo(name = "id")
    @SerializedName("_id")
    var id: String = ""

    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String = ""

    @ColumnInfo(name = "created_date")
    @SerializedName("createdAt")
    var createdDate: String = ""

    @ColumnInfo(name = "description")
    @SerializedName("desc")
    var description: String = ""

    @ColumnInfo(name = "images")
    @SerializedName("images")
    var images: List<String> = emptyList()

    @ColumnInfo(name = "like_counts")
    @SerializedName("likeCounts")
    var likeCounts: Int = 0

    @ColumnInfo(name = "published_date")
    @SerializedName("publishedAt")
    var publishedDate: String = ""

    @ColumnInfo(name = "stars")
    @SerializedName("stars")
    var starCount: Int = 0

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String = ""

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String = ""

    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String = ""

    @ColumnInfo(name = "views")
    @SerializedName("views")
    var viewCounts: Int = 0
}
