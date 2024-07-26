package com.seosh817.moviehub.core.datastore.source

import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.UserSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataSource {

    val userSettings: Flow<UserSettings>

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode)

    suspend fun setBookMarkedMovieIds(movieId: Long, bookmarked: Boolean)

    suspend fun setAppLanguage(appLanguage: AppLanguage)
}