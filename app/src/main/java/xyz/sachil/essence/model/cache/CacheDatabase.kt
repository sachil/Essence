package xyz.sachil.essence.model.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import xyz.sachil.essence.model.cache.dao.DetailDao
import xyz.sachil.essence.model.cache.dao.TypeDao
import xyz.sachil.essence.model.cache.dao.TypeDataDao
import xyz.sachil.essence.model.cache.dao.WeeklyPopularDataDao
import xyz.sachil.essence.model.net.bean.Detail
import xyz.sachil.essence.model.net.bean.Type
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.model.net.bean.WeeklyPopularData
import xyz.sachil.essence.util.Utils

@Database(
    version = 1, entities = [Type::class, TypeData::class, WeeklyPopularData::class, Detail::class],
    exportSchema = false
)
abstract class CacheDatabase() : RoomDatabase() {

    companion object {

        @Volatile
        private var instance: CacheDatabase? = null

        fun newInstance(context: Context): CacheDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                CacheDatabase::class.java,
                Utils.DATABASE_NAME
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }

    }

    abstract fun typeDao(): TypeDao

    abstract fun typeDataDao(): TypeDataDao

    abstract fun weeklyPopularDataDao(): WeeklyPopularDataDao

    abstract fun detailDao():DetailDao
}