package com.seosh817.moviehub.feature.movies

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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.moviehub.core.model.state.PostBookmarkUiState
import com.seosh817.ui.ContentsLoading
import com.seosh817.ui.movies.MovieContents
import kotlinx.coroutines.cancel

@Composable
internal fun MoviesRoute(
    onMovieClick: (MovieType, Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
) {
    val moviePagingItems: LazyPagingItems<UserMovie> = viewModel.pagingMoviesStateFlow.collectAsLazyPagingItems()
    val postBookmarkUiState by viewModel.postBookmarkUiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val moviesUiEvent by viewModel.moviesUiEvent.collectAsStateWithLifecycle(initialValue = null)

    MoviesScreen(
        modifier = modifier,
        pagingItems = moviePagingItems,
        postBookmarkUiState = postBookmarkUiState,
        moviesUiEvent = moviesUiEvent,
        isRefreshing = isRefreshing,
        onBookmarkClick = viewModel::onBookmarkClick,
        onMovieClick = onMovieClick,
        onShowSnackbar = onShowSnackbar,
        onRefresh = {
            moviePagingItems.refresh()
        },
        showMessage = viewModel::showMessage,
        hideMessage = viewModel::hideMessage,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<UserMovie>,
    postBookmarkUiState: PostBookmarkUiState,
    moviesUiEvent: MoviesUiEvent?,
    isRefreshing: Boolean,
    onBookmarkClick: (Long, Boolean) -> Unit,
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

    val moviesRefreshErrorMessage = stringResource(id = R.string.movies_refresh_error)
    val refreshText = stringResource(id = R.string.refresh)

    val bookmarkedSuccessMessage = stringResource(id = R.string.bookmarked_success)
    val bookmarkedFailedMessage = stringResource(id = R.string.bookmarked_failed)
    val okText = stringResource(id = R.string.ok)

    LaunchedEffect(moviesUiEvent) {
        when (moviesUiEvent) {
            is MoviesUiEvent.ShowBookmarkedMessage -> {
                if (moviesUiEvent.isBookmarked) {
                    onShowSnackbar(bookmarkedSuccessMessage, okText, SnackbarDuration.Short)
                } else {
                    onShowSnackbar(bookmarkedFailedMessage, okText, SnackbarDuration.Short)
                }
            }

            is MoviesUiEvent.ShowRefreshErrorMessage -> {
                val result = onShowSnackbar(moviesRefreshErrorMessage, refreshText, SnackbarDuration.Indefinite)
                if (result) {
                    pagingItems.refresh()
                }
            }

            is MoviesUiEvent.HideRefreshErrorMessage -> {
                cancel()
            }

            else -> {}
        }
    }

    if (pagingItems.loadState.refresh is LoadState.Error) {
        showMessage()
    } else if (pagingItems.loadState.refresh is LoadState.Loading) {
        hideMessage()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MovieContents(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            moviePagingItems = pagingItems,
            lazyGridState = lazyGridState,
            onMovieClick = {
                onMovieClick(MovieType.POPULAR, it)
            },
            pullRefreshState = pullRefreshState,
            onRefresh = onRefresh,
            onBookmarkClick = onBookmarkClick
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
