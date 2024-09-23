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

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.seosh817.moviehub.core.designsystem.component.dialog.BaseDialog
import com.seosh817.moviehub.core.model.DarkThemeMode
import com.seosh817.ui.RadioButtonRow

@Composable
fun AppThemeSettingsDialog(
    appThemeMode: DarkThemeMode,
    onThemeClick: (DarkThemeMode) -> Unit,
    onDismiss: () -> Unit,
) {
    BaseDialog(
        title = stringResource(id = R.string.app_theme),
        icon = {
            Icon(
                imageVector = Icons.Default.BrightnessMedium,
                contentDescription = null,
            )
        },
        content = {
            Column {
                DarkThemeMode
                    .entries
                    .forEach { darkThemeMode ->
                        RadioButtonRow(
                            text = darkThemeMode.name,
                            selected = appThemeMode == darkThemeMode,
                            onClick = { onThemeClick(darkThemeMode) },
                        )
                    }
            }
        },
        onDismiss = onDismiss,
    )
}
