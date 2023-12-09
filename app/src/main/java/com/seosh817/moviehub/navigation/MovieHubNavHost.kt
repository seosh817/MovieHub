package com.seosh817.moviehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.seosh817.moviehub.feature.movies.navigation.moviesNavigationRoute
import com.seosh817.moviehub.feature.movies.navigation.moviesGraph

@Composable
fun MovieHubNavHost(
    movieHubNavigator: MovieHubNavigator,
    onMovieClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = moviesNavigationRoute
) {
    NavHost(
        navController = movieHubNavigator.navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        moviesGraph(
            onMovieClick = onMovieClick
        )

    }
}