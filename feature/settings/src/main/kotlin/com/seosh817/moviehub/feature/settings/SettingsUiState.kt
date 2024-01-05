package com.seosh817.moviehub.feature.settings

sealed interface SettingsUiState {

        data object Loading : SettingsUiState

        data class Success(val settings: UserEditableSettings) : SettingsUiState
}