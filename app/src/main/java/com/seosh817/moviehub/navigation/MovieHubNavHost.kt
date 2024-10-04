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

import android.app.Activity
import android.content.Intent
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.navigation.compose.NavHost
import com.seosh817.moviehub.R
import com.seosh817.moviehub.core.model.OpenDialog
import com.seosh817.moviehub.feature.bookmarks.navigation.bookmarksScreen
import com.seosh817.moviehub.feature.movie_detail.navigation.movieDetailScreen
import com.seosh817.moviehub.feature.movie_detail.navigation.navigateToMovieDetail
import com.seosh817.moviehub.feature.movies.navigation.MOVIES_NAVIGATION_ROUTE
import com.seosh817.moviehub.feature.movies.navigation.movieScreen
import com.seosh817.moviehub.feature.search.navigation.searchScreen
import com.seosh817.moviehub.feature.settings.navigation.settingsScreen
import com.seosh817.moviehub.feature.viedo_player.navigation.navigateToVideoPlayer
import com.seosh817.moviehub.feature.viedo_player.navigation.videoPlayerScreen

@Composable
fun MovieHubNavHost(
    modifier: Modifier = Modifier,
    movieHubNavigator: MovieHubNavigator,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    openDialog: (OpenDialog) -> Unit,
    startDestination: String = MOVIES_NAVIGATION_ROUTE,
) {
    val navController = movieHubNavigator.navController
    val activity = (LocalContext.current as Activity)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        movieScreen(
            onMovieClick = navController::navigateToMovieDetail,
            onShowSnackbar = onShowSnackbar,
        )
        movieDetailScreen(
            onBackClick = navController::popBackStack,
            onShareClick = activity::createShareIntent,
            onTrailerClick = navController::navigateToVideoPlayer,
            onShowSnackbar = onShowSnackbar,
        )
        bookmarksScreen(
            onMovieClick = navController::navigateToMovieDetail,
            onShowSnackbar = onShowSnackbar,
        )
        settingsScreen(
            onBackClick = navController::popBackStack,
            openDialog = openDialog,
        )
        searchScreen(
            onMovieClick = navController::navigateToMovieDetail,
            onShowSnackbar = onShowSnackbar,
        )
        videoPlayerScreen()
    }
}

private fun Activity.createShareIntent(movieName: String) {
    val shareText = getString(R.string.share_text_movie, movieName)
    val shareIntent = ShareCompat
        .IntentBuilder(this)
        .setText(shareText)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    startActivity(shareIntent)
}
