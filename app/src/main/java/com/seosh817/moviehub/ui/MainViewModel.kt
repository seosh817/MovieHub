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
package com.seosh817.moviehub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.moviehub.core.domain.repository.AppPreferencesRepository
import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.DarkThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = appPreferencesRepository.userSettings
        .map {
            MainUiState.Success(it)
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = MainUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000),
        )

    fun updateDarkThemeMode(darkThemeMode: DarkThemeMode) {
        viewModelScope.launch {
            appPreferencesRepository.setDarkThemeMode(darkThemeMode)
        }
    }

    fun updateAppLanguage(appLanguage: AppLanguage) {
        viewModelScope.launch {
            appPreferencesRepository.setAppLanguage(appLanguage)
        }
    }
}
