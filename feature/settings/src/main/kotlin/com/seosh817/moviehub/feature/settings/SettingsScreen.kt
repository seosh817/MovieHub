package com.seosh817.moviehub.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seosh817.moviehub.core.designsystem.component.DefaultTopAppBar
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.ui.ContentsLoading
import com.seosh817.ui.ContentsSection
import com.seosh817.ui.SettingsItem

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState,
        onBackClick = onBackClick
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
                .background(MaterialTheme.colorScheme.background)
    ) {
        when (settingsUiState) {
            is SettingsUiState.Loading -> Box(Modifier.fillMaxSize()) {
                ContentsLoading(
                        modifier = Modifier
                                .align(Alignment.Center)
                )
            }
            is SettingsUiState.Success -> {
                SettingsContent(
                    darkThemeMode = settingsUiState.settings.darkThemeMode,
                    useDynamicColor = settingsUiState.settings.useDynamicColor,
                    onBackClick = onBackClick
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    darkThemeMode: DarkThemeMode,
    useDynamicColor: Boolean,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .statusBarsPadding(),
        topBar = {
            DefaultTopAppBar(
                    title = stringResource(id = R.string.settings_title),
                    onBackClick = onBackClick) {}
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .consumeWindowInsets(padding)
        ) {
            ContentsSection(
                title = stringResource(id = R.string.preferences)) {
                SettingsItem(
                    modifier = Modifier.fillMaxWidth(),
                    titleText = stringResource(id = R.string.app_theme),
                    valueText = darkThemeMode.name,
                    onClick = {

                    }
                )

                SettingsItem(
                    modifier = Modifier.fillMaxWidth(),
                    titleText = stringResource(id = R.string.dynamic_color),
                    valueText = useDynamicColor.toString(),
                    onClick = {

                    }
                )
                SettingsItem(
                    modifier = Modifier.fillMaxWidth(),
                    titleText = stringResource(id = R.string.app_version),
                    valueText = useDynamicColor.toString(),
                    onClick = {

                    }
                )
            }
        }
    }
}
