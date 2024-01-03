package com.seosh817.moviehub.feature.movie_detail

import com.seosh817.moviehub.core.model.MovieDetailResult

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(val movieDetailResult: MovieDetailResult) : MovieDetailUiState
    data class Error(val e: Throwable) : MovieDetailUiState
}