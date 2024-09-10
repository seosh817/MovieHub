package com.seosh817.moviehub.feature.movie_detail

sealed interface MovieDetailUiEvent {
    data class ShowBookmarkedMessage(val isBookmarked: Boolean, val id: Long) : MovieDetailUiEvent
}
