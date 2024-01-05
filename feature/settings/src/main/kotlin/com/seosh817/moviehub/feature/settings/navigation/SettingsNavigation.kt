package com.seosh817.moviehub.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.seosh817.moviehub.feature.settings.SettingsRoute

const val settingsNavigationRoute = "settings_route"
private const val DEEP_LINK_URI_PATTERN = "https://www.moviehub.seosh817.com/settings"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = settingsNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
    ) {
        SettingsRoute(
            onBackClick = onBackClick
        )
    }
}
