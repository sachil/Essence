package xyz.sachil.essence.util

object Utils {

    const val HOST = "gank.io"
    const val DATABASE_NAME="essence_data.db"
    const val SHARED_PREFERENCES_NAME = "essence_shared_preferences"

    enum class Category(val type: String) {
        ARTICLE("Article"),
        GAN_HUO("GanHuo"),
        GIRL("Girl"),
        WEEKLY_POPULAR("WeeklyPopular")
    }

    enum class PopularType(val type: String) {
        VIEWS("views"),
        LIKES("likes")
    }

}