package com.seosh817.moviehub.core.datastore.impl

import android.util.Log
import androidx.datastore.core.DataStore
import com.seosh817.moviehub.core.datastore.UserPreferences
import com.seosh817.moviehub.core.datastore.DarkThemeModeProto
import com.seosh817.moviehub.core.datastore.copy
import com.seosh817.moviehub.core.datastore.source.AppPreferencesDataSource
import com.seosh817.moviehub.core.model.UserSettings
import com.seosh817.moviehub.core.model.DarkThemeMode
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppPreferencesDataSourceImpl @Inject constructor(
    private val appStartUpPreferences: DataStore<UserPreferences>,
) : AppPreferencesDataSource {
    override val userSettings = appStartUpPreferences.data
        .map {
            UserSettings(
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
        private val TAG: String = AppPreferencesDataSourceImpl::class.java.simpleName
    }
}