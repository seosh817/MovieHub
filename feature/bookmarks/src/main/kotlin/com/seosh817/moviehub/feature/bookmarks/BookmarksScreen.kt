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
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.movies.BookmarkContents

@Composable
internal fun BookmarksRoute(
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = hiltViewModel(),
    onMovieClick: (MovieType, Long) -> Unit,
) {
    val moviePagingItems: LazyPagingItems<UserMovie> = viewModel.pagingMoviesStateFlow.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    BookmarksScreen(
        modifier = modifier,
        pagingItems = moviePagingItems,
        isRefreshing = isRefreshing,
        onRefresh = {
            moviePagingItems.refresh()
        },
        onMovieClick = onMovieClick,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BookmarksScreen(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<UserMovie>,
    isBookmarked: Boolean = false,
    isRefreshing: Boolean,
    onMovieClick: (MovieType, Long) -> Unit,
    onRefresh: () -> Unit,
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