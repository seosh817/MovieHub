package com.seosh817.moviehub.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.moviehub.core.domain.repository.AppStartUpSettingsRepository
import com.seosh817.moviehub.core.model.DarkThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appStartUpSettingsRepository: AppStartUpSettingsRepository
) : ViewModel() {

    val settingsUiState: StateFlow<SettingsUiState> =
        appStartUpSettingsRepository.appSettings
            .map { appSettings ->
                SettingsUiState.Success(
                    settings = UserEditableSettings(
                        useDynamicColor = appSettings.useDynamicColor,
                        darkThemeMode = appSettings.darkThemeMode,
                    ),
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading,
            )

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            appStartUpSettingsRepository.setDynamicColorPreference(useDynamicColor)
        }
    }

    fun updateDarkThemeMode(darkThemeMode: DarkThemeMode) {
        viewModelScope.launch {
            appStartUpSettingsRepository.setDarkThemeMode(darkThemeMode)
        }
    }
}

data class UserEditableSettings(
    val useDynamicColor: Boolean,
    val darkThemeMode: DarkThemeMode,
)