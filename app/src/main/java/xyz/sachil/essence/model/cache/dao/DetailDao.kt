package xyz.sachil.essence.model.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import xyz.sachil.essence.model.net.bean.Detail

@Dao
interface DetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(detail: Detail)

    @Query("SELECT * FROM detail_table WHERE id=:id")
    fun getDetail(id: String): Detail

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDetail(detail: Detail)

    @Delete
    fun deleteDetail(detail: Detail)

    @Query("DELETE FROM detail_table")
    fun deleteAllDetail()

}