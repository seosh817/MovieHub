package com.seosh817.moviehub.feature.movie_detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun MovieDetailRoute(
    modifier: Modifier = Modifier,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movieDetailUiState = movieDetailViewModel.movieDetailUiStateFlow.collectAsState()

    MovieDetailScreen(
        modifier = modifier,
        movieDetailUiState = movieDetailUiState.value
    )
}

@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailUiState: MovieDetailUiState
) {
    Box(modifier = modifier) {
        if (movieDetailUiState is MovieDetailUiState.Success) {
            Text("movie: ${movieDetailUiState.movieDetail.originalTitle}")
        }

    }
}