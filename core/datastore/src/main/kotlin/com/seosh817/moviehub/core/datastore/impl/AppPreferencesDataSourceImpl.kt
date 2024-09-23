/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.core.datastore.impl

import android.util.Log
import androidx.datastore.core.DataStore
import com.seosh817.moviehub.core.datastore.AppLanguageProto
import com.seosh817.moviehub.core.datastore.DarkThemeModeProto
import com.seosh817.moviehub.core.datastore.UserPreferences
import com.seosh817.moviehub.core.datastore.copy
import com.seosh817.moviehub.core.datastore.source.AppPreferencesDataSource
import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.moviehub.core.model.UserSettings
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
                    DarkThemeModeProto.DARK_THEME_MODE_DARK,
                    -> DarkThemeMode.DARK
                },
                appLanguage = when (it.appLanguage) {
                    AppLanguageProto.APP_LANGUAGE_KOREAN -> AppLanguage.KOREAN
                    AppLanguageProto.APP_LANGUAGE_JAPANESE -> AppLanguage.JAPANESE
                    else -> AppLanguage.ENGLISH
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

    override suspend fun setAppLanguage(appLanguage: AppLanguage) {
        appStartUpPreferences.updateData {
            it.copy {
                this.appLanguage = when (appLanguage) {
                    AppLanguage.ENGLISH -> AppLanguageProto.APP_LANGUAGE_ENGLISH
                    AppLanguage.KOREAN -> AppLanguageProto.APP_LANGUAGE_KOREAN
                    AppLanguage.JAPANESE -> AppLanguageProto.APP_LANGUAGE_JAPANESE
                }
            }
        }
    }

    companion object {
        private val TAG: String = AppPreferencesDataSourceImpl::class.java.simpleName
    }
}
