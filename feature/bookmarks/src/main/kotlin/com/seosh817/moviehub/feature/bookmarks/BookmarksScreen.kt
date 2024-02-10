package com.seosh817.moviehub.feature.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.moviehub.core.model.state.PostBookmarkUiState
import com.seosh817.ui.ContentsLoading
import com.seosh817.ui.movies.BookmarkContents
import kotlinx.coroutines.cancel

@Composable
internal fun BookmarksRoute(
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = hiltViewModel(),
    onMovieClick: (MovieType, Long) -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
) {
    val moviePagingItems: LazyPagingItems<UserMovie> = viewModel.pagingMoviesStateFlow.collectAsLazyPagingItems()
    val postBookmarkUiState by viewModel.postBookmarkUiState.collectAsStateWithLifecycle()
    val bookmarksUiEvent by viewModel.bookmarksUiEvent.collectAsStateWithLifecycle(initialValue = null)
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    BookmarksScreen(
        modifier = modifier,
        pagingItems = moviePagingItems,
        postBookmarkUiState = postBookmarkUiState,
        bookmarksUiEvent = bookmarksUiEvent,
        isRefreshing = isRefreshing,
        onShowSnackbar = onShowSnackbar,
        onRefresh = {
            moviePagingItems.refresh()
        },
        onMovieClick = onMovieClick,
        onBookmarkClick = viewModel::onBookmarkClick
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BookmarksScreen(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<UserMovie>,
    postBookmarkUiState: PostBookmarkUiState,
    bookmarksUiEvent: BookmarksUiEvent?,
    isRefreshing: Boolean,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    onMovieClick: (MovieType, Long) -> Unit,
    onRefresh: () -> Unit,
    onBookmarkClick: (Long, Boolean) -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    val moviesRefreshErrorMessage = stringResource(id = R.string.movies_refresh_error)
    val refreshText = stringResource(id = R.string.refresh)

    val bookmarkedSuccessMessage = stringResource(id = R.string.bookmarked_success)
    val bookmarkedFailedMessage = stringResource(id = R.string.bookmarked_failed)
    val okText = stringResource(id = R.string.ok)

    LaunchedEffect(bookmarksUiEvent) {
        when (bookmarksUiEvent) {
            is BookmarksUiEvent.ShowBookmarkedMessage -> {
                if (bookmarksUiEvent.isBookmarked) {
                    onShowSnackbar(bookmarkedSuccessMessage, okText, SnackbarDuration.Short)
                } else {
                    onShowSnackbar(bookmarkedFailedMessage, okText, SnackbarDuration.Short)
                }
            }

            is BookmarksUiEvent.ShowRefreshErrorMessage -> {
                val result = onShowSnackbar(moviesRefreshErrorMessage, refreshText, SnackbarDuration.Indefinite)
                if (result) {
                    pagingItems.refresh()
                }
            }

            is BookmarksUiEvent.HideRefreshErrorMessage -> {
                cancel()
            }

            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BookmarkContents(
            isRefreshing = isRefreshing,
            moviePagingItems = pagingItems,
            lazyGridState = lazyGridState,
            modifier = Modifier.align(Alignment.TopCenter),
            onMovieClick = {
                onMovieClick(MovieType.POPULAR, it)
            },
            pullRefreshState = pullRefreshState,
            onRefresh = onRefresh,
            onLikeClick = onBookmarkClick
        )
    }

    if (postBookmarkUiState is PostBookmarkUiState.Loading) {
        Box(Modifier.fillMaxSize()) {
            ContentsLoading(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}