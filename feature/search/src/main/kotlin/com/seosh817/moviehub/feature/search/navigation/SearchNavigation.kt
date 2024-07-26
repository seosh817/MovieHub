package com.seosh817.moviehub.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.feature.search.SearchRoute

const val searchNavigationRoute = "search_route"
private const val DEEP_LINK_URI_PATTERN = "https://www.moviehub.seosh817.com/search"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(searchNavigationRoute, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = searchNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        SearchRoute(
            onBackClick = onBackClick,
        )
    }
}
