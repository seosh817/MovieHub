/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
