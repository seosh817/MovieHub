package com.seosh817.moviehub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.seosh817.moviehub.feature.bookmarks.navigation.bookmarksNavigationRoute
import com.seosh817.moviehub.feature.bookmarks.navigation.navigateToBookmarks
import com.seosh817.moviehub.feature.movies.navigation.moviesNavigationRoute
import com.seosh817.moviehub.feature.movies.navigation.navigateToMovies
import com.seosh817.moviehub.feature.settings.navigation.navigateToSettings
import com.seosh817.moviehub.navigation.PrimaryDestination.BOOKMARKS
import com.seosh817.moviehub.navigation.PrimaryDestination.MOVIES

class MovieHubNavigator(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentPrimaryDestination: PrimaryDestination?
        @Composable get() = when (currentDestination?.route) {
            moviesNavigationRoute -> MOVIES
            bookmarksNavigationRoute -> BOOKMARKS
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentPrimaryDestination != null

    fun navigate(mainTab: PrimaryDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (mainTab) {
            MOVIES -> navController.navigateToMovies(navOptions)
            BOOKMARKS -> navController.navigateToBookmarks(navOptions)
        }
    }

    fun navigateToSettings() {
        navController.navigateToSettings()
    }
}

@Composable
internal fun rememberMovieHubNavigator(
    navController: NavHostController = rememberNavController(),
): MovieHubNavigator = remember(navController) {
    MovieHubNavigator(navController)
}

fun NavDestination?.isPrimaryDestinationInHierarchy(destination: PrimaryDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false