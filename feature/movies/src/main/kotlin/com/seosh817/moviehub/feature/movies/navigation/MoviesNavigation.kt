package com.seosh817.moviehub.feature.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.feature.movies.MoviesRoute

const val moviesNavigationRoute = "movies_route"
private const val DEEP_LINK_URI_PATTERN = "https://www.moviehub.seosh817.com/movies"

fun NavController.navigateToMovies(navOptions: NavOptions? = null) {
    navigate(moviesNavigationRoute, navOptions)
}

fun NavGraphBuilder.movieScreen(
    onMovieClick: (Long) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = moviesNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        MoviesRoute(
            onMovieClick = onMovieClick,
            onShowSnackbar = onShowSnackbar
        )
    }
}
