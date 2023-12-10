package com.seosh817.moviehub.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.seosh817.moviehub.R
import com.seosh817.moviehub.core.designsystem.component.MovieHubNavigationBar
import com.seosh817.moviehub.core.designsystem.component.MovieHubNavigationBarItem
import com.seosh817.moviehub.core.designsystem.component.MainTopAppBar
import com.seosh817.moviehub.navigation.MovieHubNavHost
import com.seosh817.moviehub.navigation.MovieHubNavigator
import com.seosh817.moviehub.navigation.PrimaryDestination
import com.seosh817.moviehub.navigation.isPrimaryDestinationInHierarchy
import com.seosh817.moviehub.navigation.rememberMovieHubNavigator
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MainScreen(
    movieHubNavigator: MovieHubNavigator = rememberMovieHubNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        bottomBar = {
            MovieHubBottomBar(
                currentDestination = movieHubNavigator.currentDestination,
                destinations = PrimaryDestination
                    .values()
                    .toList()
                    .toPersistentList(),
                onTabSelected = { movieHubNavigator.navigate(it) },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            val destination = movieHubNavigator.currentPrimaryDestination
            if (destination != null) {
                MainTopAppBar(
                    titleRes = destination.labelResId,
                    navigationIcon = Icons.Default.Search,
                    navigationIconContentDescription = stringResource(
                        id = R.string.top_app_bar_navigation_icon_description,
                    ),
                    onNavigationClick = { },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.DarkMode,
                                contentDescription = stringResource(id = R.string.top_app_bar_dark_mode_icon_description)
                            )
                        }

                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(id = R.string.top_app_bar_settings_icon_description)
                            )
                        }
                    },
                )
            }

            MovieHubNavHost(
                movieHubNavigator = movieHubNavigator,
                onMovieClick = { movieId ->
                    // movieHubNavigator.navigateToMovieDetail(movieId)
                },
            )
        }
    }
}

@Composable
private fun MovieHubBottomBar(
    currentDestination: NavDestination?,
    destinations: PersistentList<PrimaryDestination>,
    onTabSelected: (PrimaryDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    MovieHubNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { primaryDestination ->
            val selected = currentDestination.isPrimaryDestinationInHierarchy(primaryDestination)
            MovieHubNavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(primaryDestination) },
                icon = {
                    Icon(
                        imageVector = primaryDestination.unSelectedIcon,
                        contentDescription = primaryDestination.contentDescription,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = primaryDestination.selectedIcon,
                        contentDescription = primaryDestination.contentDescription,
                    )
                },
                label = { Text(stringResource(primaryDestination.labelResId)) },
            )
        }
    }
}