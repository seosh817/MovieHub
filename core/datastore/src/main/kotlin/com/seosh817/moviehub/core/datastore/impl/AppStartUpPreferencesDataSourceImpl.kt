package com.seosh817.moviehub.core.datastore.impl

import android.util.Log
import androidx.datastore.core.DataStore
import com.seosh817.moviehub.core.datastore.AppStartUpSettings
import com.seosh817.moviehub.core.datastore.DarkThemeModeProto
import com.seosh817.moviehub.core.datastore.copy
import com.seosh817.moviehub.core.datastore.source.AppStartUpPreferencesDataSource
import com.seosh817.moviehub.core.model.AppSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppStartUpPreferencesDataSourceImpl @Inject constructor(
    private val appStartUpPreferences: DataStore<AppStartUpSettings>,
) : AppStartUpPreferencesDataSource {
    override val appSettings = appStartUpPreferences.data
        .map {
            AppSettings(
                useDynamicColor = it.useDynamicColor,
                darkThemeMode = when (it.darkThemeMode) {
                    DarkThemeModeProto.DARK_THEME_MODE_FOLLOW_SYSTEM -> DarkThemeMode.FOLLOW_SYSTEM
                    DarkThemeModeProto.DARK_THEME_MODE_LIGHT -> DarkThemeMode.LIGHT
                    null,
                    DarkThemeModeProto.DARK_THEME_MODE_UNSPECIFIED,
                    DarkThemeModeProto.UNRECOGNIZED,
                    DarkThemeModeProto.DARK_THEME_MODE_DARK -> DarkThemeMode.DARK
                },
                bookmarkedMovieIds = it.bookmarkedMovieIdsMap.keys,
            )
        }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        appStartUpPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }

    override suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode) {
        appStartUpPreferences.updateData {
            it.copy {
                this.darkThemeMode = when (darkThemeMode) {
                    DarkThemeMode.LIGHT -> DarkThemeModeProto.DARK_THEME_MODE_LIGHT
                    DarkThemeMode.DARK -> DarkThemeModeProto.DARK_THEME_MODE_DARK
                    else -> DarkThemeModeProto.DARK_THEME_MODE_FOLLOW_SYSTEM
                }
            }
        }
    }

    override suspend fun setBookMarkedMovieIds(movieId: Long, bookmarked: Boolean) {
        try {
            appStartUpPreferences.updateData {
                it.copy {
                    if (bookmarked) {
                        bookmarkedMovieIds.put(movieId, true)
                    } else {
                        bookmarkedMovieIds.remove(movieId)
                    }
                }
            }
        } catch (exception: IOException) {
            Log.e(TAG, "Failed to update app settings", exception)
        }
    }

    companion object {
        private val TAG: String = AppStartUpPreferencesDataSourceImpl::class.java.simpleName
    }
}