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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.seosh817.moviehub.core.designsystem.component.DefaultTopAppBar

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val settingsUiState = settingsViewModel.settingsUiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState.value,
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
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        when (settingsUiState) {
            is SettingsUiState.Loading -> {

            }
            is SettingsUiState.Success -> {
                SettingsContent(
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
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .statusBarsPadding(),
        topBar = {
            DefaultTopAppBar(title = stringResource(id = R.string.settings_title), onBackClick = onBackClick) {

            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding)
        ) {

        }
    }
}
