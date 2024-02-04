package com.seosh817.moviehub.feature.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.movies.BookmarkContents

@Composable
internal fun BookmarksRoute(
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = hiltViewModel(),
    onMovieClick: (MovieType, Long) -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
) {
    val moviePagingItems: LazyPagingItems<UserMovie> = viewModel.pagingMoviesStateFlow.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    BookmarksScreen(
        modifier = modifier,
        pagingItems = moviePagingItems,
        showRefreshError = viewModel.showRefreshError,
        isRefreshing = isRefreshing,
        onRefresh = {
            moviePagingItems.refresh()
        },
        onMovieClick = onMovieClick,
        onShowSnackbar = onShowSnackbar,
        showMessage = viewModel::showMessage,
        hideMessage = viewModel::hideMessage,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BookmarksScreen(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<UserMovie>,
    showRefreshError: Boolean = false,
    isRefreshing: Boolean,
    onMovieClick: (MovieType, Long) -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    onRefresh: () -> Unit,
    showMessage: () -> Unit,
    hideMessage: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

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
        )
    }
}