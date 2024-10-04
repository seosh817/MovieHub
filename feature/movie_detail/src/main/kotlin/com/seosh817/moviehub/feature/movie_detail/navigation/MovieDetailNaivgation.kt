/*
 * Copyright 2024 seosh817 (Seunghwan Seo)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seosh817.moviehub.feature.movie_detail.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.feature.movie_detail.MovieDetailRoute

internal const val MOVIE_ID_ARG = "movieId"
internal const val MOVIE_TYPE_ARG = "movieType"
const val MOVIE_DETAIL_NAVIGATION_ROUTE = "movie_route/{$MOVIE_TYPE_ARG}/{$MOVIE_ID_ARG}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.moviehub.seosh817.com/movie/{$MOVIE_TYPE_ARG}/{$MOVIE_ID_ARG}"

fun NavController.navigateToMovieDetail(movieType: MovieType, movieId: Long) {
    navigate("movie_route/${movieType.value}/$movieId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.movieDetailScreen(
    onBackClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onTrailerClick: (String) -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
) {
    composable(
        route = MOVIE_DETAIL_NAVIGATION_ROUTE,
        arguments = listOf(
            navArgument(MOVIE_ID_ARG) { type = NavType.LongType },
            navArgument(MOVIE_TYPE_ARG) { type = NavType.StringType },
        ),
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        MovieDetailRoute(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onTrailerClick = onTrailerClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
