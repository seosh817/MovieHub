package com.seosh817.moviehub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seosh817.moviehub.core.domain.repository.AppStartUpSettingsRepository
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
class MainViewModel @Inject constructor(
    private val appStartUpSettingsRepository: AppStartUpSettingsRepository
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = appStartUpSettingsRepository.appSettings
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
            appStartUpSettingsRepository.setDarkThemeMode(darkThemeMode)
        }
    }
}