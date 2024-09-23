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
package com.seosh817.moviehub.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.domain.repository.AppVersionsRepository
import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.AppVersions
import com.seosh817.moviehub.core.model.DarkThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    appVersionsRepository: AppVersionsRepository,
) : ViewModel() {

    val settingsUiState: StateFlow<SettingsUiState> =
        appPreferencesRepository.userSettings
            .map { settings ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        useDynamicColor = settings.useDynamicColor,
                        darkThemeMode = settings.darkThemeMode,
                        appLanguage = settings.appLanguage,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading,
            )

    private val _appVersionsStateFlow: MutableStateFlow<AppVersions> = MutableStateFlow(appVersionsRepository.getAppVersion())
    val appVersionsStateFlow: StateFlow<AppVersions> = _appVersionsStateFlow.asStateFlow()

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            appPreferencesRepository.setDynamicColorPreference(useDynamicColor)
        }
    }
}

data class UserEditableSettings(
    val useDynamicColor: Boolean,
    val darkThemeMode: DarkThemeMode,
    val appLanguage: AppLanguage,
)
