package com.seosh817.moviehub.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.seosh817.moviehub.core.designsystem.component.SearchTextField
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.ContentsLoading
import com.seosh817.ui.error.ConfirmErrorContents
import com.seosh817.ui.search.SearchRowItem

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    onMovieClick: (MovieType, Long) -> Unit,
    onBackClick: () -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val searchPagingItems: LazyPagingItems<UserMovie> = viewModel.searchImagePagingItems.collectAsLazyPagingItems()
    val query by viewModel.queryStateFlow.collectAsState()

    SearchScreen(
        modifier = modifier,
        searchPagingItems = searchPagingItems,
        query = query,
        onTextChanged = {
            viewModel.sendEvent(SearchUiEvent.OnQueryChanged(it))
        },
        onClearIconClick = {
            viewModel.sendEvent(SearchUiEvent.ClearSearchQuery)
        },
        onShowSnackbar = onShowSnackbar,
        onMovieClick = {
            onMovieClick(MovieType.POPULAR, it)
        },
        onBookmarkClick = { userMovieId, bookmarked ->
            viewModel.sendEvent(SearchUiEvent.OnBookmarkClick(userMovieId, bookmarked))
        },
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchPagingItems: LazyPagingItems<UserMovie>,
    query: String,
    onTextChanged: (String) -> Unit = {},
    onClearIconClick: () -> Unit = {},
    onMovieClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    onBookmarkClick: (Long, Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        SearchTextField(
            modifier = modifier.fillMaxWidth(),
            value = query,
            hint = stringResource(id = R.string.enter_keyword),
            onValueChanged = onTextChanged,
            onClickClearKeyword = onClearIconClick
        )

        SearchContents(
            modifier = Modifier
                .fillMaxSize(),
            query = query,
            searchMovieItems = searchPagingItems,
            onMovieClick = onMovieClick,
            onBookmarkClick = onBookmarkClick
        )
    }
}

@Composable
fun SearchContents(
    modifier: Modifier,
    query: String,
    searchMovieItems: LazyPagingItems<UserMovie>,
    onMovieClick: (Long) -> Unit,
    onBookmarkClick: (Long, Boolean) -> Unit
) {

    val lazyListState = rememberLazyListState()

    if (query.isEmpty()) {
        ConfirmErrorContents(
            modifier = modifier.fillMaxSize(),
            message = stringResource(id = R.string.search_empty),
        )
    } else {
        when (searchMovieItems.loadState.refresh) {
            is LoadState.Loading -> {
                ContentsLoading()
            }

            is LoadState.Error -> {
                val error = (searchMovieItems.loadState.refresh as LoadState.Error).error
                val errorMessage: String
                if (error is NoSuchElementException) {
                    errorMessage = stringResource(id = R.string.no_search_result)
                    ConfirmErrorContents(
                        modifier = modifier.fillMaxSize(),
                        message = errorMessage,
                    )
                } else {
                    errorMessage = error.message ?: stringResource(id = R.string.refresh_error)
                    ConfirmErrorContents(
                        modifier = modifier.fillMaxSize(),
                        cause = errorMessage,
                    )
                }
            }

            is LoadState.NotLoading -> {
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        horizontal = AppDimens.PaddingSmall,
                        vertical = AppDimens.PaddingSmall
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppDimens.PaddingSmall),
                ) {
                    items(
                        count = searchMovieItems.itemCount,
                        key = searchMovieItems.itemKey(),
                        contentType = searchMovieItems.itemContentType()
                    ) { index ->
                        val userMovie: UserMovie? = searchMovieItems[index]
                        if (userMovie != null) {
                            SearchRowItem(
                                modifier = modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.small)
                                    .background(MaterialTheme.colorScheme.background)
                                    .clickable {
                                        onMovieClick.invoke(userMovie.id)
                                    },
                                userMovie = userMovie,
                                onClickBookmark = {
                                    onBookmarkClick.invoke(userMovie.id, userMovie.isBookmarked)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
