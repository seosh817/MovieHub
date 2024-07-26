package com.seosh817.moviehub.core.database.converter

import androidx.room.TypeConverter

class LongListConverter {

    @TypeConverter
    fun fromLongList(longList: List<Long>?): String? {
        return longList?.joinToString(DELIMITER)
    }

    @TypeConverter
    fun toLongList(longListString: String?): List<Long>? {
        return longListString?.let {
            if (it.isNotEmpty()) {
                it
                    .split(DELIMITER)
                    .map { str ->
                        str
                            .trim()
                            .toLong()
                    }
            } else {
                null
            }
        }
    }

    companion object {
        private const val DELIMITER = ","
    }
}
