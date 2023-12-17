package com.seosh817.moviehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.seosh817.moviehub.feature.movies.navigation.moviesGraph
import com.seosh817.moviehub.feature.movies.navigation.moviesNavigationRoute

@Composable
fun MovieHubNavHost(
    modifier: Modifier = Modifier,
    movieHubNavigator: MovieHubNavigator,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onMovieClick: (Long) -> Unit,
    startDestination: String = moviesNavigationRoute
) {
    NavHost(
        navController = movieHubNavigator.navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        moviesGraph(
            onMovieClick = onMovieClick,
            onShowSnackbar = onShowSnackbar
        )
    }
}