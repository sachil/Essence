package xyz.sachil.essence.model.cache.dao

import androidx.paging.DataSource
import androidx.room.*
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.util.Utils

@Dao
interface TypeDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypeData(typeDataList: List<TypeData>)

    @Query("SELECT * FROM type_data_table WHERE category=:category AND type=:type ORDER BY published_date DESC ")
    fun getTypeData(category: String, type: String): DataSource.Factory<Int, TypeData>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTypeData(vararg typeData: TypeData)

    @Query("SELECT COUNT(id) FROM type_data_table WHERE category=:category AND type=:type")
    fun getCount(category: String, type: String): Int

    @Delete
    fun deleteTypeData(vararg typeData: TypeData)

    @Query("DELETE FROM type_data_table WHERE category=:category AND type=:type")
    fun deleteTypeData(category: String, type: String)

    @Query("DELETE FROM type_data_table")
    fun deleteAllData()
}