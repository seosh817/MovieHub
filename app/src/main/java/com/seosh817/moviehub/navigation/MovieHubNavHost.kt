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
import com.seosh817.moviehub.feature.movie_detail.navigation.movieDetailScreen
import com.seosh817.moviehub.feature.movie_detail.navigation.navigateToMovieDetail
import com.seosh817.moviehub.feature.movies.navigation.movieScreen
import com.seosh817.moviehub.feature.movies.navigation.moviesNavigationRoute
import com.seosh817.moviehub.core.model.OpenDialog
import com.seosh817.moviehub.feature.bookmarks.navigation.bookmarksScreen
import com.seosh817.moviehub.feature.settings.navigation.settingsScreen

@Composable
fun MovieHubNavHost(
    modifier: Modifier = Modifier,
    movieHubNavigator: MovieHubNavigator,
    onShowSnackbar: suspend (String, String?, SnackbarDuration) -> Boolean,
    openDialog: (OpenDialog) -> Unit,
    startDestination: String = moviesNavigationRoute
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
            onShowSnackbar = onShowSnackbar
        )
        movieDetailScreen(
            onBackClick = navController::popBackStack,
            onShareClick = activity::createShareIntent
        )
        bookmarksScreen(
            onMovieClick = navController::navigateToMovieDetail,
            onShowSnackbar = onShowSnackbar
        )
        settingsScreen(
            onBackClick = navController::popBackStack,
            openDialog = openDialog
        )
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
