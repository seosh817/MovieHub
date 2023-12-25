package com.seosh817.moviehub.feature.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.seosh817.moviehub.core.model.Movie
import com.seosh817.ui.movies.MovieContents

@Composable
internal fun MoviesRoute(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    val moviePagingItems: LazyPagingItems<Movie> = viewModel.pagingMoviesStateFlow.collectAsLazyPagingItems()

    MoviesScreen(
        modifier = modifier,
        pagingItems = moviePagingItems,
        showRefreshError = viewModel.showRefreshError,
        onMovieClick = onMovieClick,
        onShowSnackbar = onShowSnackbar,
        refreshItems = viewModel::refreshItems,
        showMessage = viewModel::showMessage,
        hideMessage = viewModel::hideMessage,
    )
}

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<Movie>,
    showRefreshError: Boolean = false,
    onMovieClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    refreshItems: () -> Unit,
    showMessage: () -> Unit,
    hideMessage: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val moviesRefreshErrorMessage = stringResource(id = R.string.movies_refresh_error)
    val okText = stringResource(id = R.string.ok)

    LaunchedEffect(showRefreshError) {
        if (showRefreshError) {
            val result = onShowSnackbar(moviesRefreshErrorMessage, okText)
            if (result) {
                refreshItems()
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
    ) {
        MovieContents(
            moviePagingItems = pagingItems,
            lazyGridState = lazyGridState,
            errorText = { text ->
                Text(
                    text = text,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xffadb1bb),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
            },
            onMovieClick = onMovieClick,
        )
    }
}

@Composable
fun LoadingColumn(title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title)
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .padding(top = 16.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingColumnPreview() {
    LoadingColumn(title = "Please wait...")
}
