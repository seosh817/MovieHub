package com.seosh817.moviehub.feature.bookmarks

sealed class BookmarksUiEvent {
    data class ShowBookmarkedMessage(val isBookmarked: Boolean) : BookmarksUiEvent()

    data object ShowRefreshErrorMessage : BookmarksUiEvent()

    data object HideRefreshErrorMessage : BookmarksUiEvent()
}
