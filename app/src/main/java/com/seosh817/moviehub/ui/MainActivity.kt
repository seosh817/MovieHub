package com.seosh817.moviehub.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import com.seosh817.moviehub.core.designsystem.theme.MovieHubTheme
import com.seosh817.moviehub.navigation.MovieHubNavigator
import com.seosh817.moviehub.navigation.rememberMovieHubNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val movieHubNavigator: MovieHubNavigator = rememberMovieHubNavigator()
            MovieHubTheme {
                MainScreen(
                    movieHubNavigator = movieHubNavigator,
                )
            }
        }
    }
}
