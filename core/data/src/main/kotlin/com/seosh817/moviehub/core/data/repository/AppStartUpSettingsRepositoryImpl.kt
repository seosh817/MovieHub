package com.seosh817.moviehub.core.data.repository

import com.seosh817.moviehub.core.data.source.AppStartUpPreferencesDataSource
import com.seosh817.moviehub.core.domain.repository.AppStartUpSettingsRepository
import com.seosh817.moviehub.core.model.AppSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppStartUpSettingsRepositoryImpl @Inject constructor(
    private val appStartUpPreferencesDataSource: AppStartUpPreferencesDataSource
) : AppStartUpSettingsRepository {

    override val appSettings: Flow<AppSettings>
        get() = appStartUpPreferencesDataSource.appSettings

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        return appStartUpPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode) {
        return appStartUpPreferencesDataSource.setDarkThemeMode(darkThemeMode)
    }

    override suspend fun setBookMarkedMovieIds(movieIds: Set<Long>) {
        return appStartUpPreferencesDataSource.setBookMarkedMovieIds(movieIds)
    }
}