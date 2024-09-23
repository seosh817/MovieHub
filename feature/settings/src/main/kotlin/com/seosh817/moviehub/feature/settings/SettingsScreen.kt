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
package com.seosh817.moviehub.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.seosh817.moviehub.core.designsystem.theme.AppDimens
import com.seosh817.moviehub.core.model.AppLanguage
import com.seosh817.moviehub.core.model.AppVersions
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.moviehub.core.model.OpenDialog
import com.seosh817.ui.ContentsLoading
import com.seosh817.ui.ContentsSection
import com.seosh817.ui.settings.SettingsItem
import com.seosh817.ui.settings.SettingsSwitchItem

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    openDialog: (OpenDialog) -> Unit,
) {
    val settingsUiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()
    val appVersions by settingsViewModel.appVersionsStateFlow.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = modifier,
        settingsUiState = settingsUiState,
        appVersions = appVersions,
        onBackClick = onBackClick,
        openDialog = openDialog,
        onUseDynamicColorClick = settingsViewModel::updateDynamicColorPreference,
    )
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsUiState: SettingsUiState,
    appVersions: AppVersions,
    onBackClick: () -> Unit,
    openDialog: (OpenDialog) -> Unit,
    onUseDynamicColorClick: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
    ) {
        when (settingsUiState) {
            is SettingsUiState.Loading -> Box(Modifier.fillMaxSize()) {
                ContentsLoading(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
            }

            is SettingsUiState.Success -> {
                SettingsContent(
                    darkThemeMode = settingsUiState.settings.darkThemeMode,
                    useDynamicColor = settingsUiState.settings.useDynamicColor,
                    appLanguage = settingsUiState.settings.appLanguage,
                    appVersions = appVersions,
                    onBackClick = onBackClick,
                    openDialog = openDialog,
                    onUseDynamicColorClick = onUseDynamicColorClick,
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
    appLanguage: AppLanguage,
    appVersions: AppVersions,
    useDynamicColor: Boolean,
    onBackClick: () -> Unit,
    openDialog: (OpenDialog) -> Unit,
    onUseDynamicColorClick: (Boolean) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .statusBarsPadding(),
        topBar = {
            DefaultTopAppBar(
                title = stringResource(id = R.string.settings_title),
                onBackClick = onBackClick,
            ) {}
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .consumeWindowInsets(padding),
        ) {
            ContentsSection(
                title = stringResource(id = R.string.preferences),
            ) {
                SettingsItem(
                    titleText = stringResource(id = R.string.app_theme),
                    valueText = darkThemeMode.name,
                    onClick = {
                        openDialog(OpenDialog.APP_THEME_SETTINGS)
                    },
                )

                SettingsItem(
                    titleText = stringResource(id = R.string.language),
                    valueText = appLanguage.name,
                    onClick = {
                        openDialog(OpenDialog.APP_LANGUAGE_SETTINGS)
                    },
                )

                SettingsSwitchItem(
                    titleText = stringResource(id = R.string.dynamic_color),
                    isChecked = useDynamicColor,
                    onClick = {
                        onUseDynamicColorClick(!useDynamicColor)
                    },
                )
            }

            ContentsSection(
                modifier = Modifier.padding(top = AppDimens.PaddingLarge),
                title = stringResource(id = R.string.about),
            ) {
                SettingsItem(
                    titleText = stringResource(id = R.string.app_version),
                    valueText = appVersions.versionName,
                    onClick = { },
                )
            }
        }
    }
}
