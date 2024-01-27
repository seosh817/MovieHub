package com.seosh817.moviehub.feature.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.movies.MovieContents

@Composable
internal fun MoviesRoute(
    onMovieClick: (MovieType, Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val moviePagingItems: LazyPagingItems<UserMovie> = viewModel.pagingMoviesStateFlow.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    MoviesScreen(
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
fun MoviesScreen(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<UserMovie>,
    showRefreshError: Boolean = false,
    isRefreshing: Boolean,
    onMovieClick: (MovieType, Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onRefresh: () -> Unit,
    showMessage: () -> Unit,
    hideMessage: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )
    val moviesRefreshErrorMessage = stringResource(id = R.string.movies_refresh_error)
    val okText = stringResource(id = R.string.refresh)

    LaunchedEffect(showRefreshError) {
        if (showRefreshError) {
            val result = onShowSnackbar(moviesRefreshErrorMessage, okText)
            if (result) {
                onRefresh()
            } else {
                hideMessage()
            }
        }
    }

    if (pagingItems.loadState.refresh is LoadState.Error) {
        showMessage()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MovieContents(
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
