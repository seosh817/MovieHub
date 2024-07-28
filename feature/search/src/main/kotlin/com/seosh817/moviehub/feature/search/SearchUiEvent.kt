package com.seosh817.moviehub.feature.search

import com.seosh817.moviehub.core.model.UserMovie

sealed interface SearchUiEvent {

    data class OnQueryChanged(val query: String) : SearchUiEvent

    data object ClearSearchQuery : SearchUiEvent

    data class OnBookmarkClick(val userMovieId: Long, val isBookmark: Boolean) : SearchUiEvent

    data class ShowBookmarkedMessage(val isBookmarked: Boolean) : SearchUiEvent
}
