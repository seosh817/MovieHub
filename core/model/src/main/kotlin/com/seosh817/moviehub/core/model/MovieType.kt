package com.seosh817.moviehub.core.model

enum class MovieType(val value: String) {
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming"),
    NOW_PLAYING("now_playing"),
    UNKNOWN("unknown");

    companion object {
        fun fromValue(value: String): MovieType = when (value) {
            "popular" -> POPULAR
            "top_rated" -> TOP_RATED
            "upcoming" -> UPCOMING
            "now_playing" -> NOW_PLAYING
            else -> UNKNOWN
        }
    }
}
