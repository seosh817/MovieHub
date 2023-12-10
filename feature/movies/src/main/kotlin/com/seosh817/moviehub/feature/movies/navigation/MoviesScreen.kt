package com.seosh817.moviehub.feature.movies.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun MoviesRoute(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    MoviesScreen(
        onMovieClick = onMovieClick,
        modifier = modifier,
    )
}

@Composable
fun MoviesScreen(
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = "Movies")
    }
}