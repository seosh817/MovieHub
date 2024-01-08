package com.seosh817.moviehub.core.database.converter

import androidx.room.TypeConverter

class LongListConverter {

    @TypeConverter
    fun fromLongList(longList: List<Long>?): String? {
        return longList?.joinToString(",")
    }

    @TypeConverter
    fun toLongList(longListString: String?): List<Long>? {
        return longListString?.split(",")?.map { it.toLong() }
    }
}
