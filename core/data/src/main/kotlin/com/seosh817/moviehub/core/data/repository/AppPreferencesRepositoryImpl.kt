package com.seosh817.moviehub.core.data.repository

import com.seosh817.moviehub.core.datastore.source.AppPreferencesDataSource
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.UserSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferencesDataSource: AppPreferencesDataSource
) : AppPreferencesRepository {

    override val userSettings: Flow<UserSettings>
        get() = appPreferencesDataSource.userSettings

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        return appPreferencesDataSource.setDynamicColorPreference(useDynamicColor)
    }

    override suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode) {
        return appPreferencesDataSource.setDarkThemeMode(darkThemeMode)
    }

    override suspend fun setBookMarkedMovieIds(movieId: Long, bookmarked: Boolean) {
        return appPreferencesDataSource.setBookMarkedMovieIds(movieId, bookmarked)
    }

    override suspend fun setAppLanguage(appLanguage: AppLanguage) {
        return appPreferencesDataSource.setAppLanguage(appLanguage)
    }
}
