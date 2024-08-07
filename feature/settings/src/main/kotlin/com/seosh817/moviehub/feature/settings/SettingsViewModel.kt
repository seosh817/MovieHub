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
    appVersionsRepository: AppVersionsRepository
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