package com.seosh817.moviehub.core.model

data class AppSettings(
    val useDynamicColor: Boolean,
    val darkThemeMode: DarkThemeMode,
    val bookmarkedMovieIds: Set<Long>,
)