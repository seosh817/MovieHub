package com.seosh817.moviehub.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.seosh817.moviehub.core.designsystem.theme.MovieHubTheme
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.moviehub.navigation.MovieHubNavigator
import com.seosh817.moviehub.navigation.rememberMovieHubNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveable
import com.seosh817.moviehub.feature.settings.AppThemeSettingsDialog
import com.seosh817.moviehub.core.model.OpenDialog

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState by mutableStateOf<MainUiState>(MainUiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                is MainUiState.Loading -> true
                is MainUiState.Success -> false
            }
        }

        enableEdgeToEdge()

        setContent {
            val movieHubNavigator: MovieHubNavigator = rememberMovieHubNavigator()

            val snackbarHostState = remember { SnackbarHostState() }
            val topAppBarState = rememberTopAppBarState()
            var openDialog by rememberSaveable {
                mutableStateOf(OpenDialog.NONE)
            }

            val isDarkTheme = shouldUseDarkTheme(uiState)
            val useDynamicColor = shouldUseDynamicColor(uiState)

            if (openDialog != OpenDialog.NONE) {
                when (openDialog) {
                    OpenDialog.NONE -> {}
                    OpenDialog.APP_THEME_SETTINGS -> {
                        if (uiState is MainUiState.Success) {
                            AppThemeSettingsDialog(
                                appThemeMode = (uiState as MainUiState.Success).userSettings.darkThemeMode,
                                onThemeClick = viewModel::updateDarkThemeMode,
                                onDismiss = {
                                    openDialog = OpenDialog.NONE
                                },
                            )
                        }
                    }
                }
            }

            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { isDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT
                    ) { isDarkTheme },
                )
                onDispose {}
            }

            MovieHubTheme(
                darkTheme = isDarkTheme,
                useDynamicColor = useDynamicColor,
            ) {
                MainScreen(
                    movieHubNavigator = movieHubNavigator,
                    snackbarHostState = snackbarHostState,
                    topAppBarState = topAppBarState,
                    openDialog = {
                        openDialog = it
                    },
                )
            }
        }
    }
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainUiState,
): Boolean = when (uiState) {
    MainUiState.Loading -> isSystemInDarkTheme()
    is MainUiState.Success -> when (uiState.userSettings.darkThemeMode) {
        DarkThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkThemeMode.LIGHT -> false
        DarkThemeMode.DARK -> true
    }
}

@Composable
private fun shouldUseDynamicColor(
    uiState: MainUiState,
): Boolean = when (uiState) {
    MainUiState.Loading -> false
    is MainUiState.Success -> uiState.userSettings.useDynamicColor
}