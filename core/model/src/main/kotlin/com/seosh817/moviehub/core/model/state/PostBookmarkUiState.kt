package com.seosh817.moviehub.core.model.state

sealed interface PostBookmarkUiState {
    data object Loading : PostBookmarkUiState
    data object Success : PostBookmarkUiState
    data class Error(val e: Throwable) : PostBookmarkUiState
}
