package com.seosh817.moviehub.feature.bookmarks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.feature.bookmarks.BookmarksRoute

const val bookmarksNavigationRoute = "bookmarks_route"
private const val DEEP_LINK_URI_PATTERN = "https://www.moviehub.seosh817.com/bookmarks"

fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    navigate(bookmarksNavigationRoute, navOptions)
}

fun NavGraphBuilder.bookmarksScreen() {
    composable(
        route = bookmarksNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        BookmarksRoute()
    }
}