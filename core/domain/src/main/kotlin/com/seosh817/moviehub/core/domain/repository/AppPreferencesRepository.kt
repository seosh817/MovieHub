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
package com.seosh817.moviehub.core.domain.repository

import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.moviehub.core.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    val userSettings: Flow<UserSettings>

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeMode(darkThemeMode: DarkThemeMode)

    suspend fun setBookMarkedMovieIds(movieId: Long, bookmarked: Boolean)

    suspend fun setAppLanguage(appLanguage: AppLanguage)
}
