package com.seosh817.moviehub.feature.search

sealed interface SearchUiState {
    data object Loading : SearchUiState
    data object Success : SearchUiState
    data class Error(val e: Throwable) : SearchUiState
}