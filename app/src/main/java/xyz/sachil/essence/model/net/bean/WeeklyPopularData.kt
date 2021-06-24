package xyz.sachil.essence.model.net.bean

import androidx.room.*
import xyz.sachil.essence.model.cache.converter.ListConverter

@Entity(
    tableName = "weekly_popular_table",
    indices = [Index(value = ["category", "type"])],
)
@TypeConverters(ListConverter::class)
class WeeklyPopularData : TypeData() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "row_id")
    var rowId: Int = 0

    @ColumnInfo(name = "popular_type")
    var popularType: String = ""
}
