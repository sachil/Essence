package xyz.sachil.essence.model.cache.dao

import androidx.paging.DataSource
import androidx.room.*
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.bean.WeeklyPopularData

@Dao
interface WeeklyPopularDataDao {

    @Insert
    fun insertWeeklyPopularData(list: List<WeeklyPopularData>)

    @Update
    fun updateWeeklyPopularData(vararg data: WeeklyPopularData)

    @Query("SELECT * FROM weekly_popular_table WHERE category=:category AND popular_type=:popularType ORDER BY views DESC")
    fun getWeeklyPopularByViews(
        category: String,
        popularType: String
    ): DataSource.Factory<Int, TypeData>

    @Query("SELECT * FROM weekly_popular_table WHERE category=:category AND popular_type=:popularType ORDER BY like_counts DESC")
    fun getWeeklyPopularByLikes(
        category: String,
        popularType: String
    ): DataSource.Factory<Int, TypeData>

    @Query("SELECT COUNT(id) FROM WEEKLY_POPULAR_TABLE WHERE category=:category AND popular_type=:popularType ")
    fun getCount(category: String, popularType: String): Int

    @Delete
    fun deleteWeeklyPopularData(vararg data: WeeklyPopularData)

    //用于删除weeklyPopular
    @Query("DELETE FROM weekly_popular_table WHERE category=:category AND popular_type=:popularType")
    fun deleteWeeklyPopularData(category: String,popularType: String)

    @Query("DELETE FROM weekly_popular_table")
    fun deleteAllData()

}