package com.seosh817.ui.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.seosh817.moviehub.core.designsystem.theme.Dimens
import com.seosh817.moviehub.core.model.Movie

@Composable
fun MovieContents(
    moviePagingItems: LazyPagingItems<Movie>,
    lazyGridState: LazyGridState,
    onMovieClick: (Long) -> Unit,
    errorText: @Composable (String) -> Unit,
    noImageText: @Composable () -> Unit
) {
    val context = LocalContext.current
    when (moviePagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            MovieContentsLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.background
                    )
            )
        }

        is LoadState.Error -> {
            val text = (moviePagingItems.loadState.refresh as LoadState.Error).error.message ?: ""
            errorText(text)
        }

        else -> {
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
                    val movie: Movie? = moviePagingItems[index]
                    if (movie != null) {
                        MovieContentItem(
                            context = context,
                            movie = movie,
                            noImageText = noImageText,
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
        }
    }
}
