package com.seosh817.moviehub.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seosh817.moviehub.core.designsystem.theme.Dimens
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.core.model.UserMovie
import com.seosh817.ui.movies.MovieContentItem
import com.seosh817.ui.search.SearchHeader

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    onMovieClick: (MovieType, Long) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val searchMovieItems by viewModel.moviePagingItems.collectAsState(initial = emptyList())
    var searchText by remember { mutableStateOf("") }

    SearchScreen(
        modifier = modifier,
        movieItems = searchMovieItems,
        searchText = searchText,
        onSearchTextChanged = {
            searchText = it
            viewModel.setQuery(it)
        },
        onMovieClick = {
            onMovieClick.invoke(MovieType.POPULAR, it)
        },
        onBackClick = onBackClick,
        onBookmarkClick = { movieId, isBookmarked ->

        }
    )
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    movieItems: List<UserMovie>,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onMovieClick: (Long) -> Unit,
    onBackClick: () -> Unit,
    onBookmarkClick: (Long, Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        SearchHeader(
            searchText = searchText,
            onSearchTextChange = onSearchTextChanged,
            onBackClick = onBackClick,
        )

        SearchContents(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp),
            movieItems = movieItems,
            onMovieClick = onMovieClick,
            onBookmarkClick = onBookmarkClick
        )
    }
}

@Composable
fun SearchContents(
    modifier: Modifier,
    movieItems: List<UserMovie>,
    onMovieClick: (Long) -> Unit,
    onBookmarkClick: (Long, Boolean) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = Dimens.dp_4, vertical = Dimens.dp_4),
        ) {
            items(movieItems) { userMovie ->
                MovieContentItem(
                    modifier = modifier
                        .fillMaxSize()
                        .height(240.dp)
                        .padding(Dimens.dp_4)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.background)
                        .clickable {
                            onMovieClick.invoke(userMovie.id)
                        },
                    context = context,
                    movie = userMovie,
                    onLikeClick = onBookmarkClick
                )
            }
        }
    }
}
