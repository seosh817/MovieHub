package com.seosh817.moviehub.ui

import com.seosh817.moviehub.core.model.AppSettings

sealed interface MainUiState {
    data class Success(val appSettings: AppSettings) : MainUiState
    data object Loading : MainUiState
}