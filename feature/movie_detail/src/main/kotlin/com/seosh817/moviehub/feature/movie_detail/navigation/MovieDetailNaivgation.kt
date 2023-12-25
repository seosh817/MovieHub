package com.seosh817.moviehub.feature.movie_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.feature.movie_detail.MovieDetailRoute

internal const val movieIdArg = "movieId"
const val movieDetailNavigationRoute = "movie_route/{$movieIdArg}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.moviehub.seosh817.com/movie/{$movieIdArg}"

fun NavController.navigateToMovieDetail(movieId: Long) {
    navigate("movie_route/$movieId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.movieDetailScreen() {
    composable(
        route = movieDetailNavigationRoute,
        arguments = listOf(
            navArgument(movieIdArg) { type = NavType.LongType },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        MovieDetailRoute()
    }
}
