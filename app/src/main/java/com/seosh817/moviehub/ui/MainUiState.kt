package com.seosh817.moviehub.ui

import com.seosh817.moviehub.core.model.UserSettings

sealed interface MainUiState {
    data class Success(val userSettings: UserSettings) : MainUiState
    data object Loading : MainUiState
}
