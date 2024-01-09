package com.seosh817.moviehub.core.domain.repository

import com.seosh817.moviehub.core.model.AppSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.Flow

interface AppStartUpSettingsRepository {

    val appSettings: Flow<AppSettings>

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode)

    suspend fun setBookMarkedMovieIds(movieId: Long, bookmarked: Boolean)
}