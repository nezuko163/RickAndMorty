package com.nezuko.data.db

import androidx.room.TypeConverter

class Converters {
    private val SEPARATOR = " "

    @TypeConverter
    fun fromStringList(list: List<String>?): String? =
        list?.joinToString(separator = SEPARATOR)

    @TypeConverter
    fun toStringList(value: String?): List<String> =
        value
            ?.takeIf { it.isNotEmpty() }
            ?.split(SEPARATOR)
            ?: emptyList()
}