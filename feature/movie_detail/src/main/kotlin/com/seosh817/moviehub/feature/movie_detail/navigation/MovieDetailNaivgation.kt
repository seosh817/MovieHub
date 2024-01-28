package com.seosh817.moviehub.feature.movie_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.feature.movie_detail.MovieDetailRoute

internal const val movieIdArg = "movieId"
internal const val movieTypeArg = "movieType"
const val movieDetailNavigationRoute = "movie_route/{$movieTypeArg}/{$movieIdArg}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.moviehub.seosh817.com/movie/{$movieTypeArg}/{$movieIdArg}"

fun NavController.navigateToMovieDetail(movieType: MovieType, movieId: Long) {
    navigate("movie_route/${movieType.value}/$movieId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.movieDetailScreen(
    onBackClick: () -> Unit,
    onShareClick: (String) -> Unit,
) {
    composable(
        route = movieDetailNavigationRoute,
        arguments = listOf(
            navArgument(movieIdArg) { type = NavType.LongType },
            navArgument(movieTypeArg) { type = NavType.StringType }
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        MovieDetailRoute(
            onBackClick = onBackClick,
            onShareClick = onShareClick
        )
    }
}
