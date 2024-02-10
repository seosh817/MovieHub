package com.seosh817.moviehub.feature.movies

sealed class MoviesUiEvent {
    data class ShowBookmarkedMessage(val isBookmarked: Boolean) : MoviesUiEvent()

    data object ShowRefreshErrorMessage : MoviesUiEvent()

    data object HideRefreshErrorMessage : MoviesUiEvent()
}
