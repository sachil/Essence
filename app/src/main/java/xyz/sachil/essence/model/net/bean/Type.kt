package xyz.sachil.essence.model.net.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "type_table")
data class Type(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "category") var category: String = "",
    @ColumnInfo(name = "type_id") @SerializedName("_id") val typeId: String,
    @ColumnInfo(name = "cover_image_url") @SerializedName("coverImageUrl") val imageUrl: String,
    @ColumnInfo(name = "description") @SerializedName("desc") val description: String,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "type") @SerializedName("type") val type: String
)
