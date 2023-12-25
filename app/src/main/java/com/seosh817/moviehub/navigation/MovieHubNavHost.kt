package com.seosh817.moviehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.seosh817.moviehub.feature.movie_detail.navigation.movieDetailScreen
import com.seosh817.moviehub.feature.movie_detail.navigation.navigateToMovieDetail
import com.seosh817.moviehub.feature.movies.navigation.movieScreen
import com.seosh817.moviehub.feature.movies.navigation.moviesNavigationRoute

@Composable
fun MovieHubNavHost(
    modifier: Modifier = Modifier,
    movieHubNavigator: MovieHubNavigator,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    startDestination: String = moviesNavigationRoute
) {
    val navController = movieHubNavigator.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        movieScreen(
            onMovieClick = navController::navigateToMovieDetail,
            onShowSnackbar = onShowSnackbar
        )
        movieDetailScreen()
    }
}