package com.seosh817.moviehub.core.model

data class UserSettings(
    val useDynamicColor: Boolean,
    val darkThemeMode: DarkThemeMode,
    val appLanguage: AppLanguage,
    val bookmarkedMovieIds: Set<Long>,
)