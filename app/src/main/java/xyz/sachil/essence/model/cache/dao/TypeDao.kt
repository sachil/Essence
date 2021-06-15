package xyz.sachil.essence.model.cache.dao

import androidx.room.*
import xyz.sachil.essence.model.net.bean.Type

@Dao
interface TypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypes(types: List<Type>)

    @Query("SELECT * FROM type_table WHERE category=:category")
    fun getTypes(category: String): List<Type>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTypes(vararg type: Type)

    @Query("DELETE FROM type_table")
    fun deleteAllData()

    @Delete
    fun deleteTypes(vararg type: Type)

}