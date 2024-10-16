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
package com.seosh817.moviehub.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.seosh817.moviehub.feature.bookmarks.navigation.BOOKMARKS_NAVIGATION_ROUTE
import com.seosh817.moviehub.feature.bookmarks.navigation.navigateToBookmarks
import com.seosh817.moviehub.feature.movies.navigation.MOVIES_NAVIGATION_ROUTE
import com.seosh817.moviehub.feature.movies.navigation.navigateToMovies
import com.seosh817.moviehub.feature.search.navigation.navigateToSearch
import com.seosh817.moviehub.feature.settings.navigation.navigateToSettings
import com.seosh817.moviehub.feature.viedo_player.VideoPlayerActivity
import com.seosh817.moviehub.feature.viedo_player.VideoPlayerActivity.Companion.VIDEO_ID
import com.seosh817.moviehub.navigation.PrimaryDestination.BOOKMARKS
import com.seosh817.moviehub.navigation.PrimaryDestination.MOVIES

class MovieHubNavigator(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentPrimaryDestination: PrimaryDestination?
        @Composable get() = when (currentDestination?.route) {
            MOVIES_NAVIGATION_ROUTE -> MOVIES
            BOOKMARKS_NAVIGATION_ROUTE -> BOOKMARKS
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

    fun navigateToSearch() {
        navController.navigateToSearch()
    }

    fun navigateToVideoPlayer(videoId: String) {
        Intent(navController.context, VideoPlayerActivity::class.java).apply {
            putExtra(VIDEO_ID, videoId)
        }.also {
            navController.context.startActivity(it)
        }
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
