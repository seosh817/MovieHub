package com.seosh817.moviehub.core.model

data class UserSettings(
    val useDynamicColor: Boolean,
    val darkThemeMode: DarkThemeMode,
    val bookmarkedMovieIds: Set<Long>,
)