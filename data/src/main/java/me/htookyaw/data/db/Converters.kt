package me.htookyaw.data.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTimestamp(value: List<Int>): String {
        return value.joinToString { "$it" }
    }

    @TypeConverter
    fun dateToTimestamp(value: String): List<Int> {
        if (value.isEmpty()) {
            return emptyList()
        }
        return value.split(", ").mapNotNull { it.toIntOrNull() }
    }
}
