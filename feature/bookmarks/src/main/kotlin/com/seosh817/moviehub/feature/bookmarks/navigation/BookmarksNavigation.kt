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
package com.seosh817.moviehub.feature.bookmarks.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.core.model.MovieType
import com.seosh817.moviehub.feature.bookmarks.BookmarksRoute

const val BOOKMARKS_NAVIGATION_ROUTE = "bookmarks_route"
private const val DEEP_LINK_URI_PATTERN = "https://www.moviehub.seosh817.com/bookmarks"

fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    navigate(BOOKMARKS_NAVIGATION_ROUTE, navOptions)
}

fun NavGraphBuilder.bookmarksScreen(
    onMovieClick: (MovieType, Long) -> Unit,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
) {
    composable(
        route = BOOKMARKS_NAVIGATION_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        BookmarksRoute(
            onMovieClick = onMovieClick,
            onShowSnackbar = onShowSnackbar,
        )
    }
}
