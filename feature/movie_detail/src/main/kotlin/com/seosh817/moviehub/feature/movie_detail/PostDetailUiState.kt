package com.seosh817.moviehub.feature.movie_detail

sealed interface PostDetailUiState {
    data object Loading : PostDetailUiState
    data object Success : PostDetailUiState
    data class Error(val e: Throwable) : PostDetailUiState
}