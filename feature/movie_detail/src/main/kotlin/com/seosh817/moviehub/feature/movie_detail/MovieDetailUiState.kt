package com.seosh817.moviehub.feature.movie_detail

import com.seosh817.moviehub.core.model.MovieDetail

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(val movieDetail: MovieDetail) : MovieDetailUiState
    data class Error(val throwable: Throwable) : MovieDetailUiState
}