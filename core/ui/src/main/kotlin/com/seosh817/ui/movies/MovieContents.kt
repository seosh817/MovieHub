package com.seosh817.ui.movies

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.seosh817.moviehub.core.designsystem.theme.Dimens
import com.seosh817.moviehub.core.model.MovieOverview
import com.seosh817.ui.ContentsLoading

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieContents(
    modifier: Modifier,
    isRefreshing: Boolean,
    moviePagingItems: LazyPagingItems<MovieOverview>,
    lazyGridState: LazyGridState,
    pullRefreshState: PullRefreshState,
    onMovieClick: (Long) -> Unit,
    errorText: @Composable (String) -> Unit,
) {
    val context = LocalContext.current
    Log.d("!!!", "refresh: ${moviePagingItems.loadState.refresh}")
    Log.d("!!!", "mediator: ${moviePagingItems.loadState.mediator}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState,
            contentPadding = PaddingValues(horizontal = Dimens.dp_4, vertical = Dimens.dp_4),
        ) {
            items(
                count = moviePagingItems.itemCount,
                key = moviePagingItems.itemKey(),
                contentType = moviePagingItems.itemContentType()
            ) { index ->
                val movie: MovieOverview? = moviePagingItems[index]
                if (movie != null) {
                    MovieContentItem(
                        context = context,
                        movie = movie,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(240.dp)
                            .padding(Dimens.dp_4)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                onMovieClick.invoke(movie.id)
                            }
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = modifier,
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

//    when (moviePagingItems.loadState.mediator?.refresh) {
//        is LoadState.Loading -> {
//            ContentsLoading(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(
//                        MaterialTheme.colorScheme.background
//                    )
//            )
//        }
//
//        is LoadState.Error -> {
//            val text = (moviePagingItems.loadState.refresh as LoadState.Error).error.message ?: ""
//            errorText(text)
//        }
//
//        else -> {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .pullRefresh(pullRefreshState)
//            ) {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    state = lazyGridState,
//                    contentPadding = PaddingValues(horizontal = Dimens.dp_4, vertical = Dimens.dp_4),
//                ) {
//                    items(
//                        count = moviePagingItems.itemCount,
//                        key = moviePagingItems.itemKey(),
//                        contentType = moviePagingItems.itemContentType()
//                    ) { index ->
//                        val movie: MovieOverview? = moviePagingItems[index]
//                        if (movie != null) {
//                            MovieContentItem(
//                                context = context,
//                                movie = movie,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .height(240.dp)
//                                    .padding(Dimens.dp_4)
//                                    .clip(MaterialTheme.shapes.small)
//                                    .background(MaterialTheme.colorScheme.background)
//                                    .clickable {
//                                        onMovieClick.invoke(movie.id)
//                                    }
//                            )
//                        }
//                    }
//                }
//                PullRefreshIndicator(
//                    refreshing = isRefreshing,
//                    state = pullRefreshState,
//                    modifier = modifier,
//                    backgroundColor = Color.White,
//                    contentColor = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
}
