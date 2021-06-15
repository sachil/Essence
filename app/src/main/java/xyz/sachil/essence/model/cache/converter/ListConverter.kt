package xyz.sachil.essence.model.cache.converter

import androidx.room.TypeConverter
import java.lang.StringBuilder

class ListConverter {
    @TypeConverter
    fun listToString(list: List<String>): String {
        val stringBuilder = StringBuilder()
        list.forEach {
            stringBuilder.append(it).append(",")
        }
        if (stringBuilder.isNotEmpty()) {
            stringBuilder.removeSuffix(",")
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    fun stringToList(string: String): List<String> {
        return string.split(",")
    }
}