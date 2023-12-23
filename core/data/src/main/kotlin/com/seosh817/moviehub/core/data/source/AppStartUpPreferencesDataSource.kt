package com.seosh817.moviehub.core.data.source

import com.seosh817.moviehub.core.model.AppSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.Flow

interface AppStartUpPreferencesDataSource {

    val appSettings: Flow<AppSettings>

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode)

    suspend fun setBookMarkedMovieIds(movieIds: Set<String>)
}