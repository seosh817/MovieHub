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
package com.seosh817.moviehub.core.model

enum class MovieType(val value: String) {
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming"),
    NOW_PLAYING("now_playing"),
    UNKNOWN("unknown"),
    ;

    companion object {
        fun fromValue(value: String): MovieType =
            when (value) {
                "popular" -> POPULAR
                "top_rated" -> TOP_RATED
                "upcoming" -> UPCOMING
                "now_playing" -> NOW_PLAYING
                else -> UNKNOWN
            }
    }
}
