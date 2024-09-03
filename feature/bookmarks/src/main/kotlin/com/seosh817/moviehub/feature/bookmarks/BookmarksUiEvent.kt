package com.seosh817.moviehub.feature.bookmarks

sealed class BookmarksUiEvent {
    data class ShowBookmarkedMessage(val isBookmarked: Boolean, val id: Long) : BookmarksUiEvent()

    data object ShowRefreshErrorMessage : BookmarksUiEvent()

    data object HideRefreshErrorMessage : BookmarksUiEvent()
}
