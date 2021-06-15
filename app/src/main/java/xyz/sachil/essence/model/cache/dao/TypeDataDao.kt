package xyz.sachil.essence.model.cache.dao

import androidx.paging.DataSource
import androidx.room.*
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.util.Utils

@Dao
interface TypeDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypeData(typeDataList: List<TypeData>)

    @Query("SELECT * FROM type_data_table WHERE owner=:owner AND category=:category AND type=:type ORDER BY published_date DESC ")
    fun getTypeData(
        owner: String,
        category: String,
        type: String
    ): DataSource.Factory<Int, TypeData>

    //用于获取weeklyPopular
    @Query("SELECT * FROM type_data_table WHERE owner=:owner AND category=:category AND popular_type=:popularType")
    fun getWeeklyPopular(
        owner: String,
        category: String,
        popularType: String
    ): DataSource.Factory<Int, TypeData>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTypeData(vararg typeData: TypeData)

    @Query("SELECT COUNT(id) FROM type_data_table WHERE owner=:owner AND category=:category AND type=:type")
    fun getCount(owner: String, category: String, type: String): Int

    @Query("SELECT COUNT(id) FROM type_data_table WHERE owner=:owner AND category=:category AND popular_type=:popularType")
    fun test(owner: String, category: String, popularType: String): Int

    @Delete
    fun deleteTypeData(vararg typeData: TypeData)

    //用于删除weeklyPopular
    @Query("DELETE FROM type_data_table WHERE owner=:owner AND category=:category")
    fun deleteWeeklyPopular(owner: String, category: String)

    @Query("DELETE FROM type_data_table WHERE owner=:owner AND category=:category AND type=:type")
    fun deleteTypeData(owner: String, category: String, type: String)

    @Query("DELETE FROM type_data_table")
    fun deleteAllData()
}